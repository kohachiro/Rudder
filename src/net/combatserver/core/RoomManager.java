package net.combatserver.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.combatserver.messagehandler.NewMessageNoticeHandler;
import net.combatserver.messagehandler.NewRoomNoticeHandler;
import net.combatserver.messagehandler.NewUserNoticeHandler;
import net.combatserver.messagehandler.PluginNoticeHandler;
import net.combatserver.messagehandler.RoomPropertyUpdateNoticeHandler;
import net.combatserver.messagehandler.RoomRemoveNoticeHandler;
import net.combatserver.messagehandler.UserLeaveNoticeHandler;
import net.combatserver.messagehandler.UserPropertyUpdateNoticeHandler;
import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.Properties;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Scheduled;
import net.combatserver.serverlogic.Template;
import net.combatserver.serverlogic.User;
import net.combatserver.serverlogic.Zone;

/**
 * ROOM管理类<br/>
 * 实现对room的添加删除 群发消息等功能<br/>
 * @author kohachiro
 * 
 */
public class RoomManager {
	/**
	 * roomID 
	 */
	static AtomicInteger roomid = new AtomicInteger(0);
	/**
	 * room map 通过 id 查找
	 */
	final static Map<Integer, Room> rooms = new ConcurrentHashMap<Integer, Room>();
	/**
	 * zoom map 通过 id 查找
	 */
	final static Map<Integer, Zone> zones = new ConcurrentHashMap<Integer, Zone>();
	/**
	 * template map 通过 id 查找
	 */
	final static Map<Integer, Template> templates = new ConcurrentHashMap<Integer, Template>();

	/**
	 * 
	 */
	public RoomManager() {
		
	}
	/**
	 * 创建新template
	 * @param template
	 */
	public static void initTemplate(Template template) {
		templates.put(template.getId(), template);
		// System.out.println("new  Template :\t[ID:" + template.getId()
		// + ",name:" + template.getName() + "]");
		System.out.println(template.toString());
	}
	/**
	 * 创建新Zone
	 * @param zone
	 */
	public static void initNewZone(Zone zone) {
		zones.put(zone.getId(), zone);
		System.out.println(zone.toString());
	}
	/**
	 * 创建新Room
	 * @param room
	 */
	public static void initNewRoom(Room room) {
		//update roomid from the key "room.id" in file "config.xml".
		roomid.getAndSet(room.getId());
		addRoom(room);
	}
	
	/**
	 * 创建动态房间(使用模板创建房间)
	 * @param template
	 * @param user
	 * @param name
	 * @param password
	 * @return
	 */
	public static Room createRoom(Template template, User user, String name,
			String password) {
		Room room = new Room(roomid.incrementAndGet(), name, true, true, template.getId(), template.getMaxUsers(), password, template.getZone(), user.getId());
		//copy template properties to room properties.
		Entry<String, String> entry;
		for (Iterator<Entry<String, String>> it = template.getProperties().entrySet().iterator(); it
				.hasNext();) {
			entry=it.next();
			room.addProperty(entry.getKey(),entry.getValue());
		}
		room.setLocked(false);
		room.setSpectatorLimit(0);
		//create scheduled instance when room with a scheduled setting.
		if (template.getScheduledClass()!=""){			
			try {
				Scheduled instance = ExtensionsManager.getScheduled(template.getScheduledClass());
				Scheduled clone = instance.clone();
				clone.setRoom(room);
				room.setScheduled(clone);
				System.out.println("setScheduled");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		addRoom(room);
		return room;
	}
	/**
	 * 添加房间 设置room对象与  zone rooms 关联
	 * @param room
	 */
	private static void addRoom(Room room) {
		room.getZone().addRoom(room);
		rooms.put(room.getId(), room);
		System.out.println(room.toString());
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Zone getZone(int id) {
		return zones.get(id);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Room getRoom(int id) {
		return rooms.get(id);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Template getTemplate(int id) {
		return templates.get(id);
	}

	/**
	 * 如果Zone  设置了net.com.sunkey.serverlogic.Zone.sendUserCountChange
	 * 和  net.com.sunkey.serverlogic.Zone.sendRoomChangeTo
	 * 将会把Zone内部所有 Room人数变化发送到 sendRoomChangeTo指定的房间
	 * @param room
	 * @param userId
	 * @throws Exception
	 */
	public static void userCountChange(Room room, int userId) throws Exception {
		if (room.getZone().isSendUserCountChange()) {
			Properties properties = new Properties(room.getId());
			properties.addProperty(Room.UserCountPropertyKey, String.valueOf(room.getUserNumber()));
			roomPropertyNotice(properties, getRoom(room.getZone()
					.getSendRoomChangeTo()), userId);
		}
	}
	/**
	 * 移除房间
	 * @param room
	 * @throws Exception
	 */
	public static void removeRoom(Room room) throws Exception {
		room.closeScheduled();
		rooms.remove(room.getId());
		room.getZone().removeRoom(room.getId());
		System.out.println("Rooms:" + rooms.size());
	}
	

	/**
	 * 用户属性变更通知
	 * @param properties
	 * @param toRoom
	 * @param senderId
	 * @throws Exception
	 */
	public static void userPropertyNotice(Properties properties, Room toRoom, int senderId) throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = toRoom.getUserList().entrySet().iterator(); it
				.hasNext();) {
			user = it.next().getValue();
			if (senderId != user.getId()) {
				UserPropertyUpdateNoticeHandler.invoke(properties,
						user.getChannel());
			}
		}
	}	
	/**
	 * 房间属性变更通知
	 * @param properties
	 * @param toRoom
	 * @param senderId
	 * @throws Exception
	 */
	public static void roomPropertyNotice(Properties properties, Room toRoom, int senderId) throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = toRoom.getUserList().entrySet().iterator(); it
				.hasNext();) {
			user = it.next().getValue();
			if (senderId != user.getId()) {
				RoomPropertyUpdateNoticeHandler.invoke(properties,
						user.getChannel());
			}
		}
	}

	/**
	 * @param room
	 * @param message
	 * @throws Exception
	 */
	public static void pluginNoticeInvoke(Room room, int senderId,PluginData message) throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = room.getUserList().entrySet().iterator(); it
				.hasNext();) {
			user = it.next().getValue();
			if (senderId != user.getId())
				PluginNoticeHandler.invoke(message, user.getChannel());
		}
	}
	/**
	 * room所有用户群发新用户通知
	 * @param room
	 * @param sender
	 * @throws Exception
	 */
	public static void newUserNoticeInvoke(Room room, User sender) throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = room.getUserList().entrySet().iterator(); it
				.hasNext();) {
			user = it.next().getValue();
			if (sender.getId() != user.getId())
				NewUserNoticeHandler.invoke(sender, user.getChannel());
		}
	}

	/**
	 * room所有用户群发移除用户通知
	 * @param room
	 * @param userId
	 * @throws Exception
	 */
	public static void removeUserNoticeInvoke(Room room, int userId) throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = room.getUserList().entrySet().iterator(); it
				.hasNext();) {
			user = it.next().getValue();
			if (userId != user.getId())
				UserLeaveNoticeHandler.invoke(userId, user.getChannel());
		}
	}

	/**
	 * 对目标房间群发新房间通知
	 * @param toRoom
	 * @param newRoom
	 * @param createrId
	 * @throws Exception
	 */
	public static void newRoomNoticeInvoke(Room toRoom, Room newRoom, int createrId)
			throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> er = toRoom.getUserList().entrySet().iterator(); er
				.hasNext();) {
			user = er.next().getValue();
			if (createrId != user.getId()) {
				NewRoomNoticeHandler.invoke(newRoom, user.getChannel());
			}
		}
	}

	/**对目标房间群发新房间通知
	 * @param room
	 * @param roomId
	 * @throws Exception
	 */
	public static void removeRoomNoticeInvoke(Room room, int roomId) throws Exception {
		for (Iterator<Entry<Integer, User>> it = room.getUserList().entrySet()
						.iterator(); it.hasNext();) {
			RoomRemoveNoticeHandler.invoke(roomId, it.next().getValue().getChannel());
		}
	}

	/**
	 * 发送聊天信息到房间
	 * @param message
	 * @param sender
	 * @throws Exception
	 */
	public static void sendToRoom(Message message, User sender)
			throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = sender.getRoom().getUserList().entrySet()
				.iterator(); it.hasNext();) {
			user =it.next().getValue();
			// if (sender.getId()!=user.getId())
				NewMessageNoticeHandler.invoke(message, user.getChannel());
		}
	}
	/**
	 * 发送聊天信息到房间 
	 * @param message
	 * @param room
	 * @throws Exception
	 */
	public static void sendToRoom(Message message, Room room)
			throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = room.getUserList().entrySet()
				.iterator(); it.hasNext();) {
			user =it.next().getValue();
			NewMessageNoticeHandler.invoke(message, user.getChannel());
		}
	}
	/**
	 * 发送消息到Zone
	 * @param mesage
	 * @param sender
	 * @throws Exception
	 */
	public static void sendToZone(Message mesage, User sender) throws Exception {
		Zone zone = sender.getRoom().getZone();
		User user;
		for (Iterator<Entry<Integer, Room>> it = zone.getRoomList().entrySet().iterator(); it
				.hasNext();) {
			for (Iterator<Entry<Integer, User>> er = it.next().getValue().getUserList().entrySet().iterator(); er
					.hasNext();) {
				user=er.next().getValue();
				// if (sender.getId()!=user.getId())
					NewMessageNoticeHandler.invoke(mesage, user.getChannel());
			}
		}
	}

	/**
	 * @return
	 */
	public static String RoomList() {
		String string="";
		for (Iterator<Entry<Integer, Room>> it = rooms.entrySet().iterator(); it.hasNext();) {
			string+=it.next().getValue().toString()+"\n";
		}
		return string;
	}
	public static int getRoomCount() {
		return rooms.size();
	}
	public static String ZoneList() {
		String string="";
		for (Iterator<Entry<Integer, Zone>> it = zones.entrySet().iterator(); it.hasNext();) {
			string+=it.next().getValue().toString()+"\n";
		}
		return string;
	}
	

}
