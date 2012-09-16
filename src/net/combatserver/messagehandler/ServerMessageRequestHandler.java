package net.combatserver.messagehandler;

import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerMessage.ServerMessageRequestData;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class ServerMessageRequestHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ServerMessageRequest_VALUE+"]ServerMessageRequest");	

		User sender=User.get(channel);
		Message mesage=new Message(((ServerMessageRequestData)data).getMessage(),sender,MessageRouter.ROUTER_SERVER_VALUE);
		sender.sendToServer(mesage);
		return MessageHandler.ServerMessageRequest_VALUE;
	}
}
