package net.combatserver{
	import com.google.protobuf.Int64;
	import com.google.protobuf.Message;
	import com.google.protobuf.WritingBuffer;
	
	import flash.errors.*;
	import flash.events.*;
	import flash.net.Socket;
	import flash.utils.ByteArray;
	
	import net.combatserver.protobuf.*;
	import net.combatserver.protobuf.MessageHandler;
		
	public class Connection extends Socket {
		public var me:User;
		public var room:Room;
		public var zone:Zone;
		public var rooms:HashMap=new HashMap();		
		public var users:HashMap=new HashMap();			
		private var handler:ConnectEvent;
		private var methodID:Number=-1;
		private var length:Number=-1;
		public function Connection(_handler:ConnectEvent,host:String = null, port:uint = 0) {
			this.handler=_handler;
			super();
			configureListeners();
			if (host && port)  {
				super.connect(host, port);
			}
		}
		
		private function configureListeners():void {
			addEventListener(Event.CLOSE, closeHandler);
			addEventListener(Event.CONNECT, connectHandler);
			addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
			addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
			addEventListener(ProgressEvent.SOCKET_DATA, socketDataHandler);
		}
			
		private function closeHandler(event:Event):void {
			trace("closeHandler: " + event);
		}
		
		private function connectHandler(event:Event):void {
			trace("connectHandler: " + event);
			//sendRequest();
		}
		
		private function ioErrorHandler(event:IOErrorEvent):void {
			trace("ioErrorHandler: " + event);
		}
		
		private function securityErrorHandler(event:SecurityErrorEvent):void {
			trace("securityErrorHandler: " + event);
		}
		
		private function socketDataHandler(event:ProgressEvent):void {
			//trace("socketDataHandler: " + event);

			if (this.bytesAvailable<4)
				return;
			methodID=this.readShort();
			length=this.readUnsignedShort();

//			if (length>this.bytesAvailable)
//				return;

			var data:ByteArray=new ByteArray();
			this.readBytes(data,0,length);

			switch (methodID){
				case MessageHandler.ServerTimeResponse:
					serverTimeResponse(data);
					break;
				case MessageHandler.NetSpeedResponse:
					netSpeedResponse(data);
					break;
				case MessageHandler.JoinServerResponse:
					joinServerResponse(data);
					break;
				case MessageHandler.JoinRoomResponse:
					joinRoomResponse(data);
					break;
				case MessageHandler.UserListNotice:
					userListNotice(data);
					break;
				case MessageHandler.RoomListNotice:
					roomListNotice(data);
					break;
				case MessageHandler.GetZoneResponse:
					getZoneResponse(data);
					break;
				case MessageHandler.ChangeRoomResponse:
					changeRoomResponse(data);
					break;
				case MessageHandler.UserLeaveNotice:
					userLeaveNotice(data);
					break;
				case MessageHandler.RoomRemoveNotice:
					roomRemoveNotice(data);
					break;
				case MessageHandler.NewUserNotice:
					newUserNotice(data);
					break;
				case MessageHandler.NewRoomNotice:
					newRoomNotice(data);
					break;
				case MessageHandler.NewMessageNotice:
					newMessageNotice(data);
					break;
				case MessageHandler.CreateRoomResponse:
					createRoomResponse(data);
					break;
				case MessageHandler.PluginNotice:
					pluginNotice(data);
					break;
				case MessageHandler.PluginResponse:
					pluginResponse(data);
					break;					
				case MessageHandler.RoomPropertyUpdateNotice:
					roomPropertyUpdateNotice(data);
					break;
				case MessageHandler.UserPropertyUpdateNotice:
					userPropertyUpdateNotice(data);
					break;
				case MessageHandler.ServerErrorResponse:
					serverErrorResponse(data);
					break;
				
					
			}
			length=-1;
			methodID=-1;			
		}
		private function sendResponseLong(methodID:int,data:Int64):void {
			this.writeShort(methodID);
			this.writeShort(8);			
			this.writeUnsignedInt(data.low);
			this.writeInt(data.high);
			this.flush();
		}
		private function sendResponseInt(methodID:int,data:int):void {
			this.writeShort(methodID);
			this.writeShort(4);
			this.writeInt(data);
			this.flush();
		}
		private function sendResponseNull(methodID:int):void {
			this.writeShort(methodID);
			this.writeShort(0);
			this.flush();
		}
		
		private function sendResponseMessage(methodID:int,data:WritingBuffer):void {
			this.writeShort(methodID);
			var len:int=data.length;
			this.writeShort(len);
			this.writeBytes(data,0,len);
			this.flush();
		}

		private function serverTimeResponse(data:ByteArray):void {
			trace("["+MessageHandler.ServerTimeResponse+"]ServerTimeResponse");
			var time:Int64=new Int64();
			time.low=data.readUnsignedInt();
			time.high=data.readInt();			
			sendResponseLong(MessageHandler.NetSpeedRequest,time);
			trace("["+MessageHandler.NetSpeedRequest+"]NetSpeedRequest");
		}
		private function netSpeedResponse(data:ByteArray):void {
			trace("["+MessageHandler.NetSpeedResponse+"]NetSpeedResponse");
			var message:NetSpeedResponseData=new NetSpeedResponseData();
			message.readFromSlice(data,0);
			handler.onConnected(message.delay);
		}
		private function joinServerResponse(data:ByteArray):void {
			trace("["+MessageHandler.JoinServerResponse+"]JoinServerResponse");
			var message:JoinServerResponseData=new JoinServerResponseData();
			message.readFromSlice(data,0);
			this.me=message.user;
			handler.onJoinServer(message.user);
		}
		private function joinRoomResponse(data:ByteArray):void {
			trace("["+MessageHandler.JoinRoomResponse+"]JoinRoomResponse");
			var message:JoinRoomResponseData=new JoinRoomResponseData();
			message.readFromSlice(data,0);
			this.room=message.room;
			handler.onJoinRoom(this.room);
		}
		private function userListNotice(data:ByteArray):void {
			trace("["+MessageHandler.UserListNotice+"]UserListNotice");
			var message:UserListNoticeData=new UserListNoticeData();
			message.readFromSlice(data,0);
			var users:Array=message.user;
			for (var i:int=0;i<users.length;i++){
				this.users.put(users[i].id,users[i]);
			}
			handler.onUserList(users);
		}
		private function roomListNotice(data:ByteArray):void {
			trace("["+MessageHandler.RoomListNotice+"]RoomListNotice");
			var message:RoomListNoticeData=new RoomListNoticeData();
			message.readFromSlice(data,0);
			var rooms:Array=message.room;
			for (var i:int=0;i<rooms.length;i++){
				this.rooms.put(rooms[i].id,rooms[i]);
			}
			handler.onRoomList(rooms);			
		}
		private function getZoneResponse(data:ByteArray):void {
			trace("["+MessageHandler.GetZoneResponse+"]GetZoneResponse");
			var message:GetZoneResponseData=new GetZoneResponseData();
			message.readFromSlice(data,0);
			this.zone=message.zone;
			handler.onZoneInfo(this.zone);		
		}
		private function changeRoomResponse(data:ByteArray):void {
			trace("["+MessageHandler.ChangeRoomResponse+"]ChangeRoomResponse");	
			var message:ChangeRoomResponseData=new ChangeRoomResponseData();
			message.readFromSlice(data,0);
			this.room=message.room;
			handler.onChangeRoom(this.room);				
		}			
		private function userLeaveNotice(data:ByteArray):void {
			trace("["+MessageHandler.UserLeaveNotice+"]UserLeaveNotice");	
			var message:UserLeaveNoticeData=new UserLeaveNoticeData();
			message.readFromSlice(data,0);
			handler.onUserLeave(message.userid);				
		}
		private function roomRemoveNotice(data:ByteArray):void {
			trace("["+MessageHandler.RoomRemoveNotice+"]RoomRemoveNotice");	
			var message:RoomRemoveNoticeData=new RoomRemoveNoticeData();
			message.readFromSlice(data,0);
			handler.onUserLeave(message.roomid);	
		}
		private function newUserNotice(data:ByteArray):void {
			trace("["+MessageHandler.NewUserNotice+"]NewUserNotice");	
			var message:NewUserNoticeData=new NewUserNoticeData();
			message.readFromSlice(data,0);
			handler.onNewUser(message.user);
		}
		private function newRoomNotice(data:ByteArray):void {
			trace("["+MessageHandler.NewRoomNotice+"]NewRoomNotice");	
			var message:NewRoomNoticeData=new NewRoomNoticeData();
			message.readFromSlice(data,0);
			handler.onNewRoom(message.room);			
		}
		private function newMessageNotice(data:ByteArray):void {
			trace("["+MessageHandler.NewMessageNotice+"]NewMessageNotice");	
			var message:NewMessageNotice=new NewMessageNotice();
			message.readFromSlice(data,0);
			handler.onNewMessage(message.router,message.message,this.users[message.userid]);
		}
		private function createRoomResponse(data:ByteArray):void {
			trace("["+MessageHandler.CreateRoomResponse+"]CreateRoomResponse");	
			var message:CreateRoomResponseData=new CreateRoomResponseData();
			message.readFromSlice(data,0);
			handler.onCreateRoom(message.room);		
		}
		private function pluginNotice(data:ByteArray):void {
			
		}
		private function pluginResponse(data:ByteArray):void {
			
		}
		private function roomPropertyUpdateNotice(data:ByteArray):void {
			trace("["+MessageHandler.UserPropertyUpdateNotice+"]UserPropertyUpdateNotice");	
			var message:UserPropertyUpdateNoticeData=new UserPropertyUpdateNoticeData();
			message.readFromSlice(data,0);
			var user:User=this.users[message.id];
			
		}

		private function userPropertyUpdateNotice(data:ByteArray):void {
			trace("["+MessageHandler.UserPropertyUpdateNotice+"]UserPropertyUpdateNotice");	
			var message:UserPropertyUpdateNoticeData=new UserPropertyUpdateNoticeData();
			message.readFromSlice(data,0);
			var user:User=this.users[message.id];
	
		}
		private function serverErrorResponse(data:ByteArray):void {
			var code:int=data.readInt();
			trace("["+MessageHandler.ServerErrorResponse+"]ServerErrorResponse:"+ReturnStatus.getName(code));
			handler.onServerError(code);
		}
		
		
		
		
		public function joinServerRequest(username:String,password:String):void {
			trace("["+MessageHandler.JoinServerRequest+"]JoinServerRequest");
			var message:JoinServerRequestData =new JoinServerRequestData();
			message.username=username;
			message.password=password;
			var bytes:WritingBuffer = new WritingBuffer();
			message.writeToBuffer(bytes);
			sendResponseMessage(MessageHandler.JoinServerRequest,bytes);
		}
		public function joinDefaultRoomRequest():void {
			trace("["+MessageHandler.JoinDefaultRoomRequest+"]JoinDefaultRoomRequest");
			sendResponseNull(MessageHandler.JoinDefaultRoomRequest);
		}
		public function changeRoomRequest(roomId:int):void {
			trace("["+MessageHandler.ChangeRoomRequest+"]ChangeRoomRequest");
			sendResponseInt(MessageHandler.ChangeRoomRequest,roomId);
		}
		public function createRoomRequest(name:String,pass:String,template:int):void {
			trace("["+MessageHandler.CreateRoomRequest+"]CreateRoomRequest");
			var message:CreateRoomRequestData =new CreateRoomRequestData();
			message.name=name;
			message.password=pass;
			message.templateid=template;
			var bytes:WritingBuffer = new WritingBuffer();
			message.writeToBuffer(bytes);
			sendResponseMessage(MessageHandler.CreateRoomRequest,bytes);
		}
		public function getZoneRequest(zoneId:int):void {
			trace("["+MessageHandler.GetZoneRequest+"]GetZoneRequest");
			var message:GetZoneRequestData =new GetZoneRequestData();
			message.zoneid=zoneId;
			var bytes:WritingBuffer = new WritingBuffer();
			message.writeToBuffer(bytes);			
			sendResponseMessage(MessageHandler.GetZoneRequest,bytes);			
		}
	}
}