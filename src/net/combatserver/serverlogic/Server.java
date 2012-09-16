package net.combatserver.serverlogic;

import java.nio.ByteBuffer;

import net.combatserver.core.AbstractServer;
import net.combatserver.core.ExtensionsManager;
import net.combatserver.core.UserManager;
import net.combatserver.messagehandler.NewMessageNoticeHandler;
import net.combatserver.util.DumpTools;

/**
 * @author kohachiro
 *
 */
public class Server {
	/**
	 * 
	 */
	public Server() {

	}
	/**
	 * 
	 */
	public static String getName() {
		return AbstractServer.name;
	}
	/* (non-Javadoc)
	 * @see com.sunkey.core.InterfaceServerHandler#sendResponse(int,java.nio.ByteBuffer,java.lang.Object)
	 */		
	public static void sendResponse(int methodID, ByteBuffer message,Object channel) throws Exception {
		AbstractServer.callBackHandler.sendResponse(methodID,message,channel);
	}
	/* (non-Javadoc)
	 * @see com.sunkey.core.InterfaceServerHandler#sendResponseClose(java.nio.ByteBuffer,java.lang.Object)
	 */		
	public static void sendResponse(ByteBuffer message,Object channel, boolean isClose) throws Exception {
		AbstractServer.callBackHandler.sendResponse(message,channel,isClose);
	}
	/* (non-Javadoc)
	 * @see com.sunkey.messagehandler.NewMessageResponseHandler#invoke(java.lang.Object,java.lang.Object)
	 */	
	public static void sendMessage(ByteBuffer message,User user) throws Exception{
		if (user==null)
			return;
		NewMessageNoticeHandler.invoke(message, user.getChannel());
	}
	/* (non-Javadoc)
	 * @see com.sunkey.core.InterfaceServerHandler#getId(java.lang.Object)
	 */	
//	public static int getId(Object channel) {
//		return AbstractServer.callBackHandler.getId(channel);
//	}
	/* (non-Javadoc)
	 * @see com.sunkey.core.UserManager#disconnected(java.lang.Object)
	 */
	public static void disconnected(Object channel) throws Exception {
		UserManager.disconnected(channel);		
	}	
	/* (non-Javadoc)
	 * @see com.sunkey.core.ExtensionsManager#getInstance(java.lang.String)
	 */
	public static Plugin getPlugin(String name) {
		return ExtensionsManager.getPlugin(name);	
	}
	/**
	 * @return
	 */
	public static int getDefaultRoomId() {
		return AbstractServer.defaultRoom;	
	}
	/**
	 * @return
	 */
	public static int getPingInterval() {
		return AbstractServer.pingInterval;
	}
	/**
	 * @return
	 */
	public static User getSystemUser() {
		return AbstractServer.systemUser;
	}
	/**
	 * @return
	 */
	public static Object getEntity(String name) {
		return ExtensionsManager.getEntity(name);
	}
	/**
	 * @return
	 */
	public static String getIP(Object channel) {
		return AbstractServer.callBackHandler.getIP(channel);
	}	

}
