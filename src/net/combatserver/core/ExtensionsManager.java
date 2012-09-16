package net.combatserver.core;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.combatserver.protobuf.Protocol;
import net.combatserver.serverlogic.Entity;
import net.combatserver.serverlogic.Module;
import net.combatserver.serverlogic.Plugin;
import net.combatserver.serverlogic.Scheduled;
import net.combatserver.serverlogic.Server;

import com.sun.tools.javac.Main;

/**
 * ��չ������ <br>���� module,plugin,scheduled ������չ<br/>
 * module: ʵ�ֶ� ϵͳ net.com.sunkey.messagehandler�ڲ� �����ĸ���<br/>
 * plugin: ͨ��  PluginRequest PluginResponse  ʵ�� RPC <br/>
 * scheduled: ʵ��һ����ʱ�� ��ͨ��config.xml��template��scheduled����  �� room �� ��<br/>
 * ��room����ʱ ʵ������ͨ��plugin �������������� room.runScheduled()�������ʱ��<br/>
 * Ĭ�϶�ʱ�� �� 2Сʱ�Զ��ر� ����ͨ�� scheduledTimeout�����øı����ֵ ������ -1 �Զ��ر� ʧЧ<br/>
 * 
 * @author kohachiro
 * 
 */
public class ExtensionsManager implements Runnable {
	/**
	 * ���� ��ʱ�� �Զ��ر�ʱ��
	 */
	public static int scheduledTimeout = 7200;	
	/**
	 * ��չ�ļ�·��
	 */
	private static String filePath = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "extension";
	/**
	 * ��չģ��class path
	 */
	private static String fileClass = "serverlogic.";
	/**
	 * ��¼�ļ�����ʱ��
	 */
	private static Map<String, Long> files = new ConcurrentHashMap<String, Long>();
	/**
	 * �洢Module����
	 */
	private static Map<Integer, Module> modules = new ConcurrentHashMap<Integer, Module>();
	/**
	 * �洢Plugin����
	 */
	private static Map<String, Plugin> plugins = new ConcurrentHashMap<String, Plugin>();
	/**
	 * �洢Scheduled����
	 */
	private static Map<String, Scheduled> scheduleds = new ConcurrentHashMap<String, Scheduled>();
	/**
	 * �洢�־ö���
	 */
	private static Map<String, Object> entities = new ConcurrentHashMap<String, Object>();
	/**
	 * ���� �Զ����ص��߳�
	 */
	private Thread thread;
	/**
	 * �Զ����ص� ˢ��ʱ��
	 */
	private int interval = 10000;

	/**
	 * 
	 */
	public ExtensionsManager() {
		thread = new Thread(this);
	}

	/**
	 * ���� �Զ����ص��߳�
	 */
	public void start() {
		thread.start();
	}

	/**
	 * ������� 
	 */
	public void process() {
		compileJava(filePath);
		loadClass(filePath);
	}

	/**
	 * @param interval
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * ���һ�� �ļ�����ʱ��
	 * @param name
	 * @param newtime
	 */
	private void addList(String name, long newtime) {
		if (files.get(name) != null)
			files.remove(name);
		files.put(name, newtime);
	}

	/**
	 * ����ű�
	 * @param path
	 * @param name
	 */
	private void compile(String path, String name) {
		Main.compile(new String[] { path + System.getProperty("file.separator")
				+ name });
	}

	/**
	 * �ж��Ƿ����ļ�
	 * @param name
	 * @param newtime
	 * @return
	 */
	private boolean isNewFile(String name, long newtime) {
		if (files.get(name) == null || files.get(name) < newtime)
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			while (true) {
				process();
				Thread.sleep(interval);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����java�ļ��������ļ� ����ʱ��
	 * @param path
	 */
	private void compileJava(String path) {
		File dir = new File(path + System.getProperty("file.separator")
				+ "serverlogic");
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (files[i].getName().lastIndexOf(".java") > 0) {
				long fileTime = files[i].lastModified();
				String className = name.substring(0, name.lastIndexOf("."));
				if (isNewFile(name, fileTime)) {
					compile(dir.getPath(), name);
					addList(name, fileTime);
					System.out.println("Compile " + className + ".java");
				}
			}
		}
	}

	/**
	 * ����һ���� 
	 * @param path
	 */
	private void loadClass(String path) {
		File dir = new File(path + System.getProperty("file.separator")
				+ "serverlogic");
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (files[i].getName().lastIndexOf(".class") > 0) {
				long fileTime = files[i].lastModified();
				String className = name.substring(0, name.lastIndexOf("."));
				if (isNewFile(name, fileTime)) {
					addList(name, fileTime);
					urlLoadClass(className);
					// System.out.println("load "+className+".class");
				}
			}
		}
	}

	/**
	 * ����һ����
	 * @param name
	 */
	private void urlLoadClass(String name) {
		try {
			URL url = new File(filePath).toURI().toURL();
			URLClassLoader urlCL = new URLClassLoader(new URL[] { url });
			String className = fileClass + name;
			Class<?> clazz = urlCL.loadClass(className);
			Object instance = clazz.newInstance();
			String type = (String) clazz.getMethod("type").invoke(instance);
			//create a instance and add Module list when class is a Module  
			if (type.equals("Module")) {
				int id = (int) clazz.getMethod("register").invoke(instance);
				if (modules.get(id) != null)
					modules.remove(id);
				modules.put(id, (Module)instance);
				System.out.println("Register " + className + " as "
						+ Protocol.MessageHandler.valueOf(id).name());
			//create a instance and add Plugin list when class is a Plugin 				
			} else if (type.equals("Plugin")) {
				String pluginID = (String) clazz.getMethod("register").invoke(
						instance);
				if (plugins.get(pluginID) != null)
					plugins.remove(pluginID);
				plugins.put(pluginID,(Plugin)instance);
				System.out.println("Register " + className
						+ " pluginID: " + pluginID);
				//create a instance and add Plugin list when class is a Plugin 				
			} else if (type.equals("Entity")) {
				String entityName = (String) clazz.getMethod("register").invoke(
						instance);
				Object object = clazz.getMethod("invoke").invoke(instance);
				if (entities.get(entityName) != null)
					entities.remove(entityName);
				entities.put(entityName, object);
				System.out.println("Register " + className
						+ " as an Entity\n\tKey : " + entityName + "\tValue : " + object.toString() );
			//create a cloneable instance and add Scheduled list when class is a Scheduled 	
			} else if (type.equals("Scheduled")) {
				if (scheduleds.get(className) != null)
					scheduleds.remove(className);
				scheduleds.put(className, (Scheduled)instance);			
				System.out.println("Register " + className
						+ " as  a scheduled");
			}else{
				instance=null;
			}
			urlCL = null;
		} catch (Exception e) {
			//other class will thow a NoSuchMethodException  
		}
	}

	/**
	 * @param methodID
	 * @return
	 */
	public static boolean isModule(int methodID) {
		if (modules.get(methodID) != null)
			return true;
		return false;
	}

	/**
	 * @param methodID
	 * @return
	 */
	public static Module getModule(int methodID) {
		return  modules.get(methodID);
	}

	/**
	 * @param pluginID
	 * @return
	 */
	public static Plugin getPlugin(String pluginID) {
		return  plugins.get(pluginID);
	}

	/**
	 * @param clazz
	 * @return
	 */
	public static Scheduled getScheduled(String classname) {
		return  scheduleds.get(classname);
	}
	/**
	 * @param clazz
	 * @return
	 */
	public static Object getEntity(String name) {
		return entities.get(name);
	}	
}
