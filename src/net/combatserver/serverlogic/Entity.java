package net.combatserver.serverlogic;

/**
 * @author kohachiro
 *
 */
public abstract class Entity {

	/**
	 * 
	 */
	public Entity() {
		
	}
	/**
	 * @return
	 */
	public abstract Object invoke();	
	/**
	 * @return
	 */
	public String type(){
		return "Entity";
	}

	/**
	 * @return
	 */
	public abstract String register();
}
