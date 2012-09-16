package net.combatserver.serverlogic;

/**
 * @author kohachiro
 *
 */
public abstract class Module {

	/**
	 * 
	 */
	public Module() {
		
	}
	/**
	 * @return
	 */
	public String type(){
		return "Module";
	}
	/**
	 * @param data
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	public abstract int invoke(Object data, Object channel) throws Exception ;
	/**
	 * @return
	 */
	public abstract int register();
}
