package net.combatserver.messagehandler;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.UserPropertyUpdate.UserPropertyUpdateRequestData;
import net.combatserver.serverlogic.Properties;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class UserPropertyUpdateRequestHandler {

	/**
	 * 
	 */
	//@Override
	public static int invoke(Object data, Object channel)  throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.UserPropertyUpdateRequest_VALUE+"]UserPropertyUpdateRequest");
		UserPropertyUpdateRequestData property=((UserPropertyUpdateRequestData)data);
		if (property.getProperty().getName()!="" && property.getProperty().getValue()!=""){
			Properties properties=new Properties(channel.hashCode());
			properties.addProperty(property.getProperty().getName(), property.getProperty().getValue());
			User user=User.get(channel);
			user.getRoom().userPropertyNotice(properties, user.getId());
		}
		return MessageHandler.UserPropertyUpdateRequest_VALUE;
	}
}
