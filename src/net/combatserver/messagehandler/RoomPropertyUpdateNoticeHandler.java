package net.combatserver.messagehandler;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.RoomPropertyUpdate.RoomPropertyUpdateNoticeData;
import net.combatserver.serverlogic.Properties;
import net.combatserver.serverlogic.Server;
/**
 * @author kohachiro
 *
 */
public class RoomPropertyUpdateNoticeHandler {

	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel)  throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.RoomPropertyUpdateNotice_VALUE+"]RoomPropertyUpdateNotice");
		RoomPropertyUpdateNoticeData.Builder builder=RoomPropertyUpdateNoticeData.newBuilder();
		Properties properties=((Properties)data);
		builder.setId(properties.getId());

		Property.Builder propertyBuilder;
		Entry<String, String> entry;		
		for (Iterator<Entry<String, String>> er = properties.getProperties().entrySet().iterator();er.hasNext();){
			entry=er.next();
			propertyBuilder=Property.newBuilder();
			propertyBuilder.setName(entry.getKey());
			propertyBuilder.setValue(entry.getValue());
			builder.addProperties(propertyBuilder);
		}		
		RoomPropertyUpdateNoticeData message=builder.build();

		Server.sendResponse(MessageHandler.RoomPropertyUpdateNotice_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		return MessageHandler.RoomPropertyUpdateNotice_VALUE;
	}
}
