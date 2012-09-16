package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.UserLeave.UserLeaveNoticeData;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class UserLeaveNoticeHandler {
	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.UserLeaveNotice_VALUE+"]UserLeaveNotice");	
		UserLeaveNoticeData.Builder builder=UserLeaveNoticeData.newBuilder();
		builder.setUserid((int)data);	
		UserLeaveNoticeData message=builder.build();
		
		Server.sendResponse(MessageHandler.UserLeaveNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.UserLeaveNotice_VALUE;
	}
}
