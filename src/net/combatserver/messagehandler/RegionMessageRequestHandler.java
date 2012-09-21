package net.combatserver.messagehandler;

import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.RegionMessage.RegionMessageRequestData;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class RegionMessageRequestHandler {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.RegionMessageRequest_VALUE+"]RegionMessageRequest");	

		User sender=User.get(channel);
		Message mesage=new Message(((RegionMessageRequestData)data).getMessage(),sender,MessageRouter.ROUTER_REGION_VALUE);
		sender.sendToRegion(mesage);
		return MessageHandler.RegionMessageRequest_VALUE;
	}
}
