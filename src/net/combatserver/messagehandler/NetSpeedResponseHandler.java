package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.NetSpeed.NetSpeedResponseData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class NetSpeedResponseHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.NetSpeedResponse_VALUE+"]NetSpeedResponse");
		NetSpeedResponseData.Builder builder=NetSpeedResponseData.newBuilder();	
		builder.setDelay((int)(System.currentTimeMillis()-((ByteBuffer)data).getLong())>>1);
		builder.setTime(System.currentTimeMillis());		
		NetSpeedResponseData message=builder.build();
		
		Server.sendResponse(MessageHandler.NetSpeedResponse_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);

		return MessageHandler.NetSpeedResponse_VALUE;
	}

}
