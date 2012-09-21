package net.combatserver.api;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import net.combatserver.protobuf.ChangeRoom.ChangeRoomResponseData;
import net.combatserver.protobuf.CreateRoom.CreateRoomResponseData;
import net.combatserver.protobuf.CreateRoom.NewRoomNoticeData;
import net.combatserver.protobuf.DataStructures.Room;
import net.combatserver.protobuf.DataStructures.User;
import net.combatserver.protobuf.GetRegion.GetRegionResponseData;
import net.combatserver.protobuf.JoinRoom.JoinRoomResponseData;
import net.combatserver.protobuf.JoinServer.JoinServerResponseData;
import net.combatserver.protobuf.NetSpeed.NetSpeedResponseData;
import net.combatserver.protobuf.NewMessage.NewMessageNoticeData;
import net.combatserver.protobuf.NewUser.NewUserNoticeData;
import net.combatserver.protobuf.Protocol.MessageHandler;
import net.combatserver.protobuf.RoomList.RoomListNoticeData;
import net.combatserver.protobuf.RoomRemove.RoomRemoveNoticeData;
import net.combatserver.protobuf.ServerError.ReturnStatus;
import net.combatserver.protobuf.UserLeave.UserLeaveNoticeData;
import net.combatserver.protobuf.UserList.UserListNoticeData;


public class DispatcherHandler{
    private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Connection connection;


	public DispatcherHandler(Connection connection, DataInputStream inputStream, DataOutputStream outputStream) {
    	this.inputStream=inputStream;
    	this.outputStream=outputStream;
    	this.connection=connection;
    }

	public void handlerEvent(String name,Object...objects) {
		Object target=Connection.getObject(name);
		if (target==null)
			return;
		Class<?> clazz = target.getClass();
		try {
			Method[] ms = clazz.getDeclaredMethods();
			for(Method m : ms){ 
				if(m.getName().equals(name)){
					m.invoke(target, objects);
					return;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
	}
   
    public void messageReceived (int MethodID) throws IOException{
		switch(MethodID){
		case MessageHandler.ServerTimeResponse_VALUE:			
			System.out.println("ServerTimeResponse");
			NetSpeedRequest();
			break;
		case MessageHandler.NetSpeedResponse_VALUE:
			NetSpeedResponse();
			break;
		case MessageHandler.JoinServerResponse_VALUE:
			JoinServerResponse();
			break;			
		case MessageHandler.JoinRoomResponse_VALUE:
	    	JoinRoomResponse();
	    	break;
		case MessageHandler.UserListNotice_VALUE:
	    	UserListNotice();
	    	break;
		case MessageHandler.GetRegionResponse_VALUE:
			GetRegionInfoResponse();
			break;
		case MessageHandler.RoomListNotice_VALUE:
	    	RoomListNotice();
	    	break;
		case MessageHandler.NewUserNotice_VALUE:
	    	NewUserNotice();
	    	break;
		case MessageHandler.UserLeaveNotice_VALUE:
	    	UserLeaveNotice();
	    	break;				
		case MessageHandler.ChangeRoomResponse_VALUE:
			ChangeRoomResponse();
			break;	
		case MessageHandler.NewMessageNotice_VALUE:
	    	NewMessageNotice();
	    	break;	
		case MessageHandler.NewRoomNotice_VALUE:			
			NewRoomNotice();
			break;	
		case MessageHandler.CreateRoomResponse_VALUE:
			CreateRoomResponse();
			break;
		case MessageHandler.RoomRemoveNotice_VALUE:
			RoomRemoveNotice();
			break;			
		case MessageHandler.ServerErrorResponse_VALUE:	
			ServerErrorResponse();
			break;
		default:
			parseData();
		}
    }

	private void ServerErrorResponse() throws IOException {
		System.out.println("ServerErrorResponse");
		inputStream.readShort();
		int error=inputStream.readInt();
		System.out.println(error);
		System.out.println(ReturnStatus.valueOf(error).name());
	}
	private void RoomRemoveNotice() throws IOException {
		System.out.println("RoomRemoveNotice");
		RoomRemoveNoticeData inPutMessage=RoomRemoveNoticeData.parseFrom(parseData());
		handlerEvent(EventHandler.onRoomRemove,inPutMessage.getRoomid());
	}	
	private void CreateRoomResponse() throws IOException {
		System.out.println("CreateRoomResponse");
		CreateRoomResponseData inPutMessage=CreateRoomResponseData.parseFrom(parseData());
		handlerEvent(EventHandler.onCreateRoom,inPutMessage.getRoom());
	}
	private void NewRoomNotice() throws IOException {
		System.out.println("NewRoomNotice");
		NewRoomNoticeData inPutMessage=NewRoomNoticeData.parseFrom(parseData());
		handlerEvent(EventHandler.onNewRoom,inPutMessage.getRoom());
	}

	private void NewMessageNotice() throws IOException {
    	System.out.println("NewMessageNotice");
    	NewMessageNoticeData inPutMessage=NewMessageNoticeData.parseFrom(parseData());
    	handlerEvent(EventHandler.onNewMessage,inPutMessage.getRouter(),inPutMessage.getMessage(),inPutMessage.getUsername(),inPutMessage.getUserid());
	}
	
	private void ChangeRoomResponse() throws IOException {
		System.out.println("ChangeRoomResponse");
		ChangeRoomResponseData inPutMessage=ChangeRoomResponseData.parseFrom(parseData());
		Room room=inPutMessage.getRoom();
		connection.activeRoomId=room.getId();
		handlerEvent(EventHandler.onChangeRoom,room);
	}
	
	private void UserLeaveNotice() throws IOException {
    	System.out.println("UserLeaveNotice");
    	UserLeaveNoticeData inPutMessage=UserLeaveNoticeData.parseFrom(parseData());
		handlerEvent(EventHandler.onUserLeave,inPutMessage.getUserid());
	}

	private void NewUserNotice() throws IOException {
    	System.out.println("NewUserNotice");
		NewUserNoticeData inPutMessage=NewUserNoticeData.parseFrom(parseData());
		handlerEvent(EventHandler.onNewUser,inPutMessage.getUser());
	}


	private void RoomListNotice() throws IOException {
    	System.out.println("RoomListNotice");
		RoomListNoticeData inPutMessage=RoomListNoticeData.parseFrom(parseData());
	   	for (Iterator<Room> it = inPutMessage.getRoomList().iterator();it.hasNext();){
	   		Room r=it.next();
	   		Connection.roomList.put(r.getId(), r);
	   	}		
		handlerEvent(EventHandler.onRoomList,inPutMessage.getRoomList());
	}

	private void GetRegionInfoResponse() throws IOException{
		System.out.println("GetRegionInfoRespones");
		GetRegionResponseData inPutMessage=GetRegionResponseData.parseFrom(parseData());
		connection.region=inPutMessage.getRegion();
		handlerEvent(EventHandler.onRegionInfo,connection.region);
	}
	private void UserListNotice() throws IOException {
    	System.out.println("UserListNotice");
    	Connection.userList.clear();
		UserListNoticeData inPutMessage=UserListNoticeData.parseFrom(parseData());
	   	for (Iterator<User> it = inPutMessage.getUserList().iterator();it.hasNext();){
	   		User r=it.next();
	   		Connection.userList.put(r.getId(), r);
	   	}		
		handlerEvent(EventHandler.onUserList,inPutMessage.getUserList());
	}

	private void JoinRoomResponse() throws IOException {
    	System.out.println("JoinRoomResponse");
		JoinRoomResponseData inPutMessage=JoinRoomResponseData.parseFrom(parseData());
		Room room= inPutMessage.getRoom();
		connection.activeRoomId=room.getId();
		handlerEvent(EventHandler.onJoinRoom,room);
	}

    private void JoinServerResponse() throws IOException {
		System.out.println("JoinServerResponse");    	
		JoinServerResponseData inPutMessage=JoinServerResponseData.parseFrom(parseData());
		connection.me=inPutMessage.getUser();
		int pingIntervall = inPutMessage.getPingInterval();
		if(pingIntervall > 0){
			//connection.runPing(pingIntervall);
        }
		handlerEvent(EventHandler.onJoinServer,connection.me);
	}

    
	private void NetSpeedResponse() throws IOException {
		System.out.println("NetSpeedResponse");		
		NetSpeedResponseData data=NetSpeedResponseData.parseFrom(parseData());
		handlerEvent(EventHandler.onConnected,data.getDelay());
	}    
	private void NetSpeedRequest() throws IOException {
		System.out.println("NetSpeedRequest");		
    	inputStream.readChar();
		long time=inputStream.readLong();
		connection.netSpeedRequest(time);
	}
	
	private byte[] parseData() throws IOException {
		int length=inputStream.readChar();
		byte[] bytes = new byte[length];
		inputStream.read(bytes);
		return bytes;
	}
	
}
