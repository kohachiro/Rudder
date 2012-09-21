package net.combatserver.protobuf {
	public final class MessageRouter {
		public static const ROUTER_USER:int = -1;
		public static const ROUTER_ROOM:int = -2;
		public static const ROUTER_REGION:int = -3;
		public static const ROUTER_SERVER:int = -4;
		public function getName(enum:int):String { 
			switch (enum){
				case ROUTER_USER:
					return "ROUTER_USER";
				case ROUTER_ROOM:
					return "ROUTER_ROOM";
				case ROUTER_REGION:
					return "ROUTER_REGION";
				case ROUTER_SERVER:
					return "ROUTER_SERVER";
				default:
					return "";
			}
		}
	}
}
