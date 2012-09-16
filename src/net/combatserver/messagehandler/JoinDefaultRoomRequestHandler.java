package net.combatserver.messagehandler;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;


/**
 * @author kohachiro
 *
 */
public class JoinDefaultRoomRequestHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static  int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.JoinDefaultRoomRequest_VALUE+"]JoinDefaultRoomRequest");
		Room room=Room.get(Server.getDefaultRoomId());
		if (room.isFull())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_FULL_VALUE, channel);
		if (room.isLocked())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_LOCKED_VALUE, channel);
		User user=User.get(channel);
		
//		if (room.isSendUserList())
//			UserListResponseHandler.invoke(room, channel);
		
		room.newUser(user);
		
		JoinRoomResponseHandler.invoke(room, channel);
		return MessageHandler.JoinDefaultRoomRequest_VALUE;
	}
}
