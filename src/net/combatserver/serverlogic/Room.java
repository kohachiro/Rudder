package net.combatserver.serverlogic;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.combatserver.core.AbstractServer;
import net.combatserver.core.ExtensionsManager;
import net.combatserver.core.RoomManager;
import net.combatserver.messagehandler.UserListNoticeHandler;
import net.combatserver.protobuf.DataStructures;
import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Plugin.PluginData;

/**
 * @author kohachiro
 * 
 */
public class Room {
	public final static String UserCountPropertyKey = "userCount";
	
	private final int id;
	private final String name;
	private final Zone zone;	
	private final int createrId;
	private final Map<String, String> properties;
	private final Map<Integer, User> userList;//
	private final CopyOnWriteArrayList<Integer> from;
	private final boolean visible;	
	private final boolean sendUserList;	
	private final int templateId;
	private final int maxUsers;
	private final String password;

	private volatile boolean locked;//
	private ScheduledFuture<?> scheduledFuture;
	private ScheduledFuture<?> closeFuture;	
	private Scheduled scheduled;
	private int spectatorLimit;//not in used
	/**
	 * 
	 */
	public Room(int id, String name,boolean visible,boolean sendUserList,int templateId,int maxUsers,String password,Zone zone,int createrId) {
		this.id=id;
		this.name=name;
		this.zone=zone;
		this.createrId=createrId;		
		this.visible=visible;
		this.sendUserList=sendUserList;
		this.maxUsers=maxUsers;
		this.password=password;	
		this.templateId=templateId;
		from  = new CopyOnWriteArrayList<Integer>();
		userList = new ConcurrentHashMap<Integer, User>();		
		properties = new ConcurrentHashMap<String, String>();
	}
	public static Room get(int id) {
		return RoomManager.getRoom(id);
	}



	/**
	 * @return
	 */
	public int getId() {
		return id;
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
	public Map<String, String> getProperties() {
		return properties;
	}
	/**
	 * @param value
	 */
	public void addFrom(int value) {
		from.add(value);
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
	 * @param value
	 * @throws Exception 
	 */
	public void setProperty(String key, String value) throws Exception {
		properties.remove(key);
		properties.put(key, value);
		Properties data=new Properties(this.id);
		data.addProperty(key, value);
		RoomManager.roomPropertyNotice(data,this,-1);
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
	public Map<Integer, User> getUserList() {
		return userList;
	}

	/**
	 * @return
	 */
	public int getUserNumber() {
		return userList.size();
	}

	/**
	 * @return
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * @return
	 */
	public int getMaxUsers() {
		return maxUsers;
	}

	/**
	 * @param userID
	 * @return
	 */
	public User getUser(int userID) {
		return userList.get(userID);
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	public int getCreaterId() {
		return createrId;
	}	

	/**
	 * @return
	 */
	public int getSpectatorLimit() {
		return spectatorLimit;
	}

	/**
	 * @param spectatorLimit
	 */
	public void setSpectatorLimit(int spectatorLimit) {
		this.spectatorLimit = spectatorLimit;
	}

	/**
	 * @return
	 */
	public boolean isLocked() {
		return locked;
	}


	/**
	 * @param locked
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	/**
	 * @return the templateId
	 */
	public Template getTemplate() {
		return RoomManager.getTemplate(this.templateId);
	}
	/**
	 * @return the templateId
	 */
	public int getTemplateId() {
		return templateId;
	}
	/**
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * @return the sendUserList
	 */
	public boolean isSendUserList() {
		return sendUserList;
	}
	/**
	 * @return
	 */
	public boolean isFull() {
		if (userList.size() >= maxUsers)
			return true;
		return false;
	}
	/**
	 * @return the scheduledFuture
	 */
	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
	}
	/**
	 * @param scheduledFuture the scheduledFuture to set
	 */
	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}
	
	/**
	 * @return the scheduled
	 */
	public Scheduled getScheduled() {
		return scheduled;
	}
	/**
	 * @param scheduled the scheduled to set
	 */
	public void setScheduled(Scheduled scheduled) {
		this.scheduled = scheduled;
	}	

	public void closeScheduled() {
		if (scheduledFuture!=null){
			scheduledFuture.cancel(true);
			scheduledFuture=null;
		}
		if (closeFuture!=null){
			closeFuture.cancel(true);
			closeFuture=null;
		}
	}	
	/**
	 * 
	 */
	public void runScheduled() {
		//TODO System
		System.out.println("scheduled:" + scheduled + "ping:"
				+ scheduled.register());
		scheduledFuture = AbstractServer.scheduledPool.scheduleAtFixedRate(
				scheduled, scheduled.register(), scheduled.register(),
				TimeUnit.MILLISECONDS);
		if (ExtensionsManager.scheduledTimeout > 0)
			closeFuture = AbstractServer.scheduledPool.schedule(new Runnable() {
				public void run() {
					scheduledFuture.cancel(true);
				}
			}, ExtensionsManager.scheduledTimeout, TimeUnit.SECONDS);
	}
	public void newUser(User user) throws Exception {
		user.setRoom(this);
		getUserList().put(user.getId(), user);
		newUserNotice(user);
		userListNotice(user);
		if (scheduled!=null)
			scheduled.onNewUser(user);

	}
	/**
	 * @param userId
	 * @throws Exception
	 */
	public void removeUser(int userId) throws Exception {
		if (getUserList().size() < 2 && createrId!=Server.getSystemUser().getId()) {
			RoomManager.removeRoom(this);
			removeRoomNotice();
		} else {
			getUserList().remove(userId);
			if (scheduled!=null)
				scheduled.onRemoveUser(userId);			
			removeUserNotice(userId);
		}		
	}
	/**
	 * @throws Exception
	 */
	private void removeRoomNotice() throws Exception {
		if (isVisible()){
			if (getZone().getSendRoomChangeTo() > 0) {
				Room room = get(getZone().getSendRoomChangeTo());
				if (getId() != room.getId()) {
				    RoomManager.removeRoomNoticeInvoke(room, getId());
				}
			}
		}
	}
	/**
	 * @param userId
	 * @throws Exception
	 */
	public void removeUserNotice(int userId) throws Exception {
		if (isSendUserList()) {
			RoomManager.removeUserNoticeInvoke(this, userId);
		}
		if (isVisible()){
			RoomManager.userCountChange(this, userId);
		}	
	}
	public void userListNotice(User sender) throws Exception {
		if (isSendUserList())
			UserListNoticeHandler.invoke(this, sender.getChannel());		
	}
	/**
	 * @param sender
	 * @throws Exception
	 */
	public void newUserNotice(User sender) throws Exception {
		if (isSendUserList()&&sender.getId()!=getCreaterId()){
				RoomManager.newUserNoticeInvoke(this, sender);
		}
		if (isVisible()){
			RoomManager.userCountChange(this, sender.getId());
		}
	}

	/**
	 * @param message
	 * @throws Exception 
	 */
	public void pluginNotice(int userId,PluginData message) throws Exception {
		RoomManager.pluginNoticeInvoke(this,userId,message);
	}	
	/**
	 * @param user
	 * @throws Exception
	 */
	public void newRoomNotice(int createrId) throws Exception {
		System.out.print(isVisible());
		if (isVisible()) {
			System.out.print(zone.getSendRoomChangeTo());
			if (zone.getSendRoomChangeTo() > 0) {
				Room toRoom = get(zone.getSendRoomChangeTo());
				System.out.print(toRoom);
				System.out.print(this);
				if (!this.equals(toRoom)) {
					RoomManager.newRoomNoticeInvoke(toRoom, this, createrId);
				}
			}
		}
	}
	/**
	 * @param properties
	 * @param id
	 * @throws Exception
	 */
	public void userPropertyNotice(Properties properties, int id) throws Exception {
		RoomManager.userPropertyNotice(properties, this, id);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", properties="
				+ properties + ", maxUsers=" + maxUsers + "]";
	}
	/**
	 * @param roomBuilder
	 */
	public void addProperties(DataStructures.Room.Builder roomBuilder) {
		Property.Builder propertyBuilder;
		Entry<String, String> entry;
		for (Iterator<Entry<String, String>> it = properties.entrySet().iterator();it.hasNext();){
			entry=it.next();
			propertyBuilder=Property.newBuilder();
			propertyBuilder.setName(entry.getKey());
			propertyBuilder.setValue(entry.getValue());
			roomBuilder.addProperties(propertyBuilder);
		}
	}
	/**
	 * @param roomBuilder
	 */
	public void addCountProperty(DataStructures.Room.Builder roomBuilder) {
		Property.Builder propertyBuilder=Property.newBuilder();
		propertyBuilder.setName(UserCountPropertyKey);
		propertyBuilder.setValue(String.valueOf(getUserList().size()));
		roomBuilder.addProperties(propertyBuilder);
	}	
	/**
	 * @param message
	 * @throws Exception
	 */
	public void sendToRoom(Message message) throws Exception {
		RoomManager.sendToRoom(message, this);
	}





}
