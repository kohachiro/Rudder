package net.combatserver.framework;

import java.nio.ByteBuffer;

import net.combatserver.core.AbstractServer;
import net.combatserver.core.InterfaceServerHandler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;


 /**
 * @author kohachiro
 *
 */
public class NettyServerHandler extends SimpleChannelUpstreamHandler implements InterfaceServerHandler{
	public NettyServerHandler() {
		AbstractServer.callBackHandler = this;
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		AbstractServer.connected(e.getChannel());
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		AbstractServer.execute(((ChannelBuffer)e.getMessage()).toByteBuffer(), e.getChannel());
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		AbstractServer.errorHandler(e.getChannel());
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		AbstractServer.disConnected(e.getChannel());
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#sendResponse(int, java.nio.ByteBuffer, java.lang.Object)
	 */	
	@Override	
	public void sendResponse(int methodID,ByteBuffer message, Object channel) throws Exception {
    	ChannelBuffer buf = ChannelBuffers.directBuffer(message.remaining()+4);
    	buf.writeShort(methodID);
    	buf.writeChar(message.remaining());
    	buf.writeBytes(message);
		((Channel)channel).write(buf);
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#sendResponseClose(java.nio.ByteBuffer, java.lang.Object)
	 */
	@Override
	public void sendResponse(ByteBuffer message, Object channel, boolean isClose)
			throws Exception {
    	ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
    	buf.writeBytes(message);
    	ChannelFuture future = ((Channel)channel).write(buf);
    	if (isClose)
    		future.addListener(ChannelFutureListener.CLOSE);	
		
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#close(java.lang.Object)
	 */	
	@Override
	public void close(Object channel) throws Exception {
		((Channel)channel).close();
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#getIP(java.lang.Object)
	 */	
	@Override
	public String getIP(Object channel) {
		String ip=((Channel)channel).getRemoteAddress().toString();
		return ip.substring(1,ip.lastIndexOf(":"));	
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.InterfaceServerHandler#getIP(java.lang.Object)
	 */		
	@Override
	public int getServerPort(Object channel)  {
		String ip=((Channel)channel).getLocalAddress().toString();
		return Integer.parseInt(ip.substring(ip.lastIndexOf(":")+1));			
	}	
}