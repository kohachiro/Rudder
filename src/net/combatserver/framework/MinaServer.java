package net.combatserver.framework;

import java.net.InetSocketAddress;

import net.combatserver.core.AbstractServer;

import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @author kohachiro
 *
 */
public class MinaServer extends AbstractServer {
	final SocketAcceptor acceptor;
	/**
	 * 
	 */
	public MinaServer() {
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
        acceptor.setReuseAddress(true);
        acceptor.getSessionConfig().setTcpNoDelay(true);
        //acceptor.getSessionConfig().setKeepAlive(true);
        //acceptor.getSessionConfig().setReuseAddress(true);
		acceptor.setHandler(new MinaServerHandler());
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)  throws Exception {
		AbstractServer.framework="mina-core-2.0.4";			
		MinaServer server=new MinaServer();
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
		acceptor.bind(inetSocketAddress);
	}

}
