package net.combatserver.messagehandler;

import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ZoneMessage.ZoneMessageRequestData;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class ZoneMessageRequestHandler {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.ZoneMessageRequest_VALUE+"]ZoneMessageRequest");	

		User sender=User.get(channel);
		Message mesage=new Message(((ZoneMessageRequestData)data).getMessage(),sender,MessageRouter.ROUTER_ZONE_VALUE);
		sender.sendToZone(mesage);
		return MessageHandler.ZoneMessageRequest_VALUE;
	}
}
