package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.JoinServer.JoinServerResponseData;
import net.combatserver.protobuf.Protocol;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class JoinServerResponseHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+Protocol.MessageHandler.JoinServerResponse_VALUE+"]JoinServerResponse");
		User user=(User)data;
		DataStructures.User.Builder userBuilder=DataStructures.User.newBuilder();
		userBuilder.setId(user.getId());
		userBuilder.setName(user.getName());
		user.addProperties(userBuilder);
		JoinServerResponseData.Builder builder=JoinServerResponseData.newBuilder();
		builder.setUser(userBuilder);
		builder.setPingInterval(Server.getPingInterval());
		JoinServerResponseData message=builder.build();
		Server.sendResponse(Protocol.MessageHandler.JoinServerResponse_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return Protocol.MessageHandler.JoinServerResponse_VALUE;
	}
}
