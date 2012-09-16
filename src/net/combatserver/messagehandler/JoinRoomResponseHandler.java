package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.JoinRoom.JoinRoomResponseData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Server;
import net.combatserver.util.DumpTools;

/**
 * @author kohachiro
 *
 */
public class JoinRoomResponseHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.JoinRoomResponse_VALUE+"]JoinRoomResponse");
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
		
		JoinRoomResponseData.Builder builder=JoinRoomResponseData.newBuilder();
		builder.setRoom(roomBuilder);
		JoinRoomResponseData message=builder.build();

		Server.sendResponse(MessageHandler.JoinRoomResponse_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);

		return MessageHandler.JoinRoomResponse_VALUE;
	}

}
