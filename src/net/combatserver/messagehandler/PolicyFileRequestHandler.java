/**
 * 
 */
package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;

/**
 * @author Administrator
 *
 */
public class PolicyFileRequestHandler {

	/**
	 * 
	 */
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.PolicyFileRequest_VALUE+"]PolicyFileRequest");
		String crossDomain="<?xml version=\"1.0\"?>\n"
				//+ "<!DOCTYPE cross-domain-policy SYSTEM \"http://www.adobe.com/xml/dtds/cross-domain-policy.dtd\">\n"
				+ "\t<cross-domain-policy>\n"
				+ "\t\t<site-control permitted-cross-domain-policies=\"all\"/>"
				+ "\t\t<allow-access-from domain=\"*\" to-ports=\"*\"/>\n"
				//+ "\t\t<allow-http-request-headers-from domain=\"*\" headers=\"*\"/>"  
				+ "\t</cross-domain-policy>\0";
		Server.sendResponse(ByteBuffer.wrap(crossDomain.getBytes()),channel,true);		
		return MessageHandler.PolicyFileRequest_VALUE;
	}
}
