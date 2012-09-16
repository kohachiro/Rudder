package serverlogic;

import java.nio.ByteBuffer;

import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.serverlogic.Module;
import net.combatserver.serverlogic.Server;
/**
 * @author kohachiro
 *
 */
public class ModuleDemo extends Module{
	/**
	 * 
	 */
	public ModuleDemo() {
		
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.serverlogic.Module#invoke(java.lang.Object, java.lang.Object)
	 */
	@Override	
	public int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.HttpGetRequest_VALUE+"]ServerlogicHttpGetRequest");
		Server.sendResponse(ByteBuffer.wrap(("HTTP/1.1 200 OK\n"
				+ "Content-Type: text/html; charset=utf-8\n"
				+ "Content-Length: 20\n\n"
				+ "<h1>HelloWorld</h1>").getBytes()),channel,true);
		return MessageHandler.HttpGetRequest_VALUE;
	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.serverlogic.Module#register()
	 */
	@Override	
	public int register(){
		return MessageHandler.HttpGetRequest_VALUE;
	}
}
