package net.combatserver.messagehandler;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.UserList.UserListNoticeData;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class UserListNoticeHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+ MessageHandler.UserListNotice_VALUE+"]UserListNotice");	
		UserListNoticeData.Builder builder=UserListNoticeData.newBuilder();
		User user;
		for (Iterator<Entry<Integer, User>> er = ((Room)data).getUserList().entrySet().iterator();er.hasNext();){
			user=er.next().getValue();
			DataStructures.User.Builder userBuilder=DataStructures.User.newBuilder();
			userBuilder.setId(user.getId());
			userBuilder.setName(user.getName());
			
			user.addProperties(userBuilder);
			
			builder.addUser(userBuilder);	
		}
		UserListNoticeData message=builder.build();
		
		Server.sendResponse(MessageHandler.UserListNotice_VALUE,ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.UserListNotice_VALUE;
	}

}
