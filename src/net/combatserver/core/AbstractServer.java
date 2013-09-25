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
 * ������������ ��������������
 * @author kohachiro
 * 
 */
public abstract  class AbstractServer {
    /**
     * ҵ���̳߳�
     */
    public static ExecutorService threadPool;
    /**
     * ��ʱ���̳߳�
     */
    public static ScheduledExecutorService scheduledPool;
    /**
     * �����¼��ӿ� ���ڻص�
     */
    public static ServerHandler callBackHandler;   
	/**
	 * ����̨
	 */
	public static BufferedReader consoleInput;
	/**
	 * �ɼ�ϵͳ��Ϣ����
	 */
	public static SystemInfo systemInfo;
	/**
	 * �˿� config xml�����ļ�����������ֵ
	 */
	public static int port = 7890;
	
	public static int PolicyFilePort = 843;
	
	/**
	 * log�ļ���С����  δ���� config xml�����ļ�����������ֵ
	 */
	public static int logFileLimit;
	/**
	 * �ͻ���ping���� config xml�����ļ�����������ֵ
	 */
	public static int pingInterval = 10000;
	/**
	 * Ĭ�Ϸ����� config xml�����ļ�����������ֵ
	 */
	public static int defaultRoom = 2;
	/**
	 * ���������� config xml�����ļ�����������ֵ
	 */
	public static String name;
	/**
	 * log�ļ�
	 */
	public static String logFile;
	/**
	 * ��Ϣ����������·��
	 */
	public static String MessageClassPath;
	/**
	 * ��Ϣ��ʽ������·��
	 */
	public static String ProtobufClassPath;
	/**
	 * net�������� �û���ʾ
	 */
	public static String framework;
	/**
	 * ����ϵͳ��Ϣʱ�����õ�ϵͳ�˻�
	 */
	public static User systemUser;	
	/**
	 * �Զ����� Extensions<br/>
	 * ������ false ����Ҫ�ڿ���̨��load���� �ֶ�����<br/>
	 * @see net.combatserver.core.ExtensionsManager
	 */
	public static boolean autoLoadExtensions=true;
	
	
	/* (non-Javadoc)
	 * @see net.com.sunkey.core.ExtensionsManager
	 */	
	private ExtensionsManager extensionsManager;


	/**
	 * ���캯��<br/>
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
	 * ��������<br/>
	 * ��Ѱ��ÿ�������Ͽ��԰󶨵ĵ�ַ �ֱ�����<br/>
	 * mina �� grizzly �����ڰ���ʧ����ȷ�׳�IOException<br/>
	 */
	public void networkBind() {
		if (!(new NetworkBind()).bind(this,port))
			System.exit(1);
		if (!(new NetworkBind()).bind(this,PolicyFilePort))
			System.exit(1);
	}
	/**
	 * ����̨����
	 */
	public void console() {
		//TODO room list and region list
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
					System.out.println("\tregion: region screen.");			
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
				} else if ("region".equals(command)) {
					System.out.println("Region:");
					System.out.println(RoomManager.RegionList());
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
	 * ������չģ��<br/>
	 * autoLoadExtensions���� �߳� �Ƿ� start<br/>
	 */
	private void loadExtensions() {
		extensionsManager=new ExtensionsManager();
		if (autoLoadExtensions)
			extensionsManager.start();
		else
			extensionsManager.process();
	}
	
	/**
	 * ���� ����̨����
	 */
	private void printTitle() {
		System.out.println("*************************************************");
		System.out.println("*       Tiburon Online Game Server V1.1.0       *");
		System.out.println("*                                               *");
		System.out.println("*             (c) 2011 Kohachiro co.ltd.        *");
		System.out.println("*                                               *");	
		System.out.println("*************************************************");
		System.out.println();
		System.out.println("Framework:\t"+framework);		
	}
	/**
	 * ����log�ļ�
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
	 * ���鲢����  config.xml�ļ�<br/>
	 * @return  config.xml�ļ�
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
	 * ����crossdomain��xml Ŀǰδ����<br/>
	 * ����mina grizzly nettyʵ��http get file ����������ͬ<br/>
	 * crossdomain��xml �Ĵ�������  
	 * net.com.sunkey.messagehandler.HttpGetRequestHandler ������<br/>
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
	 * ��ȡϵͳ��Ϣ<br/>
	 * ͨ�� refresh()����ˢ��<br/>
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
	 * ����ʵ�ַ��� <br/>
	 * ���� ��ַ���� ����<br/>
	 * @param inetSocketAddress �󶨵�ַ
	 * @throws Exception �׳� IOException
	 */
	public abstract void bind(InetSocketAddress inetSocketAddress) throws Exception ;		
}
