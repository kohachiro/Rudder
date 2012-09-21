package net.combatserver.messagehandler;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.DataStructures.Region;
import net.combatserver.protobuf.GetRegion.GetRegionResponseData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;
/**
 * @author kohachiro
 *
 */
public class GetRegionResponseHandler {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.GetRegionResponse_VALUE+"]GetRegionResponse");
		net.combatserver.serverlogic.Region region=(net.combatserver.serverlogic.Region)data;
		GetRegionResponseData.Builder builder=GetRegionResponseData.newBuilder();		
		Region.Builder regionBuilder=Region.newBuilder();	
		regionBuilder.setId(region.getId());
		regionBuilder.setName(region.getName());
		Property.Builder propertyBuilder;
		Entry<String, String> entry;
		for (Iterator<Entry<String, String>> it = region.getProperties().entrySet().iterator();it.hasNext();){
			entry=it.next();
			propertyBuilder=Property.newBuilder();
			propertyBuilder.setName(entry.getKey());
			propertyBuilder.setValue(entry.getValue());
			regionBuilder.addProperties(propertyBuilder);
		}
		builder.setRegion(regionBuilder);
		RoomListNoticeHandler.invoke(region, channel);
		
		GetRegionResponseData message=builder.build();
		
		Server.sendResponse(MessageHandler.GetRegionResponse_VALUE, ByteBuffer.wrap(message.toByteArray()), channel);
		

		return MessageHandler.GetRegionResponse_VALUE;
	}
}
