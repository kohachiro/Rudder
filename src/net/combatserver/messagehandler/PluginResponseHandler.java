package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class PluginResponseHandler {

	/**
	 * 
	 */
	public static int invoke(Object data, Object channel)  throws Exception {
		System.out.println("["+channel.hashCode()+"]["+Protocol.MessageHandler.PluginResponse_VALUE+"]PluginResponse");
		Server.sendResponse(Protocol.MessageHandler.PluginResponse_VALUE, (ByteBuffer)data, channel);
		return Protocol.MessageHandler.PluginResponse_VALUE;
	}

}
