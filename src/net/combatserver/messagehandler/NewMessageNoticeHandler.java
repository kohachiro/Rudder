package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.protobuf.NewMessage.NewMessageNoticeData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class NewMessageNoticeHandler {

	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.NewMessageNotice_VALUE+"]NewMessageNotice");
		NewMessageNoticeData.Builder builder=NewMessageNoticeData.newBuilder();
		builder.setMessage(((Message)data).getMessage());
		builder.setRouter(MessageRouter.valueOf(((Message)data).getRouter()));
		builder.setUserid(((Message)data).getUser().getId());
		builder.setUsername(((Message)data).getUser().getName());
		NewMessageNoticeData message=builder.build();
		
		Server.sendResponse(MessageHandler.NewMessageNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.NewMessageNotice_VALUE;
	}
}