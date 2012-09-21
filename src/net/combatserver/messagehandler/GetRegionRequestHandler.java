package net.combatserver.messagehandler;

import net.combatserver.protobuf.GetRegion.GetRegionRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Region;

/**
 * @author kohachiro
 *
 */
public class GetRegionRequestHandler {
	/**
	 * 
	 */
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.GetRegionRequest_VALUE+"]GetRegionRequest");
		int regionId = ((GetRegionRequestData) data).getRegionid();	
		Region region=Region.get(regionId);	
		System.out.println(regionId);
		if (region==null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_REGION_NOT_FOUND_VALUE, channel);
		GetRegionResponseHandler.invoke(region,channel);
		return MessageHandler.GetRegionRequest_VALUE;

	}

}
