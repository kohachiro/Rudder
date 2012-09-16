package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.NetSpeed.NetSpeedRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.Server;


/**
 * @author kohachiro
 *
 */
public class NetSpeedRequestHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.NetSpeedRequest_VALUE+"]NetSpeedRequest");
		NetSpeedResponseHandler.invoke(data, channel);
		return MessageHandler.NetSpeedRequest_VALUE;
	}

}
