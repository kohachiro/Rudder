package net.combatserver.messagehandler;

import net.combatserver.protobuf.ServerError.ReturnStatus;


/**
 * @author kohachiro
 *
 */
public class InvidRequestHandler {

	/* (non-Javadoc)
	 * @see com.sunkey.tdserver.messagehandler.MessageHandler#invoke(java.lang.Object, org.jboss.netty.channel.Channel)
	 */
	//@Override
	public static int invoke(Object obj, Object channel) {
		System.out.println("["+channel.hashCode()+"][0]InvidRequestHandler");
		return ServerErrorResponseHandler.invoke(
				ReturnStatus.STATUS_INVID_REQUEST_VALUE, channel);
	}
}
