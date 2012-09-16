package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.RoomRemove.RoomRemoveNoticeData;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class RoomRemoveNoticeHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.RoomRemoveNotice_VALUE+"]RoomRemoveNotice");
		RoomRemoveNoticeData.Builder builder=RoomRemoveNoticeData.newBuilder();
		builder.setRoomid((int)data);
		RoomRemoveNoticeData message=builder.build();

		Server.sendResponse(MessageHandler.RoomRemoveNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.RoomRemoveNotice_VALUE;
	}
}
