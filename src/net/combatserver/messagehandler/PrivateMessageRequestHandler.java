package net.combatserver.messagehandler;

import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.protobuf.PrivateMessage.PrivateMessageRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class PrivateMessageRequestHandler {

	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.PrivateMessageRequest_VALUE+"]PrivateMessageRequest");
		User toUser=User.get(((PrivateMessageRequestData)data).getUserid());
		if (toUser!=null){
			User sender=User.get(channel);			
			Message mesage=new Message(((PrivateMessageRequestData)data).getMessage(),sender,MessageRouter.ROUTER_USER_VALUE);		
			NewMessageNoticeHandler.invoke(mesage, toUser.getChannel());
		}
		return MessageHandler.PrivateMessageRequest_VALUE;
	}
}
