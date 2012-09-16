/**
 * 
 */
package serverlogic;

import java.util.concurrent.CopyOnWriteArrayList;
import net.combatserver.serverlogic.Entity;

/**
 * @author kohachiro
 *
 */
public class EntityCombatRoom1V1 extends Entity {

	/**
	 * 
	 */
	public EntityCombatRoom1V1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object invoke() {
		// TODO Auto-generated method stub
		return new CopyOnWriteArrayList<Integer>();
	}

	@Override
	public String register() {
		// TODO Auto-generated method stub
		return 	"CombatRoom1V1";
	}
}
