package net.combatserver.framework;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.combatserver.core.AbstractServer;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author kohachiro
 * 
 */
public class NettyServer extends AbstractServer {
	final ServerBootstrap bootstrap;
	/**
	 * 
	 */
	public NettyServer() {
		//please use no limit threadpool (newCachedThreadPool) for performance		
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new NettyServerHandler());
			}
		});

		bootstrap.setOption("child.tcpNoDelay", true);
//		bootstrap.setOption("child.keepAlive", true);
//		bootstrap.setOption("child.reuseAddress", true);
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)  throws Exception {
		AbstractServer.framework="netty-3.2.6";			
		NettyServer server=new NettyServer();
		server.networkBind();
		server.start();		
		server.console();

	}
	
	/**
	 * 
	 */
	public void start(){
		
	}	
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.AbstractServer#bind(java.net.InetSocketAddress)
	 */
	@Override
	public void bind(InetSocketAddress inetSocketAddress) throws Exception {
		bootstrap.bind(inetSocketAddress);
	}
		

}
