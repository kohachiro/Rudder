package net.combatserver.messagehandler;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class DisconnectedRequest {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.DisconnectedRequest_VALUE+"]DisconnectedRequest");
		Server.disconnected(channel);
		return MessageHandler.DisconnectedRequest_VALUE;
	}

}
