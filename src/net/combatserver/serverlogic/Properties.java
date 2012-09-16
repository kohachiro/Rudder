package net.combatserver.serverlogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kohachiro
 *
 */
public class Properties {
	private final int id;
	private final Map<String, String> properties;
	/**
	 * 
	 */
	public Properties(int id) {
		this.id = id;
		properties = new ConcurrentHashMap<String, String>();
	}
	/**
	 * @return
	 */
	public int getId() {
		return id;
	}	
	/**
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}
	/**
	 * @param key
	 */
	public void removeProperty(String key) {
		properties.remove(key);
	}
	/**
	 * @return
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	/**
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.get(key);
	}
}
