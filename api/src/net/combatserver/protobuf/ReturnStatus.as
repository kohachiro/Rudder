package net.combatserver.protobuf {
	public final class ReturnStatus {
		public static const STATUS_OK:int = 0;
		public static const STATUS_FAILED:int = -10;
		public static const STATUS_ROOM_CREATE_FAILED:int = -11;
		public static const STATUS_ROOM_FULL:int = -12;
		public static const STATUS_ROOM_LOCKED:int = -13;
		public static const STATUS_ROOM_NOT_FOUND:int = -14;
		public static const STATUS_ROOM_NEED_PASWORD:int = -15;
		public static const STATUS_ROOM_WRONG_PASSWORD:int = -16;
		public static const STATUS_ROOM_TEMPLATE_NOT_FOUND:int = -17;
		public static const STATUS_ROOM_NULL:int = -18;
		public static const STATUS_REGION_FULL:int = -19;
		public static const STATUS_REGION_NOT_FOUND:int = -20;
		public static const STATUS_SERVER_FULL:int = -21;
		public static const STATUS_USER_NOT_FOUND:int = -22;
		public static const STATUS_PLUGIN_NOT_FOUND:int = -25;
		public static const STATUS_PLUGIN_ACTION_NOT_FOUND:int = -26;
		public static const STATUS_PLUGIN_PARAMETER_ERROR:int = -27;
		public static const STATUS_NAME_CONTAINS_BAD_WORD:int = -30;
		public static const STATUS_NAME_USED:int = -31;
		public static const STATUS_AUTH_FAILED:int = -33;
		public static const STATUS_INVID_REQUEST:int = -34;
		public static const STATUS_INVID_REQUEST_DATA:int = -35;
		public static const STATUS_INTERNAL_ERROR:int = -36;
		public static const STATUS_UNKNOWN_ERROR:int = -37;
		public static const STATUS_CONNECT_ERROR:int = -38;
		public static const STATUS_EXPIRED_CERTIFICATE:int = -39;
		public static const STATUS_SCHEDULED_NOT_FOUND:int = -40;
		public function getName(enum:int):String { 
			switch (enum){
				case STATUS_OK:
					return "STATUS_OK";
				case STATUS_FAILED:
					return "STATUS_FAILED";
				case STATUS_ROOM_CREATE_FAILED:
					return "STATUS_ROOM_CREATE_FAILED";
				case STATUS_ROOM_FULL:
					return "STATUS_ROOM_FULL";
				case STATUS_ROOM_LOCKED:
					return "STATUS_ROOM_LOCKED";
				case STATUS_ROOM_NOT_FOUND:
					return "STATUS_ROOM_NOT_FOUND";
				case STATUS_ROOM_NEED_PASWORD:
					return "STATUS_ROOM_NEED_PASWORD";
				case STATUS_ROOM_WRONG_PASSWORD:
					return "STATUS_ROOM_WRONG_PASSWORD";
				case STATUS_ROOM_TEMPLATE_NOT_FOUND:
					return "STATUS_ROOM_TEMPLATE_NOT_FOUND";
				case STATUS_ROOM_NULL:
					return "STATUS_ROOM_NULL";
				case STATUS_REGION_FULL:
					return "STATUS_REGION_FULL";
				case STATUS_REGION_NOT_FOUND:
					return "STATUS_REGION_NOT_FOUND";
				case STATUS_SERVER_FULL:
					return "STATUS_SERVER_FULL";
				case STATUS_USER_NOT_FOUND:
					return "STATUS_USER_NOT_FOUND";
				case STATUS_PLUGIN_NOT_FOUND:
					return "STATUS_PLUGIN_NOT_FOUND";
				case STATUS_PLUGIN_ACTION_NOT_FOUND:
					return "STATUS_PLUGIN_ACTION_NOT_FOUND";
				case STATUS_PLUGIN_PARAMETER_ERROR:
					return "STATUS_PLUGIN_PARAMETER_ERROR";
				case STATUS_NAME_CONTAINS_BAD_WORD:
					return "STATUS_NAME_CONTAINS_BAD_WORD";
				case STATUS_NAME_USED:
					return "STATUS_NAME_USED";
				case STATUS_AUTH_FAILED:
					return "STATUS_AUTH_FAILED";
				case STATUS_INVID_REQUEST:
					return "STATUS_INVID_REQUEST";
				case STATUS_INVID_REQUEST_DATA:
					return "STATUS_INVID_REQUEST_DATA";
				case STATUS_INTERNAL_ERROR:
					return "STATUS_INTERNAL_ERROR";
				case STATUS_UNKNOWN_ERROR:
					return "STATUS_UNKNOWN_ERROR";
				case STATUS_CONNECT_ERROR:
					return "STATUS_CONNECT_ERROR";
				case STATUS_EXPIRED_CERTIFICATE:
					return "STATUS_EXPIRED_CERTIFICATE";
				case STATUS_SCHEDULED_NOT_FOUND:
					return "STATUS_SCHEDULED_NOT_FOUND";
				default:
					return "";
			}
		}
	}
}
