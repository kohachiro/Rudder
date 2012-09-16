package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.NewUser.NewUserNoticeData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class NewUserNoticeHandler  {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.NewUserNotice_VALUE+"]NewUserNotice");
		User user=(User)data;
		DataStructures.User.Builder userBuilder=DataStructures.User.newBuilder();
		userBuilder.setId(user.getId());
		userBuilder.setName(user.getName());
		
		user.addProperties(userBuilder);

		NewUserNoticeData.Builder builder=NewUserNoticeData.newBuilder();
		builder.setUser(userBuilder);	
		NewUserNoticeData message=builder.build();
		
		Server.sendResponse(MessageHandler.NewUserNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.NewUserNotice_VALUE;
	}
}
