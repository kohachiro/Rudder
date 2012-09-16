package net.combatserver.serverlogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kohachiro
 *
 */
public abstract class Scheduled implements Runnable,Cloneable{
	private Room room;
	private final Map<String, String> properties;
	private AtomicInteger times;	
	/**
	 * 
	 */
	public Scheduled() {
		properties= new ConcurrentHashMap<String, String>();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Scheduled clone(){
		// TODO Auto-generated method stub
		try {
			return (Scheduled)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;			
		}
	}	
	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		if (this.room==null)
			this.room = room;
		else
			System.out.println("only be set Room once");
	}
	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}
	/**
	 * @return
	 */
	public AtomicInteger getTimes() {
	    return times;
	}
	/**
	 * @param times
	 */
	public void setTimes(AtomicInteger times) {
	    this.times = times;
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
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	/**
	 * @return
	 */
	public String type(){
		return "Scheduled";
	}
	/**
	 * @return
	 */
	public abstract int register();	
	/**
	 * @param user
	 * @throws Exception
	 */
	public abstract void onNewUser(User user) throws Exception ;	
	/**
	 * @param userId
	 * @throws Exception
	 */
	public abstract void onRemoveUser(int userId) throws Exception ;	

}
