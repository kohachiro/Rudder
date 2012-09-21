package net.combatserver.serverlogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.combatserver.core.RoomManager;

/**
 * @author kohachiro
 * 
 */
public class Template {
	private final int id;
	private final String name;
	private final Map<String, String> properties;
	private final String scheduledClass;
	private final int maxUsers;
	private final Region region;

	/**
	 * 
	 */
	public Template(int id,String name,Region region,String scheduledClass,int maxUsers) {
		this.id = id;
		this.name = name;
		this.region = region;
		this.scheduledClass = scheduledClass;
		this.maxUsers = maxUsers;
		properties = new ConcurrentHashMap<String, String>();		
	}
	/* (non-Javadoc)
	 * @see com.sunkey.core.RoomManager#createRoom(net.com.sunkey.serverlogic.Room,net.com.sunkey.serverlogic.User,java.lang.String,java.lang.String)
	 */	
	public Room createRoom (User user, String name,String password) {
		return RoomManager.createRoom(this,user,name,password);
	}
	/* (non-Javadoc)
	 * @see com.sunkey.core.RoomManager#getTemlate(int)
	 */	

	public static Template get(int id) {
		return RoomManager.getTemplate(id);
	}
	/**
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return
	 */
	public Region getRegion() {
		return this.region;
	}

	/**
	 * @return
	 */
	public int getMaxUsers() {
		return maxUsers;
	}
	
	/**
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.get(key);
	}

	/**
	 * @return
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @return the scheduledClass
	 */
	public String getScheduledClass() {
		return scheduledClass;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Template [id=" + id + ", name=" + name + ", properties="
				+ properties + "]";
	}


}
