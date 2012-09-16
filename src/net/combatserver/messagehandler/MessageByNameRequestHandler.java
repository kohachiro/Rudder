package net.combatserver.messagehandler;

import net.combatserver.protobuf.MessageByName.MessageByNameRequest;
import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class MessageByNameRequestHandler {

	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.MessageByNameRequest_VALUE+"]MessageByNameRequest");
		User toUser=User.get(((MessageByNameRequest)data).getUsername());
		if (toUser!=null){
			User sender=User.get(channel);			
			Message mesage=new Message(((MessageByNameRequest)data).getMessage(),sender,MessageRouter.ROUTER_USER_VALUE);		
			NewMessageNoticeHandler.invoke(mesage, toUser.getChannel());
		}
		return MessageHandler.MessageByNameRequest_VALUE;
	}
}
