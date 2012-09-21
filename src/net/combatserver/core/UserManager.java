package net.combatserver.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.combatserver.messagehandler.NewMessageNoticeHandler;
import net.combatserver.messagehandler.PluginNoticeHandler;
import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.serverlogic.Guild;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.Team;
import net.combatserver.serverlogic.User;

/**
 * User������ <br/>
 * 
 * @author kohachiro
 *
 */
public class UserManager{
	/**
	 * user map ͨ�� channel ����
	 */
	final static ConcurrentHashMap<Object, User> usersKeyChannelId = new ConcurrentHashMap<Object, User>();
	/**
	 * user map ͨ�� userId ����
	 */
	final static ConcurrentHashMap<Integer, User> usersKeyUserId = new ConcurrentHashMap<Integer, User>();// userid
	/**
	 * user map ͨ�� userName ����
	 */
	final static ConcurrentHashMap<String, User> usersKeyUserName = new ConcurrentHashMap<String, User>();
	/**
	 * team map ͨ��userId ���� Team
	 */
	final static ConcurrentHashMap<Integer, Team> teams = new ConcurrentHashMap<Integer, Team>();
	/**
	 * guild map ͨ��userId ���� Guild
	 */
	final static ConcurrentHashMap<Integer, Guild> guilds = new ConcurrentHashMap<Integer, Guild>();

	/**
	 * 
	 */
	public UserManager() {

	}


	/**
	 * ���֤��
	 * @param certificate
	 * @return
	 */
//	public static boolean checkCertificate(String certificate) {
//		return DBManager.checkCertificate(certificate);
//	}

	/**
	 * ��¼
	 * @param name
	 * @param password
	 * @return
	 */
//	public static int login(String username, String password) {
//		return DBManager.login(username, password);// userid
//	}
	
	/**
	 * ��ȡ��ɫ
	 * @param accountID
	 * @param channel
	 * @return
	 */
//	public static User get(int accountID, Object channel) {
//		User user = DBManager.getUser(accountID, channel);// userid
//		initUser(user);
//		return user;
//	}

	/**
	 * ��ʼ�����û�
	 * @param user
	 */
	public static void initUser(User user) {
		usersKeyUserId.put(user.getId(), user);
		usersKeyChannelId.put(user.getChannel(), user);
		usersKeyUserName.put(user.getName(), user);
	}

	// private static void loginlog(Data data) {
	// DBManager.loginLog(data.getUsername(),data.getCharset(),data.getFlashVersion(),data.getOs(),data.getPixel(),data.getVersion(),data.getClientSpeed());
	// }

	/**
	 * @param name
	 * @return
	 */
	public static User getUser(String name) {
		return usersKeyUserName.get(name);
	}

	/**
	 * @param id
	 * @return
	 */
	public static User getUser(int id) {
		return usersKeyUserId.get(id);
	}

	/**
	 * @param channel
	 * @return
	 */
	public static User getUser(Object channel) {
		return usersKeyChannelId.get(channel);
	}
	/**
	 * @return
	 */
	public static int getUserCount() {
		return usersKeyUserId.size();
	}
	/**
	 * ���ߴ���
	 * @param channel
	 * @throws Exception
	 */
	public static void disconnected(Object channel) throws Exception {
		//ChannelManager.remove(channel);
		User user = getUser(channel);
		if (user != null) {
			usersKeyChannelId.remove(channel);
			usersKeyUserName.remove(user.getName());
			usersKeyUserId.remove(user.getId());
			if(user.getRoom()!=null)
				user.getRoom().removeUser(user.getId());
		}
	}
	/**
	 * ȫ������Ⱥ����Ϣ
	 * @param mesage
	 * @param sender
	 * @throws Exception
	 */
	public static void sendToServer(Message mesage, User sender)
			throws Exception {
		User user;
		for (Iterator<Entry<Integer, User>> it = usersKeyUserId.entrySet().iterator(); it
				.hasNext();) {
			user = it.next().getValue();
			// if (sender.getId()!=user.getId())
				NewMessageNoticeHandler.invoke(mesage, user.getChannel());
		}
	}
	
	/**
	 * ����plugin����
	 * @param message
	 * @param channel
	 * @throws Exception
	 */
	public static void pluginNoticeInvoke(PluginData message, Object channel) throws Exception {
		PluginNoticeHandler.invoke(message, channel);
	}


	public static String UserList() {
		String string="";
		int i=0;
		for (Iterator<Entry<Integer, User>> it = usersKeyUserId.entrySet().iterator(); it.hasNext();i++) {
			string+=it.next().getValue().info()+"\n";
			if (i>100)
				break;
		}
		return string;
	}
}
