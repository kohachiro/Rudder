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
 * 扩展管理类 <br>包括 module,plugin,scheduled 三类扩展<br/>
 * module: 实现对 系统 net.com.sunkey.messagehandler内部 方法的覆盖<br/>
 * plugin: 通过  PluginRequest PluginResponse  实现 RPC <br/>
 * scheduled: 实现一个定时器 。通过config.xml的template的scheduled设置  和 room 绑定 。<br/>
 * 在room创建时 实例化，通过plugin 或其他方法调用 room.runScheduled()方法激活定时器<br/>
 * 默认定时器 在 2小时自动关闭 可以通过 scheduledTimeout的设置改变这个值 若设置 -1 自动关闭 失效<br/>
 * 
 * @author kohachiro
 * 
 */
public class ExtensionsManager implements Runnable {
	/**
	 * 控制 定时器 自动关闭时间
	 */
	public static int scheduledTimeout = 7200;	
	/**
	 * 扩展文件路径
	 */
	private static String filePath = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "extension";
	/**
	 * 扩展模块class path
	 */
	private static String fileClass = "serverlogic.";
	/**
	 * 记录文件更新时间
	 */
	private static Map<String, Long> files = new ConcurrentHashMap<String, Long>();
	/**
	 * 存储Module对象
	 */
	private static Map<Integer, Module> modules = new ConcurrentHashMap<Integer, Module>();
	/**
	 * 存储Plugin对象
	 */
	private static Map<String, Plugin> plugins = new ConcurrentHashMap<String, Plugin>();
	/**
	 * 存储Scheduled对象
	 */
	private static Map<String, Scheduled> scheduleds = new ConcurrentHashMap<String, Scheduled>();
	/**
	 * 存储持久对象
	 */
	private static Map<String, Object> entities = new ConcurrentHashMap<String, Object>();
	/**
	 * 用于 自动加载的线程
	 */
	private Thread thread;
	/**
	 * 自动加载的 刷新时间
	 */
	private int interval = 10000;

	/**
	 * 
	 */
	public ExtensionsManager() {
		thread = new Thread(this);
	}

	/**
	 * 启动 自动加载的线程
	 */
	public void start() {
		thread.start();
	}

	/**
	 * 处理调用 
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
	 * 添加一个 文件更新时间
	 * @param name
	 * @param newtime
	 */
	private void addList(String name, long newtime) {
		if (files.get(name) != null)
			files.remove(name);
		files.put(name, newtime);
	}

	/**
	 * 编译脚本
	 * @param path
	 * @param name
	 */
	private void compile(String path, String name) {
		Main.compile(new String[] { path + System.getProperty("file.separator")
				+ name });
	}

	/**
	 * 判断是否新文件
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
	 * 编译java文件并更新文件 更新时间
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
	 * 加载一个类 
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
	 * 加载一个类
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
