package net.combatserver.messagehandler;

import net.combatserver.protobuf.ChangeRoom.ChangeRoomRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class ChangeRoomRequestHandler {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ChangeRoomRequest_VALUE+"]ChangeRoomRequest");
		int toRoomId = ((ChangeRoomRequestData)data).getToRoomId();
		Room toRoom=Room.get(toRoomId);
		if (toRoom == null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_NOT_FOUND_VALUE, channel);
		if (toRoom.isFull())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_FULL_VALUE, channel);
		if (toRoom.isLocked())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_LOCKED_VALUE, channel);
		User user=User.get(channel);	
		Room fromRoom=user.getRoom();
		if (fromRoom==null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_NULL_VALUE, channel);
		
//		if (toRoom.isSendUserList())
//			UserListResponseHandler.invoke(toRoom, channel);
		
		toRoom.newUser(user);
		fromRoom.removeUser(user.getId());
		
		ChangeRoomResponseHandler.invoke(toRoom, channel);
		return MessageHandler.ChangeRoomRequest_VALUE;
	}
}
