package net.combatserver.messagehandler;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.UserPropertyUpdate.UserPropertyUpdateNoticeData;
import net.combatserver.serverlogic.Properties;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class UserPropertyUpdateNoticeHandler {

	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel)  throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.UserPropertyUpdateNotice_VALUE+"]UserPropertyUpdateNotice");
		UserPropertyUpdateNoticeData.Builder builder=UserPropertyUpdateNoticeData.newBuilder();
		Properties properties=((Properties)data);
		builder.setId(properties.getId());
		Property.Builder propertyBuilder;
		Entry<String, String> entry;
		for (Iterator<Entry<String, String>> it = properties.getProperties().entrySet().iterator();it.hasNext();){
			entry=it.next();
			propertyBuilder=Property.newBuilder();
			propertyBuilder.setName(entry.getKey());
			propertyBuilder.setValue(entry.getValue());
			builder.addProperties(propertyBuilder);
		}		
		UserPropertyUpdateNoticeData message=builder.build();

		Server.sendResponse(MessageHandler.UserPropertyUpdateNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.UserPropertyUpdateNotice_VALUE;
	}
}
