package net.combatserver.messagehandler;

import net.combatserver.protobuf.CreateRoom.CreateRoomRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Template;
import net.combatserver.serverlogic.User;
import net.combatserver.serverlogic.Region;


/**
 * @author kohachiro
 *
 */
public class CreateRoomRequestHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.CreateRoomRequest_VALUE+"]CreateRoomRequest");
		Template template=Template.get(((CreateRoomRequestData)data).getTemplateid());
		if (template==null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_TEMPLATE_NOT_FOUND_VALUE,channel);
		if (!template.getRegion().isCreateRoom())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ROOM_CREATE_FAILED_VALUE,channel);
		Region region=template.getRegion();
		if (region.isFull())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_REGION_NOT_FOUND_VALUE,channel);
		User user=User.get(channel);
		Room toRoom=template.createRoom(user,((CreateRoomRequestData)data).getName(),((CreateRoomRequestData)data).getPassword());
		Room fromRoom=user.getRoom();
//		if (toRoom.isSendUserList())
//			UserListResponseHandler.invoke(toRoom, channel);
		toRoom.newRoomNotice(toRoom.getCreaterId());
		toRoom.newUser(user);
		fromRoom.removeUser(user.getId());

		CreateRoomReponseHandler.invoke(toRoom,channel);
		ChangeRoomResponseHandler.invoke(toRoom,channel);
		return MessageHandler.CreateRoomRequest_VALUE;
	}
}
