package serverlogic;

import java.util.Map;

import net.combatserver.messagehandler.JoinServerResponseHandler;
import net.combatserver.messagehandler.ServerErrorResponseHandler;
import net.combatserver.protobuf.JoinServer.JoinServerRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.serverlogic.Module;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * @author kohachiro
 *
 */
public class ModuleJoinServer extends Module {
	/**
	 * 
	 */
	public ModuleJoinServer() {
		
	}
	@Override
	public int invoke(Object data, Object channel) throws Exception {
		System.out.println("["+channel.hashCode()+"]["+MessageHandler.JoinServerRequest_VALUE+"]ServerlogicJoinServerRequest");
		JoinServerRequestData requestData=(JoinServerRequestData)data;
		String playerId=requestData.getUsername();
		String token=requestData.getPassword();
		System.out.println(" playerId: "+playerId);
		System.out.println(" token : "+token);
		JedisPool pool=(JedisPool)Server.getEntity("JedisPool");
		Jedis jedis = pool.getResource();
		String tokenValue = jedis.get(playerId+"_Token");
		
		if (tokenValue==null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_AUTH_FAILED_VALUE, channel);		
		if (!tokenValue.equals(token))
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_AUTH_FAILED_VALUE, channel);
		
		Map<String, String> player = jedis.hgetAll(playerId+"_Player");
		System.out.println(player.toString());
		
		if (player.get("id")==null||player.get("name")==null)
			return ServerErrorResponseHandler.invoke(ReturnStatus.STATUS_AUTH_FAILED_VALUE, channel);
		//User user=new User(player.get("id").hashCode(),player.get("name"),channel);
		User user=new User(channel.hashCode(),player.get("name"),channel);
		user.addProperty("village_exp", player.get("village_exp"));
		user.addProperty("village_level", player.get("village_level"));
		user.addExtProperty("id", player.get("id"));		
		user.initUser();
		JoinServerResponseHandler.invoke(user,channel);
		return MessageHandler.JoinServerRequest_VALUE;
	}

	@Override
	public int register() {
		// TODO Auto-generated method stub
		return MessageHandler.JoinServerRequest_VALUE;
	}

}
