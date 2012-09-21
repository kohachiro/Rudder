package net.combatserver.protobuf {
	public final class MessageHandler {
		public static const PolicyFileRequest:int = 15472;
		public static const HttpGetRequest:int = 18245;
		public static const HttpPostRequest:int = 20559;
		public static const PingIntervalRequest:int = 300;
		public static const RoomListNotice:int = 1;
		public static const UserListNotice:int = 2;
		public static const RegionListNotice:int = 3;
		public static const NetSpeedRequest:int = 304;
		public static const NetSpeedResponse:int = 5;
		public static const GetRegionRequest:int = 6;
		public static const GetRegionResponse:int = 7;
		public static const CreateRoomRequest:int = 8;
		public static const CreateRoomResponse:int = 9;
		public static const NewRoomNotice:int = 10;
		public static const RoomPropertyUpdateRequest:int = 11;
		public static const RoomPropertyUpdateNotice:int = 12;
		public static const UserPropertyUpdateRequest:int = 13;
		public static const UserPropertyUpdateNotice:int = 14;
		public static const PrivateMessageRequest:int = 25;
		public static const MessageByNameRequest:int = 26;
		public static const RoomMessageRequest:int = 27;
		public static const RegionMessageRequest:int = 28;
		public static const ServerMessageRequest:int = 29;
		public static const NewMessageNotice:int = 30;
		public static const PluginRequest:int = 40;
		public static const PluginResponse:int = 41;
		public static const PluginNotice:int = 42;
		public static const JoinServerRequest:int = 50;
		public static const JoinServerResponse:int = 51;
		public static const JoinDefaultRoomRequest:int = 352;
		public static const JoinRoomResponse:int = 53;
		public static const ChangeRoomRequest:int = 54;
		public static const ChangeRoomResponse:int = 55;
		public static const NewUserNotice:int = 56;
		public static const LogoutRequest:int = 60;
		public static const UserLeaveNotice:int = 61;
		public static const DisconnectedRequest:int = 62;
		public static const RoomRemoveNotice:int = 68;
		public static const ServerErrorResponse:int = 369;
		public static const ServerTimeResponse:int = 380;
		public function getName(enum:int):String { 
			switch (enum){
				case PolicyFileRequest:
					return "PolicyFileRequest";
				case HttpGetRequest:
					return "HttpGetRequest";
				case HttpPostRequest:
					return "HttpPostRequest";
				case PingIntervalRequest:
					return "PingIntervalRequest";
				case RoomListNotice:
					return "RoomListNotice";
				case UserListNotice:
					return "UserListNotice";
				case RegionListNotice:
					return "RegionListNotice";
				case NetSpeedRequest:
					return "NetSpeedRequest";
				case NetSpeedResponse:
					return "NetSpeedResponse";
				case GetRegionRequest:
					return "GetRegionRequest";
				case GetRegionResponse:
					return "GetRegionResponse";
				case CreateRoomRequest:
					return "CreateRoomRequest";
				case CreateRoomResponse:
					return "CreateRoomResponse";
				case NewRoomNotice:
					return "NewRoomNotice";
				case RoomPropertyUpdateRequest:
					return "RoomPropertyUpdateRequest";
				case RoomPropertyUpdateNotice:
					return "RoomPropertyUpdateNotice";
				case UserPropertyUpdateRequest:
					return "UserPropertyUpdateRequest";
				case UserPropertyUpdateNotice:
					return "UserPropertyUpdateNotice";
				case PrivateMessageRequest:
					return "PrivateMessageRequest";
				case MessageByNameRequest:
					return "MessageByNameRequest";
				case RoomMessageRequest:
					return "RoomMessageRequest";
				case RegionMessageRequest:
					return "RegionMessageRequest";
				case ServerMessageRequest:
					return "ServerMessageRequest";
				case NewMessageNotice:
					return "NewMessageNotice";
				case PluginRequest:
					return "PluginRequest";
				case PluginResponse:
					return "PluginResponse";
				case PluginNotice:
					return "PluginNotice";
				case JoinServerRequest:
					return "JoinServerRequest";
				case JoinServerResponse:
					return "JoinServerResponse";
				case JoinDefaultRoomRequest:
					return "JoinDefaultRoomRequest";
				case JoinRoomResponse:
					return "JoinRoomResponse";
				case ChangeRoomRequest:
					return "ChangeRoomRequest";
				case ChangeRoomResponse:
					return "ChangeRoomResponse";
				case NewUserNotice:
					return "NewUserNotice";
				case LogoutRequest:
					return "LogoutRequest";
				case UserLeaveNotice:
					return "UserLeaveNotice";
				case DisconnectedRequest:
					return "DisconnectedRequest";
				case RoomRemoveNotice:
					return "RoomRemoveNotice";
				case ServerErrorResponse:
					return "ServerErrorResponse";
				case ServerTimeResponse:
					return "ServerTimeResponse";
				default:
					return "";
			}
		}
	}
}
