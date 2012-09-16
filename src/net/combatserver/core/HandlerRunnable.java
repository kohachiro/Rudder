package net.combatserver.core;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import net.combatserver.messagehandler.ServerErrorResponseHandler;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.util.DumpTools;
import net.combatserver.util.Reflection;


/**
 * ��Ϣ�����߳���<br/>
 * HandlerRunnableͨ��������� net framework�� messageReceived<br/>
 * �߳� ��ʵ�ָ����������Ӧ�Ӷ���߸���<br/> 
 * @author kohachiro
 */
public class HandlerRunnable implements Runnable {
	
	/**
	 *��Ϣ 
	 */
	ByteBuffer buffer;
	/**
	 * �ܵ� channel
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
	 * ��Ϣ������<br/>
	 * ��Ϣ��: ǰ�����ֽ� ��ʾ methodID<br/> 
	 *       �� protobuf.Protocol�����ҵ�����<br/> 
	 *       �� 2 3�ֽڱ�ʾ ����  <br/>
	 *       methodID > 256��ʾ����Ҫprotobuf����<br/>
	 *       methodID > 1024 ��ʾ�Ƿ�protobufЭ��(PolicyFile��HttpGet��HttpPost)<br/>
	 * ������� : ��ȡmethodID=>ͨ��methodID�ҵ�handler��=>�ж��Ƿ���Ҫprotobuf����=>���� ����handler��<br/>
	 * �쳣���� : ��Ϣ���������쳣�׵�����ͳһ����ͨ���쳣���� ת�� <br/>
	 *       ServerErrorResponseHandler ������<br/>
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
