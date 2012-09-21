package net.combatserver.serverlogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.combatserver.core.RoomManager;

/**
 * @author kohachiro
 * 
 */
public class Region {
	private final int id;
	private final String name;
	private final int sendRoomChangeTo;
	private final boolean sendUserCountChange;
	private final ConcurrentHashMap<String, String> properties;
	private final ConcurrentHashMap<Integer, Room> roomList;
	private final boolean createRoom;
	private final int maxRoom;	
	/**
	 * 
	 */
	public Region(int id, String name, int sendRoomChangeTo, boolean sendUserCountChange,boolean createRoom,int maxRoom) {
		this.id=id;
		this.name=name;
		this.sendRoomChangeTo=sendRoomChangeTo;
		this.sendUserCountChange=sendUserCountChange;
		properties = new ConcurrentHashMap<String, String>();
		roomList = new ConcurrentHashMap<Integer, Room>();
		this.createRoom=createRoom;
		this.maxRoom=maxRoom;
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.RoomManager#getRegion(int)
	 */		
	public static Region get(int regionId) {
		return RoomManager.getRegion(regionId);
	}	
	/**
	 * 是否满  full 将导致能创建新房间
	 * @return
	 */
	public boolean isFull() {
		if (getMaxRoom() <= getRoomList().size())
			return true;
		return false;
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
	public boolean isCreateRoom() {
		return createRoom;
	}



	/**
	 * @return
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	/**
	 * @param room
	 */
	public void addRoom(Room room) {
		roomList.put(room.getId(), room);
	}
	/**
	 * @param id
	 */
	public void getRoom(int id) {
		roomList.get(id);
	}
	/**
	 * @param id
	 */
	public void removeRoom(int id) {
		roomList.remove(id);
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
	public Map<Integer, Room> getRoomList() {
		return roomList;
	}

	/**
	 * @return
	 */
	public int getMaxRoom() {
		return maxRoom;
	}



	/**
	 * @return
	 */
	public int getSendRoomChangeTo() {
		return sendRoomChangeTo;
	}

	/**
	 * @return
	 */
	public boolean isSendUserCountChange() {
		return sendUserCountChange;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Region [id=" + id + ", name=" + name + ", properties="
				+ properties + "]";
	}





}
