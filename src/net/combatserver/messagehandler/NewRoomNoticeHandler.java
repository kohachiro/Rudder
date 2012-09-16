package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.CreateRoom.NewRoomNoticeData;
import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class NewRoomNoticeHandler {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.NewRoomNotice_VALUE+"]NewRoomNotice");
		Room room=(Room)data;
		DataStructures.Room.Builder roomBuilder=DataStructures.Room.newBuilder();
		roomBuilder.setId(room.getId());
		roomBuilder.setName(room.getName());
		roomBuilder.setMaxUsers(room.getMaxUsers());		
		if (room.getPassword()=="")
			roomBuilder.setNeedPassword(false);
		else
			roomBuilder.setNeedPassword(true);
		
		room.addCountProperty(roomBuilder);
		room.addProperties(roomBuilder);

		
		NewRoomNoticeData.Builder builder=NewRoomNoticeData.newBuilder();
		builder.setRoom(roomBuilder);	
		NewRoomNoticeData message=builder.build();
		
		Server.sendResponse(MessageHandler.NewRoomNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.NewRoomNotice_VALUE;
	}
}
