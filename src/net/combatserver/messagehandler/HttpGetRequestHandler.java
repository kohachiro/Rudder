package net.combatserver.messagehandler;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Server;

/**
 * @author kohachiro
 *
 */
public class HttpGetRequestHandler {
	/**
	 * 
	 */
	public static int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.HttpGetRequest_VALUE+"]HttpGetRequest");
		String crossDomain="<?xml version=\"1.0\"?>\n"
				+ "<!DOCTYPE cross-domain-policy SYSTEM \"http://www.adobe.com/xml/dtds/cross-domain-policy.dtd\">\n"
				+ "\t<cross-domain-policy>\n"
				//+ "\t\t<site-control permitted-cross-domain-policies=\"master-only\"/>"
				+ "\t\t<site-control permitted-cross-domain-policies=\"all\"/>"
				+ "\t\t<allow-access-from domain=\"*\" />\n"
				+ "\t\t<allow-http-request-headers-from domain=\"*\" headers=\"*\"/>"  
				+ "\t</cross-domain-policy>\0";
		Server.sendResponse(ByteBuffer.wrap(("HTTP/1.1 200 OK\n"
				+ "Content-Type: text/xml; charset=utf-8\n"
				+ "Content-Length: "+crossDomain.getBytes().length+"\n\n"
				+ crossDomain).getBytes()),channel,true);
		return MessageHandler.HttpGetRequest_VALUE;
	}
}
