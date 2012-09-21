package net.combatserver.api;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.combatserver.protobuf.ChangeRoom.ChangeRoomRequestData;
import net.combatserver.protobuf.CreateRoom.CreateRoomRequestData;
import net.combatserver.protobuf.DataStructures.Room;
import net.combatserver.protobuf.DataStructures.User;
import net.combatserver.protobuf.DataStructures.Region;
import net.combatserver.protobuf.GetRegion.GetRegionRequestData;
import net.combatserver.protobuf.JoinServer.JoinServerRequestData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.RoomMessage.RoomMessageRequestData;





/**
 * 
 */

/**
 * @author kohachiro
 *
 */
public class Connection implements Runnable{

	public static final Map<String, Object> listers = new HashMap<String, Object>();
	public static final Map<Integer, Room> roomList = new HashMap<Integer, Room>();
	public static final Map<Integer, User> userList = new HashMap<Integer, User>();	
	public static final int DefaultRegion=1;	
	public static Connection instance;
	public User me;
	public Region region;
	public int activeRoomId;
	
	private Socket socket;
	private String session;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	private DispatcherHandler dispatcherHandler;
	private TimerTask timerTask;
	private Timer timer;

	/**
	 * @param ip 
	 * @param port 
	 * 
	 */
	public Connection(String ip, int port) {
		instance=this;
		new Thread(this).start();
		try {
			socket = new Socket(ip, port);
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
		} catch ( IOException e) {
			e.printStackTrace();
		}
		dispatcherHandler =new DispatcherHandler(this,inputStream,outputStream);
	}	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		int methodID=0;
		while(true){			
            try{
            	methodID = inputStream.readShort();
            	if (methodID!=0){
            		dispatcherHandler.messageReceived(methodID);
            	}
            	Thread.sleep(100L);
        	}catch(Exception e) { 
        		//e.printStackTrace();	
        	}   
		}
	}
	public void addEventListener(String name, Object target) {
		listers.put(name, target);
	}
	public static Object getObject(String name) {
		return listers.get(name);
	}
	
	public void loginServer(String username, String password) {
		JoinServerRequestData.Builder builder=JoinServerRequestData.newBuilder();
		builder.setUsername(username);
		builder.setPassword(password);	
		JoinServerRequestData outPutMessage=builder.build();
		sendResponse(MessageHandler.JoinServerRequest_VALUE,outPutMessage.toByteArray());
		System.out.println("JoinServerRequest");
	}
	public void netSpeedRequest(long time) {
//		NetSpeedRequestData.Builder builder=NetSpeedRequestData.newBuilder();
//		builder.setTime(time);
//		NetSpeedRequestData outPutMessage=builder.build();
//		sendResponse(MessageHandler.NetSpeedRequest_VALUE,outPutMessage.toByteArray());
		sendResponse(MessageHandler.NetSpeedRequest_VALUE,time);
		System.out.println("netSpeedRequest");
	}		
	public void joinDefaultRoom() {
		sendResponse(MessageHandler.JoinDefaultRoomRequest_VALUE);
		System.out.println("joinDefaultRoom");
	}	
	public void getRegionRequest( int regionid ) {
		GetRegionRequestData.Builder builder=GetRegionRequestData.newBuilder();
		builder.setRegionid(regionid);
		GetRegionRequestData outPutMessage=builder.build();
		sendResponse(MessageHandler.GetRegionRequest_VALUE,outPutMessage.toByteArray());
		System.out.println("GetRegionInfoRequest");
	}
	public void changeRoom(int roomid) {
		ChangeRoomRequestData.Builder builder=ChangeRoomRequestData.newBuilder();
		builder.setToRoomId(roomid);
		ChangeRoomRequestData outPutMessage=builder.build();
		sendResponse(MessageHandler.ChangeRoomRequest_VALUE,outPutMessage.toByteArray());
		System.out.println("ChangeRoomRequest");
	}
	public void sendMessage(int router,String text) {
		System.out.println("sendMessage");
		RoomMessageRequestData.Builder builder=RoomMessageRequestData.newBuilder();
		builder.setMessage(text);
		RoomMessageRequestData message=builder.build();
		if (router==-2)
			sendResponse(MessageHandler.RoomMessageRequest_VALUE,message.toByteArray());
		else if (router==-3)
			sendResponse(MessageHandler.RegionMessageRequest_VALUE,message.toByteArray());
		else if (router==-4)			
			sendResponse(MessageHandler.ServerMessageRequest_VALUE,message.toByteArray());				

	}	
	public void createRoom(int tid,String roomName, String password) {
		System.out.println("createRoom");
		CreateRoomRequestData.Builder builder=CreateRoomRequestData.newBuilder();
		builder.setName(roomName);
		builder.setPassword(password);
		builder.setTemplateid(tid);
		CreateRoomRequestData message=builder.build();
		sendResponse(MessageHandler.CreateRoomRequest_VALUE,message.toByteArray());
	}	
	private void sendResponse(int methodID, long data) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		try {
			dos.writeShort(methodID);
			dos.writeChar(8);
			dos.writeLong(data);
			outputStream.write(baos.toByteArray());		
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
//	private void sendResponse(int methodID, int data) {
//		ByteArrayOutputStream baos= new ByteArrayOutputStream();
//		DataOutputStream dos=new DataOutputStream(baos);
//		try {
//			dos.writeShort(methodID);
//			dos.writeChar(4);
//			dos.writeInt(data);
//			outputStream.write(baos.toByteArray());		
//			outputStream.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	private void sendResponse(int methodID, byte[] bytes) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		try {
			dos.writeShort(methodID);
			dos.writeChar(bytes.length);
			dos.write(bytes);
			outputStream.write(baos.toByteArray());			
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void sendResponse(int methodID) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);		
		try {
			dos.writeShort(methodID);
			dos.writeChar(0);
			outputStream.write(baos.toByteArray());	
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public void setSession(String session) {
		this.session=session;
	}
	public void runPing(int pingIntervall) {
		timerTask = new TimerTask() {
			public void run(){
				try{
					outputStream.writeShort(MessageHandler.PingIntervalRequest_VALUE);							
					outputStream.flush();
				}catch(Exception e){}
            }
		};
        if(timer != null)
			timer.cancel();
        timer = new Timer();
        timer.schedule(timerTask, 0, pingIntervall);
	}

}
