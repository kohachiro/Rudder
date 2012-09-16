package net.combatserver.serverlogic;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.combatserver.core.RoomManager;
import net.combatserver.core.UserManager;
import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Plugin.PluginData;

/**
 * @author kohachiro
 * 
 */
public class User {
	private final int id;
	private final String name;
	private final Map<String, String> properties;
	private final Map<String, String> extProperties;	
	private final Object channel;
	private final CopyOnWriteArrayList<Room>  room;//
	
	private int teamId;//not in used

	/**
	 * 
	 */
	public User(int id, String name, Object channel) {
		room  = new CopyOnWriteArrayList<Room>();		
		properties = new ConcurrentHashMap<String, String>();
		extProperties = new ConcurrentHashMap<String, String>();
		this.id = id;
		this.name = name;
		this.channel = channel;
		this.teamId = id;
	}
	public static User get(int id) {
		return UserManager.getUser(id);
	}		
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.UserManager#getUser(java.lang.Object)
	 */
	public static User get(Object channel) {
		return UserManager.getUser(channel);
	}	

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param room
	 */
	public void setRoom(Room room) {
		if (this.room.isEmpty())
			this.room.add(0, room);
		else
			this.room.set(0, room);
	}

	/**
	 * @return
	 */
	public int getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public Object getChannel() {
		return channel;
	}

	/**
	 * @return
	 */
	public Room getRoom() {
		return room.get(0);
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
	 * @return
	 */
	public Map<String, String> getExtProperties() {
		return extProperties;
	}

	/**
	 * @param key
	 * @param value
	 */
	public void addExtProperty(String key, String value) {
		extProperties.put(key, value);
	}

	/**
	 * @param key
	 * @return
	 */
	public String getExtProperty(String key) {
		return extProperties.get(key);
	}

	/**
	 * @param key
	 */
	public void removeExtProperty(String key) {
		extProperties.remove(key);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", properties="
				+ properties + ", extProperties=" + extProperties 
				+ ", channel=" + channel.hashCode() + ", room=" + room.get(0)
				+ "]";
	}

	public String info() {
		return "User [id=" + id + ", name=" + name  + "]";
	}	
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.UserManager#checkCertificate(java.lang.String)
	 */	
//	public static boolean checkCertificate(String certificate) {
//		return UserManager.checkCertificate(certificate);
//	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.UserManager#login(java.lang.String,java.lang.Object)
	 */	
//	public static int login(String username,String password) {
//		return UserManager.login(username,password);
//	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.UserManager#login(java.lang.String,java.lang.Object)
	 */	
//	public static User get(int accountID, Object channel) {
//		return UserManager.get(accountID,channel);
//	}	
//	public static void loginLog(String username, String version,
//			String agent, String os, String pixel, String charset,
//			 int clientSpeed,Object channel) {
//		DBManager.loginLog(username, version, agent, os, pixel, charset, clientSpeed,channel);
//	}	
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.UserManager#sendToServer(net.com.sunkey.serverlogic.Message,net.com.sunkey.serverlogic.User)
	 */		
	public void sendToServer(Message mesage) throws Exception {
		UserManager.sendToServer(mesage, this);		
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.RoomManager#sendToZone(net.com.sunkey.serverlogic.Message,net.com.sunkey.serverlogic.User)
	 */	
	public void sendToZone(Message mesage) throws Exception {
		RoomManager.sendToZone(mesage, this);
		
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.RoomManager#sendToRoom(net.com.sunkey.serverlogic.Message,net.com.sunkey.serverlogic.User)
	 */		
	public void sendToRoom(Message mesage) throws Exception {
		RoomManager.sendToRoom(mesage, this);	
	}
	/**
	 * @param message
	 * @throws Exception
	 */
	public void pluginNotice(PluginData message) throws Exception {
		UserManager.pluginNoticeInvoke(message, getChannel());
	}	
	/**
	 * @param userBuilder
	 */
	public void addProperties(DataStructures.User.Builder userBuilder) {
		Property.Builder propertyBuilder;
		Entry<String, String> entry;
		for (Iterator<Entry<String, String>> it = properties.entrySet().iterator();it.hasNext();){
			entry=it.next();
			propertyBuilder=Property.newBuilder();
			propertyBuilder.setName(entry.getKey());
			propertyBuilder.setValue(entry.getValue());
			userBuilder.addProperties(propertyBuilder);
		}
	}
	public boolean kickUser(User user) throws Exception {
		if (user.equals(this))
			return false;
		if (getRoom()!=user.getRoom())
			return false;
		if (!getRoom().getClass().equals(this))
			return false;
		getRoom().removeUser(user.getId());
		Room toRoom=Room.get(Server.getDefaultRoomId());
		toRoom.newUser(user);
		return true;
	}
	public static User initByChannel(String name,Object channel) {
		User user=new User(channel.hashCode(),name,channel);
		user.initUser();
		return user;
	}
	public void initUser() {
		UserManager.initUser(this);
	}


}
