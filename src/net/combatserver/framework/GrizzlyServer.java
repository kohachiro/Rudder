package net.combatserver.framework;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import net.combatserver.core.AbstractServer;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.strategies.SameThreadIOStrategy;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

/**
 * @author kohachiro
 *
 */
public class GrizzlyServer extends AbstractServer {
	final TCPNIOTransport transport;
	ThreadPoolExecutor threadPool;
	/**
	 * 
	 */
	public GrizzlyServer() {
		//please use no limit threadpool (newCachedThreadPool) for performance
		threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ThreadPoolConfig config = ThreadPoolConfig.defaultConfig().copy()
                .setCorePoolSize(threadPool.getCorePoolSize())
                .setMaxPoolSize(threadPool.getMaximumPoolSize())
                .setPoolName("GRIZZLY-SERVER");
        GrizzlyExecutorService.createInstance(config);
        
        FilterChainBuilder filterChainBuilder = FilterChainBuilder.stateless();
        filterChainBuilder.add(new TransportFilter());
        filterChainBuilder.add(new GrizzlyServerHandler());
        TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
        builder.setIOStrategy(SameThreadIOStrategy.getInstance());
        builder.setTcpNoDelay(true);
        //builder.setKeepAlive(true);
        //builder.setReuseAddress(true);
        transport = builder.build();
        transport.setProcessor(filterChainBuilder.build());		
	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.core.AbstractServer#bind(java.net.InetSocketAddress)
	 */
	@Override
	public void bind(InetSocketAddress inetSocketAddress) throws Exception {
		transport.bind(inetSocketAddress);
	}

	/**
	 * @throws IOException
	 */
	public void start() throws IOException {
		transport.start();
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		AbstractServer.framework="grizzly-framework-2.1.1";			
		GrizzlyServer server=new GrizzlyServer();
		server.networkBind();
		server.start();		
		server.console();
	}

}
