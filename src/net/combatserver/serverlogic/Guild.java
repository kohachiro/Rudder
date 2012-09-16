package net.combatserver.serverlogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kohachiro
 * 
 */
public class Guild {
	private final int id;
	private final String name;
	private final Map<String, String> properties;
	private final Map<Integer, User> userList;
	public Guild(int id,String name) {
		properties = new ConcurrentHashMap<String, String>();
		userList = new ConcurrentHashMap<Integer, User>();
		this.id=id;
		this.name=name;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.get(key);
	}
	/**
	 * @param key
	 */
	public void removeProperty(String key) {
		properties.remove(key);
	}	
	/**
	 * @return the userList
	 */
	public Map<Integer, User> getUserList() {
		return userList;
	}
	
}
