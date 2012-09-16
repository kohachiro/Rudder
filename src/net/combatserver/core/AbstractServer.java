package net.combatserver.core;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import net.combatserver.messagehandler.DisconnectedRequest;
import net.combatserver.messagehandler.InvidRequestHandler;
import net.combatserver.messagehandler.ServerTimeResponseHandler;
import net.combatserver.protobuf.Protocol;
import net.combatserver.serverlogic.User;
import net.combatserver.util.NetworkBind;
import net.combatserver.util.ParseXML;
import net.combatserver.util.SystemInfo;


/**
 * 抽象服务器类 用于启动服务器
 * @author kohachiro
 * 
 */
public abstract  class AbstractServer {
    /**
     * 业务线程池
     */
    public static ExecutorService threadPool;
    /**
     * 定时器线程池
     */
    public static ScheduledExecutorService scheduledPool;
    /**
     * 网络事件接口 用于回调
     */
    public static InterfaceServerHandler callBackHandler;   
	/**
	 * 控制台
	 */
	public static BufferedReader consoleInput;
	/**
	 * 采集系统信息对象
	 */
	public static SystemInfo systemInfo;
	/**
	 * 端口 config xml配置文件会重置这个值
	 */
	public static int port = 7890;
	
	public static int PolicyFilePort = 843;
	
	/**
	 * log文件大小限制  未启用 config xml配置文件会重置这个值
	 */
	public static int logFileLimit;
	/**
	 * 客户端ping间隔 config xml配置文件会重置这个值
	 */
	public static int pingInterval = 10000;
	/**
	 * 默认房间号 config xml配置文件会重置这个值
	 */
	public static int defaultRoom = 2;
	/**
	 * 服务器名称 config xml配置文件会重置这个值
	 */
	public static String name;
	/**
	 * log文件
	 */
	public static String logFile;
	/**
	 * 消息处理类所在路径
	 */
	public static String MessageClassPath;
	/**
	 * 消息格式类所在路径
	 */
	public static String ProtobufClassPath;
	/**
	 * net框架名称 用户显示
	 */
	public static String framework;
	/**
	 * 发布系统消息时的所用的系统账户
	 */
	public static User systemUser;	
	/**
	 * 自动加载 Extensions<br/>
	 * 定义成 false 则需要在控制台打load命令 手动加载<br/>
	 * @see net.combatserver.core.ExtensionsManager
	 */
	public static boolean autoLoadExtensions=true;
	
	
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.ExtensionsManager
	 */	
	private ExtensionsManager extensionsManager;


	/**
	 * 构造函数<br/>
	 * threadPool 
	 * 
	 */
	public AbstractServer()  {
		systemUser=new User(0, "System", new Object());
//		threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors()*4 ,
//				300, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		threadPool = Executors.newCachedThreadPool();		
		scheduledPool = Executors.newScheduledThreadPool(2);	
		
		MessageClassPath = InvidRequestHandler.class.getName().substring(0,InvidRequestHandler.class.getName().lastIndexOf(".") + 1);
		ProtobufClassPath = Protocol.class.getName().substring(0,Protocol.class.getName().lastIndexOf(".") + 1);
		printTitle();
		FileInputStream file=configXMLfile();
		ParseXML parseXML=new ParseXML();
		if (!parseXML.parse(file)){
			System.err.println("config.xml parse error.\n"
					+ "Server instance is shutting down...");
			System.exit(1);
		}
		crossDomainfile();		
		logfile();
//		dBManager=new DBManager();
//		dBManager.init();
	}

	/**
	 * 网络绑定<br/>
	 * 将寻找每个网卡上可以绑定的地址 分别绑定<br/>
	 * mina 和 grizzly 不能在绑定失败正确抛出IOException<br/>
	 */
	public void networkBind() {
		if (!(new NetworkBind()).bind(this,port))
			System.exit(1);
		if (!(new NetworkBind()).bind(this,PolicyFilePort))
			System.exit(1);
	}
	/**
	 * 控制台界面
	 */
	public void console() {
		//TODO room list and zone list
		loadExtensions();
		systemInfo = new SystemInfo();		
		consoleInput = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String command;
			try {
				command = consoleInput.readLine();
				int number=0;
				try {
					number=Integer.parseInt(command);
				} catch (NumberFormatException e) {}
				if (number!=0) {
					System.out.println("User:");
					User user=User.get(Integer.parseInt(command));
					if (user!=null)
						System.out.println(user.toString());				
				}else if ("help".equals(command)) {
					System.out.println("Command:");
					System.out.println("\tload: load extensions. ");
					System.out.println("\tserver: server screen.");
					System.out.println("\tuser: user screen.");
					System.out.println("\troom: room screen.");
					System.out.println("\tzone: zone screen.");			
					System.out.println("\t[userid]: user info");
					System.out.println("\thelp: this screen. ");
				} else if ("load".equals(command)) {
					System.out.println("Load extensions:");
					extensionsManager.process();				
				} else if ("user".equals(command)) {
					System.out.println("User:");
					System.out.println("count:\t"+UserManager.getUserCount());
					System.out.println(UserManager.UserList());
				} else if ("room".equals(command)) {				
					System.out.println("Room:");
					System.out.println("count:"+RoomManager.getRoomCount());					
					System.out.println(RoomManager.RoomList());
				} else if ("zone".equals(command)) {
					System.out.println("Zone:");
					System.out.println(RoomManager.ZoneList());
				} else {
					systemInfo(systemInfo);
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}		
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.messagehandler.ServerErrorResponseHandler#invoke(byte, java.lang.Object)
	 */
	public static void errorHandler(Object channel) {
//		ServerErrorResponseHandler.invoke(
//				Protocol.ReturnStatus.STATUS_CONNECT_ERROR_VALUE, object);
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.messagehandler.ServerTimeResponseHandler#invoke(java.lang.Object, java.lang.Object)
	 */
	public static void connected(Object channel) {
		try {
			if(callBackHandler.getServerPort(channel)!=PolicyFilePort)
				ServerTimeResponseHandler.invoke(System.currentTimeMillis(),channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/* (non-Javadoc)
	 * @see net.com.sunkey.messagehandler.DisconnectedRequest#invoke(java.lang.Object, java.lang.Object)
	 */
	public static void disConnected(Object channel) {
		try {
			DisconnectedRequest.invoke(null,channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.HandlerRunnable#HandlerRunnable(java.nio.ByteBuffer,java.lang.Object)
	 */
	public static void execute(ByteBuffer buffer,Object channel) {
		threadPool.execute(new HandlerRunnable(buffer, channel));
	}
	/**
	 * 加载扩展模块<br/>
	 * autoLoadExtensions控制 线程 是否 start<br/>
	 */
	private void loadExtensions() {
		extensionsManager=new ExtensionsManager();
		if (autoLoadExtensions)
			extensionsManager.start();
		else
			extensionsManager.process();
	}
	
	/**
	 * 输出 控制台标题
	 */
	private void printTitle() {
		System.out.println("*************************************************");
		System.out.println("*       Rudder Online Game Server V1.1.0        *");
		System.out.println("*                                               *");
		System.out.println("*             (c) 2011 Rudder co.ltd.           *");
		System.out.println("*                                               *");	
		System.out.println("*************************************************");
		System.out.println();
		System.out.println("Framework:\t"+framework);		
	}
	/**
	 * 检查log文件
	 */
	private static void logfile() {
		String dir = "log";
		if (!(new File(dir)).exists()) {
			System.err.println("Directory '" + dir + "' does not exist.\n"
					+ "Server instance is shutting down...");
			System.exit(1);
		}
	}

	/**
	 * 检查并返回  config.xml文件<br/>
	 * @return  config.xml文件
	 * @see net.combatserver.util.ParseXML
	 */
	private static FileInputStream configXMLfile() {
		String filename = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "config"
				+ System.getProperty("file.separator") + "config.xml";
		if (!(new File(filename)).exists()) {
			System.err.println(filename + " does not exist.\n"
					+ "Server instance is shutting down...");
			System.exit(1);
		}
		try {
			return new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查crossdomain。xml 目前未启用<br/>
	 * 由于mina grizzly netty实现http get file 方法各不相同<br/>
	 * crossdomain。xml 的处理交由  
	 * net.com.sunkey.messagehandler.HttpGetRequestHandler 来完成<br/>
	 * @see net.combatserver.messagehandler.HttpGetRequestHandler
	 */
	private static void crossDomainfile() {
		String filename = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "config"
				+ System.getProperty("file.separator") + "crossdomain.xml";
		if (!(new File(filename)).exists()) {
			System.err.println(filename + " does not exist.\n"
					+ "Server instance is shutting down...");
			System.exit(1);
		}
	}
	/**
	 * 获取系统信息<br/>
	 * 通过 refresh()方法刷新<br/>
	 * @param systemInfo
	 */
	private void systemInfo(SystemInfo systemInfo) {
		systemInfo.refresh();
		System.out.println();
		System.out.println("port:\t\t" + port);
		System.out.println("Cores:\t\t" + systemInfo.cores);
		System.out.println("System:\t\t" + systemInfo.system);
		System.out.println("Threads:\t" + systemInfo.threads);
		System.out.println("CpuRate:\t" + systemInfo.cpuRate);
		System.out.println("MaxMemory:\t" + systemInfo.maxMemory);
		System.out.println("FreeMemory:\t" + systemInfo.freeMemory);
		System.out.println("TotalMemory:\t" + systemInfo.totalMemory);	
		System.out.println("SystemTime:\t" + systemInfo.systemTime);
		System.out.println("RunnerTime:\t" + systemInfo.runningTime);		
	}
	
	/**
	 * 绑定实现方法 <br/>
	 * 输入 地址返回 绑定<br/>
	 * @param inetSocketAddress 绑定地址
	 * @throws Exception 抛出 IOException
	 */
	public abstract void bind(InetSocketAddress inetSocketAddress) throws Exception ;		
}
