package net.combatserver.messagehandler;

import net.combatserver.protobuf.Protocol.MessageHandler;


/**
 * @author kohachiro
 *
 */
public class PingIntervalRequestHandler {
	/**
	 * 
	 */
	//@Override	
	public static int invoke(Object data, Object channel) throws Exception {
		//TODO
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.PingIntervalRequest_VALUE+"]PingIntervalRequest");
		return MessageHandler.PingIntervalRequest_VALUE;
	}
}
