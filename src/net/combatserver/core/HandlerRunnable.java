package net.combatserver.core;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import net.combatserver.messagehandler.ServerErrorResponseHandler;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.util.DumpTools;
import net.combatserver.util.Reflection;


/**
 * 消息处理线程类<br/>
 * HandlerRunnable通过尽快结束 net framework的 messageReceived<br/>
 * 线程 来实现更快的连接响应从而提高负载<br/> 
 * @author kohachiro
 */
public class HandlerRunnable implements Runnable {
	
	/**
	 *消息 
	 */
	ByteBuffer buffer;
	/**
	 * 管道 channel
	 */
	Object channel;
	/**
	 * 
	 */
	public HandlerRunnable(ByteBuffer buffer, Object channel) {
		this.buffer = buffer;
		this.channel = channel;
	}


	/**
	 * 消息处理类<br/>
	 * 消息体: 前两个字节 表示 methodID<br/> 
	 *       在 protobuf.Protocol可以找到定义<br/> 
	 *       第 2 3字节表示 长度  <br/>
	 *       methodID > 256表示不需要protobuf解码<br/>
	 *       methodID > 1024 表示是非protobuf协议(PolicyFile，HttpGet，HttpPost)<br/>
	 * 处理过程 : 获取methodID=>通过methodID找到handler类=>判断是否需要protobuf解码=>解码 调用handler类<br/>
	 * 异常处理 : 消息体内所有异常抛到这里统一处理，通过异常类型 转发 <br/>
	 *       ServerErrorResponseHandler 来处理<br/>
	 */
	@Override
	public void run() {
		try {
			if (buffer.remaining()<4)
				return;
			short methodID = buffer.getShort();
			int length = buffer.getChar();
			if (methodID<1024 && buffer.remaining()<length){
				buffer.rewind();
				return;
			}
			System.out.println("methodID:"+methodID+"\tlength:"+length+"\tremaining:"+buffer.remaining());
			

			String name = MessageHandler.valueOf(methodID).name();
			
			if (name==null)
				name="InvidRequest";
			StringBuffer protoName = new StringBuffer(
					AbstractServer.ProtobufClassPath).append(name.substring(0,name.length()-7)).append("$")
					.append(name).append("Data");
			StringBuffer handlerName = new StringBuffer(
					AbstractServer.MessageClassPath).append(name).append(
					"Handler");
			Object message=decodeMessage(protoName.toString(),buffer, methodID,length);
			
			if (ExtensionsManager.isModule(methodID))
				ExtensionsManager.getModule(methodID).invoke(message, channel);
			else
				Reflection.invokeStaticMethod(handlerName.toString(), "invoke", new Object[] { message, channel },new Class<?>[]{Object.class, Object.class});

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			ServerErrorResponseHandler.invoke(
					ReturnStatus.STATUS_INVID_REQUEST_VALUE, channel);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
			ServerErrorResponseHandler.invoke(
					ReturnStatus.STATUS_INVID_REQUEST_DATA_VALUE, channel);
		} catch (SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
			ServerErrorResponseHandler.invoke(
					ReturnStatus.STATUS_INTERNAL_ERROR_VALUE, channel);
		} catch (Exception e1) {
			e1.printStackTrace();
			ServerErrorResponseHandler.invoke(
					ReturnStatus.STATUS_UNKNOWN_ERROR_VALUE, channel);
		}
	}


	private Object decodeMessage(String protoName, ByteBuffer buffer, int methodID,int length) throws Exception {
		if (methodID>256)
			return buffer;
		byte[] message = new byte[length];	
		buffer.get(message);
		DumpTools.printHex(message);
		return Reflection.invokeStaticMethod(protoName, "parseFrom", new Object[] {message});
	}

}
