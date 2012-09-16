package net.combatserver.messagehandler;

import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.RoomMessage.RoomMessageRequestData;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class RoomMessageRequestHandler {
	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+ MessageHandler.RoomMessageRequest_VALUE+"]RoomMessageRequest");		

		User sender=User.get(channel);
		Message mesage=new Message(((RoomMessageRequestData)data).getMessage(),sender,MessageRouter.ROUTER_ROOM_VALUE);
		sender.sendToRoom(mesage);
		return MessageHandler.RoomMessageRequest_VALUE;
	}
}
