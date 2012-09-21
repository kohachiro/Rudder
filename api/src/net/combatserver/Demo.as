package net.combatserver
{
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.ByteArray;
	import fl.controls.List;
	import net.combatserver.protobuf.Room;
	import net.combatserver.protobuf.User;
	import net.combatserver.protobuf.Zone;
	
	public class Demo extends Sprite implements ConnectEvent
	{
		var connection:Connection;
		var ui:DemoUI;
		public function Demo()
		{
			var bytes:ByteArray=new ByteArray();
			connection=new Connection(this,"127.0.0.1",6789);
			ui=new DemoUI();
			this.addEventListener(Event.ADDED_TO_STAGE,init);		
		}
		public function init(e:Event):void {
			addChild(ui);
			showLoadScreen();
			this.removeEventListener(Event.ADDED_TO_STAGE,close);
			ui.loginButton.addEventListener(MouseEvent.CLICK,doLogin);
			ui.roomList.addEventListener(MouseEvent.CLICK,doChangeRoom);
			ui.createRoomButton.addEventListener(MouseEvent.CLICK,doCreateRoom);
			ui.userList.addEventListener(MouseEvent.CLICK,doToUser)
		}		
		
		public function close(e:Event):void {
			
		}
		public function showLoadScreen():void {
			hiddenAll();
			ui.loadingLabel.visible=true;
			ui.loadingLabel.text="loading...";
		}
		public function showLoginScreen():void {
			hiddenAll();
			ui.passInput.visible=true;
			ui.userInput.visible=true;
			ui.userLabel.visible=true;
			ui.passLabel.visible=true;
			ui.loginButton.visible=true;
		}
		public function showRoomScreen():void {
			hiddenAll();
			ui.roomList.visible=true;
			ui.roomListLabel.visible=true;
			ui.userList.visible=true;
			ui.userListLabel.visible=true;
			ui.createRoomButton.visible=true;
			ui.createRoomLabel.visible=true;
			ui.roomNameLabel.visible=true;
			ui.roomNameInput.visible=true;
			ui.roomPassLabel.visible=true;
			ui.roomPassInput.visible=true;
		}
		public function showChatScreen():void {
			hiddenAll();
			ui.chatArea.visible=true;
			ui.chatButton.visible=true;
			ui.chatInput.visible=true;
			ui.chatModeCombo.visible=true;
			ui.roomList.visible=true;
			ui.roomListLabel.visible=true;
			ui.userList.visible=true;
			ui.userListLabel.visible=true;		
		}
		public function hiddenAll():void {
			ui.loadingLabel.visible=false;
			ui.chatArea.visible=false;
			ui.chatButton.visible=false;
			ui.chatInput.visible=false;
			ui.chatModeCombo.visible=false;
			ui.createRoomButton.visible=false;
			ui.createRoomLabel.visible=false;
			ui.loadingLabel.visible=false;
			ui.loginButton.visible=false;
			ui.passInput.visible=false;
			ui.passLabel.visible=false;
			ui.roomList.visible=false;
			ui.roomListLabel.visible=false;
			ui.roomNameInput.visible=false;
			ui.roomNameLabel.visible=false;
			ui.roomPassInput.visible=false;
			ui.roomPassLabel.visible=false;
			ui.userInput.visible=false;
			ui.userLabel.visible=false;
			ui.userList.visible=false;
			ui.userListLabel.visible=false;
		}
	
		public function onNewUser(user:User):void
		{
			ui.userList.addItem({label:user.name, data:user.id})
		}
		
		public function onNewRoom(room:Room):void
		{
			ui.userList.addItem({label:room.name, data:room.id})
		}
		
		public function onJoinRoom(room:Room):void
		{
			showRoomScreen();
		}
		
		public function onRoomList(rooms:Array):void
		{
			ui.roomList.removeAll();
			for (var i:int=0;i<rooms.length;i++){
				ui.roomList.addItem({label:rooms[i].name, data:rooms[i].id})
			}
		}
		
		public function onUserList(users:Array):void
		{
			ui.userList.removeAll();
			for (var i:int=0;i<users.length;i++){
				ui.userList.addItem({label:users[i].name, data:users[i].id})
			}
		}
		
		public function onRegionInfo(region:Region):void
		{
			connection.joinDefaultRoomRequest();
		}
		
		public function onConnected(delay:int):void
		{
			showLoginScreen();
			ui.passInput.text="9Nw7Ke9kREe";
			ui.userInput.text="AW7pxSWfy1NI1i4BHgcy";
		}
	
		public function onCheckTime(time:Number):void
		{
			
		}
		
		public function onUserLeave(userId:int):void
		{
			for (var i:int=0;i<ui.userList.length;i++){
				if (ui.userList[i].data==userId){
					ui.userList.removeItemAt(i);
					break;
				}
			}
		}
		
		public function onCreateRoom(room:Room):void
		{
			this.onNewRoom(room);
		}
		
		public function onChangeRoom(room:Room):void
		{
			showChatScreen();
		}
		
		public function onJoinServer(user:User):void
		{
			ui.userListLabel.text="User:"+user.name;
			connection.getRegionRequest(1);
		}
		
		public function onNewMessage(router:int, message:String, user:User):void
		{
			//TODO: implement function
		}
		
		public function onRoomRemove(roomId:int):void
		{
			for (var i:int=0;i<ui.roomList.length;i++){
				if (ui.roomList[i].data==roomId){
					ui.roomList.removeItemAt(i);
					break;
				}
			}
		}
		
		public function onServerError(code:int):void
		{
			
		}
		public function doLogin(e:MouseEvent):void
		{
			showLoadScreen();
			if (ui.userInput.text!=""&&ui.passInput.text!="")
				connection.joinServerRequest(ui.userInput.text,ui.passInput.text);
		}	
		public function doChangeRoom(e:MouseEvent):void
		{
			showLoadScreen();
			if(ui.roomList.selectedItem.data>0)
				connection.changeRoomRequest(ui.roomList.selectedItem.data);
		}
		public function doCreateRoom(e:MouseEvent):void
		{
			showLoadScreen();
			if(ui.roomNameInput.text!="")
				connection.createRoomRequest(ui.roomNameInput.text,ui.roomPassInput.text,4);
		}
		public function doToUser(e:MouseEvent):void
		{

		}		
	}
}