package net.combatserver.messagehandler;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.RoomList.RoomListNoticeData;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.Region;

/**
 * @author kohachiro
 *
 */
public class RoomListNoticeHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object region, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.RoomListNotice_VALUE+"]RoomListNotice");
		RoomListNoticeData.Builder builder=RoomListNoticeData.newBuilder();
		Room room;
		for (Iterator<Entry<Integer, Room>> it = ((Region)region).getRoomList().entrySet().iterator();it.hasNext();){
			room=it.next().getValue();
			if (room.isVisible()){
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

				builder.addRoom(roomBuilder);
			}
		}		
		RoomListNoticeData message=builder.build();

		Server.sendResponse(MessageHandler.RoomListNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.RoomListNotice_VALUE;
	}

}
