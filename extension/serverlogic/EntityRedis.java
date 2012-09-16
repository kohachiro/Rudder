/**
 * 
 */
package serverlogic;

import net.combatserver.serverlogic.Entity;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author kohachiro
 *
 */
public class EntityRedis extends Entity {

	/**
	 * 
	 */
	public EntityRedis() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object invoke() {
		// TODO Auto-generated method stub
		return new JedisPool(new JedisPoolConfig(), "localhost");
	}

	@Override
	public String register() {
		// TODO Auto-generated method stub
		return "JedisPool";
	}


}
