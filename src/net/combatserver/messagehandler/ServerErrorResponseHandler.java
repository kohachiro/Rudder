package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class ServerErrorResponseHandler{
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object obj, Object channel){
		System.out.println("["+channel.hashCode()+"]["+ MessageHandler.ServerErrorResponse_VALUE+"]ServerErrorResponse:"+ReturnStatus.valueOf((int)obj).name());
		try {
			Server.sendResponse(MessageHandler.ServerErrorResponse_VALUE, ByteBuffer.wrap(Message.toByteArray((int)obj)), channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.ServerErrorResponse_VALUE;
	}
}
