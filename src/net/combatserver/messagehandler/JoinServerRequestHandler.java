package net.combatserver.messagehandler;

import net.combatserver.protobuf.JoinServer.JoinServerRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class JoinServerRequestHandler   {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.JoinServerRequest_VALUE+"]JoinServerRequest");
		JoinServerRequestData requestData=(JoinServerRequestData)data;
		User user=User.initByChannel(requestData.getUsername(),channel);
		JoinServerResponseHandler.invoke(user,channel);
		return MessageHandler.JoinServerRequest_VALUE;
	}
}
