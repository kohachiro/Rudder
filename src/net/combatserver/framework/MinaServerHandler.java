package net.combatserver.framework;

import java.nio.ByteBuffer;

import net.combatserver.core.AbstractServer;
import net.combatserver.core.InterfaceServerHandler;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @author kohachiro
 *
 */
public class MinaServerHandler extends IoHandlerAdapter implements
		InterfaceServerHandler {

	/**
	 * 
	 */
	public MinaServerHandler() {
		AbstractServer.callBackHandler = this;
	}
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.mina.core.buffer.IoBuffer,  java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		AbstractServer.errorHandler(session);
	}
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.buffer.IoBuffer, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		AbstractServer.execute(((IoBuffer)message).buf(), session);
	}
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionClosed(org.apache.mina.core.buffer.IoBuffer)
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		AbstractServer.disConnected(session);
	}
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionCreated(org.apache.mina.core.buffer.IoBuffer)
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		AbstractServer.connected(session);
	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#sendResponse(int, java.nio.ByteBuffer, java.lang.Object)
	 */
	@Override
	public void sendResponse(int methodID, ByteBuffer message, Object channel)
			throws Exception {
		ByteBuffer buffer=ByteBuffer.allocate(message.remaining()+4);
		buffer.putShort((short)methodID);
		buffer.putChar((char)message.remaining());
		buffer.put(message);
		buffer.flip();
		((IoSession)channel).write(IoBuffer.wrap(buffer));

	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#sendResponseClose(java.nio.ByteBuffer, java.lang.Object)
	 */
	@Override
	public void sendResponse(ByteBuffer message, Object channel, boolean isClose)
			throws Exception {
		WriteFuture future = ((IoSession)channel).write(IoBuffer.wrap(message));
		if (isClose)
			future.addListener(IoFutureListener.CLOSE);

	}


	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#close(java.lang.Object)
	 */
	@Override
	public void close(Object channel) throws Exception {
		((IoSession)channel).close(true);
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#getIP(java.lang.Object)
	 */	
	@Override
	public String getIP(Object channel)  {
		String ip=((IoSession)channel).getRemoteAddress().toString();
		return ip.substring(1,ip.lastIndexOf(":"));	
		
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#getIP(java.lang.Object)
	 */		
	@Override
	public int getServerPort(Object channel)  {
		String ip=((IoSession)channel).getLocalAddress().toString();
		return Integer.parseInt(ip.substring(ip.lastIndexOf(":")+1));			
	}
}
