package net.combatserver.framework;

import java.io.IOException;
import java.nio.ByteBuffer;

import net.combatserver.core.AbstractServer;
import net.combatserver.core.InterfaceServerHandler;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

/**
 * @author kohachiro
 *
 */
public class GrizzlyServerHandler extends BaseFilter implements InterfaceServerHandler {
	/**
	 * 
	 */
	public GrizzlyServerHandler() {
		AbstractServer.callBackHandler=this;
	}

	/* (non-Javadoc)
	 * @see org.glassfish.grizzly.filterchain.BaseFilter#exceptionOccurred(org.glassfish.grizzly.filterchain.FilterChainContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
		AbstractServer.errorHandler(ctx.getConnection());
	}

	/* (non-Javadoc)
	 * @see org.glassfish.grizzly.filterchain.BaseFilter#handleClose(org.glassfish.grizzly.filterchain.FilterChainContext)
	 */
	@Override
	public NextAction handleClose(FilterChainContext ctx) throws IOException {
		AbstractServer.disConnected(ctx.getConnection());
		return super.handleClose(ctx);
	}

	/* (non-Javadoc)
	 * @see org.glassfish.grizzly.filterchain.BaseFilter#handleAccept(org.glassfish.grizzly.filterchain.FilterChainContext)
	 */
	@Override
	public NextAction handleAccept(FilterChainContext ctx) throws IOException {
		AbstractServer.connected(ctx.getConnection());
		return super.handleConnect(ctx);
	}

	/* (non-Javadoc)
	 * @see org.glassfish.grizzly.filterchain.BaseFilter#handleRead(org.glassfish.grizzly.filterchain.FilterChainContext)
	 */
	@Override
	public NextAction handleRead(FilterChainContext ctx) throws IOException {
		AbstractServer.execute(((Buffer) ctx.getMessage()).toByteBuffer(),ctx.getConnection());
		return super.handleRead(ctx);
	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#sendResponse(int, java.nio.ByteBuffer, java.lang.Object)
	 */
	@Override
	public void sendResponse(int methodID, ByteBuffer message, Object channel)
			throws Exception {
		Buffer buf=((Connection<?>) channel).getTransport().getMemoryManager().allocate(message.remaining()+4);
		buf.allowBufferDispose(true);
		buf.putShort((short)methodID);
		buf.putChar((char)message.remaining());
		buf.put(message);
		buf.flip();
		((Connection<?>) channel).write(buf);

	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#sendResponseClose(java.nio.ByteBuffer, java.lang.Object)
	 */
	@Override
	public void sendResponse(ByteBuffer message, Object channel, boolean isClose)
			throws Exception {
		Buffer buf=((Connection<?>) channel).getTransport().getMemoryManager().allocate(message.remaining());
		buf.allowBufferDispose(true);
		buf.put(message);
		((Connection<?>) channel).write(buf.flip());
		if (isClose)
			((Connection<?>) channel).close();

	}


	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#close(java.lang.Object)
	 */
	@Override
	public void close(Object channel) throws Exception {
		((Connection<?>) channel).close();
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#getIP(java.lang.Object)
	 */	
	@Override
	public String getIP(Object channel)  {
		String ip=((Connection<?>)channel).getPeerAddress().toString();
		return ip.substring(1,ip.lastIndexOf(":"));			
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#getIP(java.lang.Object)
	 */		
	@Override
	public int getServerPort(Object channel)  {
		String ip=((Connection<?>)channel).getLocalAddress().toString();
		return Integer.parseInt(ip.substring(ip.lastIndexOf(":")+1));			
	}
}
