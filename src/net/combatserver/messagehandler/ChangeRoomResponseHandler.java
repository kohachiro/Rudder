package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.ChangeRoom.ChangeRoomResponseData;
import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class ChangeRoomResponseHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ChangeRoomResponse_VALUE+"]ChangeRoomResponse");
		Room room=(Room)data;	
		DataStructures.Room.Builder roomBuilder=DataStructures.Room.newBuilder();	
		roomBuilder.setId(room.getId());
		roomBuilder.setName(room.getName());
		roomBuilder.setMaxUsers(room.getMaxUsers());	
		if (room.getPassword()=="")
			roomBuilder.setNeedPassword(false);
		else
			roomBuilder.setNeedPassword(true);

		room.addProperties(roomBuilder);
		
		ChangeRoomResponseData.Builder builder=ChangeRoomResponseData.newBuilder();		
		builder.setRoom(roomBuilder);
		ChangeRoomResponseData message=builder.build();
		
		Server.sendResponse(MessageHandler.ChangeRoomResponse_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);

		return MessageHandler.ChangeRoomResponse_VALUE;
	}

}
