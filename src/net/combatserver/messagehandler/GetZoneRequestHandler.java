package net.combatserver.messagehandler;

import net.combatserver.protobuf.GetZone.GetZoneRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Zone;

/**
 * @author kohachiro
 *
 */
public class GetZoneRequestHandler {
	/**
	 * 
	 */
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.GetZoneRequest_VALUE+"]GetZoneRequest");
		int zoneId = ((GetZoneRequestData) data).getZoneid();	
		Zone zone=Zone.get(zoneId);	
		System.out.println(zoneId);
		if (zone==null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_ZONE_NOT_FOUND_VALUE, channel);
		GetZoneResponseHandler.invoke(zone,channel);
		return MessageHandler.GetZoneRequest_VALUE;

	}

}
