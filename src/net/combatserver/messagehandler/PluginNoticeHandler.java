package net.combatserver.messagehandler;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.protobuf.Plugin.PluginNoticeData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class PluginNoticeHandler {

	/**
	 * 
	 */
	public static int invoke(Object data, Object channel)  throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.PluginNotice_VALUE+"]PluginNotice");
		PluginNoticeData.Builder builder=PluginNoticeData.newBuilder();
		PluginData pluginData=(PluginData)data;
		builder.setData(pluginData);		
		System.out.println("PluginNotice:"+pluginData.getName()+"."+pluginData.getAction());
		PluginNoticeData message=builder.build();
		Server.sendResponse(MessageHandler.PluginNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.PluginNotice_VALUE;
	}


}
