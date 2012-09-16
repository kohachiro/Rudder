package net.combatserver{
	import net.combatserver.protobuf.*;	
	
	public interface ConnectEvent{	
		function onNewUser(user:User):void;
		function onNewRoom(room:Room):void;
		function onJoinRoom(room:Room):void;
		function onRoomList(rooms:Array):void;
		function onUserList(users:Array):void;
		function onZoneInfo(zone:Zone):void;
		function onConnected(delay:int): void;
		function onCheckTime(time:Number):void;
		function onUserLeave(userId:int):void;
		function onCreateRoom(room:Room):void;
		function onChangeRoom(room:Room):void;
		function onJoinServer(user:User):void;
		function onNewMessage(router:int,message:String,user:User):void;
		function onRoomRemove(roomId:int):void ;
		function onServerError(code:int):void ;
	
	}
	
}