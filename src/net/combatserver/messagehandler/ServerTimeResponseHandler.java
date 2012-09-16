package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.Server;
import net.combatserver.util.DumpTools;

/**
 * @author kohachiro
 *
 */
public class ServerTimeResponseHandler {
	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ServerTimeResponse_VALUE+"]ServerTimeResponse");		
		System.out.println(System.currentTimeMillis());
		Server.sendResponse(MessageHandler.ServerTimeResponse_VALUE, ByteBuffer.wrap(Message.toByteArray(System.currentTimeMillis())), channel);
		return MessageHandler.ServerTimeResponse_VALUE;
	}

}
