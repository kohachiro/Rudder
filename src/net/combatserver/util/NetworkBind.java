package net.combatserver.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import net.combatserver.core.AbstractServer;


/**
 * @author kohachiro
 *
 */
public class NetworkBind {
	public NetworkBind() {
		
	}
	/**
	 * 
	 */
	public boolean  bind(AbstractServer server,int port) {
        int successed=0;
        try {
			for(Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();)
			{
                Enumeration<InetAddress> enumeration1 = ((NetworkInterface)enumeration.nextElement()).getInetAddresses();
                while(enumeration1.hasMoreElements()) 
                {
                    InetAddress inetaddress = (InetAddress)enumeration1.nextElement();
                    InetSocketAddress inetsocketaddress = new InetSocketAddress(inetaddress, port);
                    String address=inetsocketaddress.toString().substring(1, inetsocketaddress.toString().length());
                    if (inetaddress.toString().lastIndexOf(".")>0 ){
                        try {
                        	server.bind(inetsocketaddress);
							System.out.println("Server is listening on " + address);
							successed=1;
						} catch (Exception e) {
							System.err.println("Could not bind to " + address);
						}
                    }

                }
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
        if (successed==0){
			System.err.println("all of networkInterfaces is in use.\n"
					+ "Server instance is shutting down...");
			return false;
        }
        return true;        
	}
//	public boolean  bind(ServerBootstrap bootstrap,InetSocketAddress address) {
//        try {
//			bootstrap.bind(address);
//			System.out.println("Server is listening on " + address.toString());
//		} catch (Exception e) {
//			System.err.println("Could not bind to " + address.toString());
//			return false;
//		}
//        return true;   
//	}
}
