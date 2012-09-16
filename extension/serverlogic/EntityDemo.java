/**
 * 
 */
package serverlogic;

import net.combatserver.serverlogic.Entity;

/**
 * @author kohachiro
 *
 */
public class EntityDemo extends Entity {

	/**
	 * 
	 */
	public EntityDemo() {
		// TODO Auto-generated constructor stub		
	}


	/* (non-Javadoc)
	 * @see net.combatserver.serverlogic.Entity#register()
	 */
	@Override
	public String register() {
		// TODO Auto-generated method stub
		return "Hello";
	}

	@Override
	public Object invoke() {
		// TODO Auto-generated method stub
		return "Hello World";
	}
}
