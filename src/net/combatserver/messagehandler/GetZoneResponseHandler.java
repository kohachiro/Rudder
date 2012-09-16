package net.combatserver.messagehandler;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.DataStructures.Zone;
import net.combatserver.protobuf.GetZone.GetZoneResponseData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;
/**
 * @author kohachiro
 *
 */
public class GetZoneResponseHandler {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.GetZoneResponse_VALUE+"]GetZoneResponse");
		net.combatserver.serverlogic.Zone zone=(net.combatserver.serverlogic.Zone)data;
		GetZoneResponseData.Builder builder=GetZoneResponseData.newBuilder();		
		Zone.Builder zoneBuilder=Zone.newBuilder();	
		zoneBuilder.setId(zone.getId());
		zoneBuilder.setName(zone.getName());
		Property.Builder propertyBuilder;
		Entry<String, String> entry;
		for (Iterator<Entry<String, String>> it = zone.getProperties().entrySet().iterator();it.hasNext();){
			entry=it.next();
			propertyBuilder=Property.newBuilder();
			propertyBuilder.setName(entry.getKey());
			propertyBuilder.setValue(entry.getValue());
			zoneBuilder.addProperties(propertyBuilder);
		}
		builder.setZone(zoneBuilder);
		RoomListNoticeHandler.invoke(zone, channel);
		
		GetZoneResponseData message=builder.build();
		
		Server.sendResponse(MessageHandler.GetZoneResponse_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		

		return MessageHandler.GetZoneResponse_VALUE;
	}
}
