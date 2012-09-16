package net.combatserver.serverlogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kohachiro
 * 
 */
public class Team {
	private final int id;
	private final String name;
	private final Map<String, String> properties;
	private final Map<Integer, User> users;

	/**
	 * 
	 */
	public Team(int id, String name) {
		this.id = id;
		this.name = name;
		properties = new ConcurrentHashMap<String, String>();
		users = new ConcurrentHashMap<Integer, User>();

	}

	/**
	 * @return
	 */
	public Map<String, String> getProperties() {
		return properties;
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
	 * @param message
	 */
	public void send(Message message) {

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", properties="
				+ properties + ", users=" + users + "]";
	}

}
