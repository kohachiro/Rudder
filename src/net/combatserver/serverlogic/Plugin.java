package net.combatserver.serverlogic;

import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.protobuf.Plugin.PluginRequestData;



/**
 * @author kohachiro
 *
 */
public abstract class Plugin {
	/**
	 * 
	 */
	public Plugin() {

	}

	
	/**
	 * @return
	 */
	public String type(){
		return "Plugin";
	}

	
	/**
	 * @param data
	 * @param user
	 * @return 
	 * @throws Exception
	 */
	public abstract PluginData invoke(PluginRequestData data, User user) throws Exception ;
	/**
	 * @return
	 */
	public abstract String register();
}
