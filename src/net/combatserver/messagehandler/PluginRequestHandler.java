package net.combatserver.messagehandler;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.protobuf.Plugin.PluginRequestData;
import net.combatserver.protobuf.Plugin.PluginResponseData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Plugin;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class PluginRequestHandler {
	/**
	 * 
	 */
	public static int invoke(Object obj, Object channel)  throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.PluginRequest_VALUE+"]PluginRequest");		
		PluginRequestData data=(PluginRequestData)obj;				
		if (!data.hasName())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_PLUGIN_NOT_FOUND_VALUE, channel);
		if (!data.hasAction())
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_PLUGIN_ACTION_NOT_FOUND_VALUE, channel);		
		Plugin plugin=Server.getPlugin(data.getName());
		if (plugin==null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_PLUGIN_NOT_FOUND_VALUE, channel);
		User user=User.get(channel);
		
		Class<?> clazz=plugin.getClass();
		PluginData result;
		try {
			System.out.println(data.getName());
			System.out.println(data.getAction());
			//result = (List<String>) Reflection.invokeMethod(plugin, data.getAction(), new Object[]{data,user});
			Method method = clazz.getMethod(data.getAction(),new Class[] { PluginRequestData.class,User.class });
			result = (PluginData) method.invoke(plugin, new Object[]{data,user});
		} catch (java.lang.NoSuchMethodException e) {
			result=plugin.invoke(data, user);
		}
		System.out.println("PluginResponse:"+data.getName()+"."+data.getAction());
		if (result==null)
			return MessageHandler.PluginRequest_VALUE;
		
		PluginResponseData.Builder builder=PluginResponseData.newBuilder();
		builder.setData(result);
		PluginResponseData message=builder.build();		
		PluginResponseHandler.invoke(ByteBuffer.wrap(message.toByteArray()), user.getChannel());
		return MessageHandler.PluginRequest_VALUE;		
	}

}
