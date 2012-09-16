package serverlogic;

import net.combatserver.protobuf.NewMessage.MessageRouter;
import net.combatserver.serverlogic.Message;
import net.combatserver.serverlogic.Scheduled;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */

public class ScheduledDemo extends Scheduled {
	/**
	 * 
	 */
	public ScheduledDemo() {
		
	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.serverlogic.Scheduled#register()
	 */
	@Override
	public int register() {
		return 200;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (getRoom()==null)
			return;
		try {
			System.out.println("sendMessage");
			getRoom().sendToRoom(new Message("ScheduledDemo", Server.getSystemUser(), MessageRouter.ROUTER_ROOM_VALUE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNewUser(User sender) throws Exception {
		System.out.println("ScheduledDemo.onNewUser");
	}

	@Override
	public void onRemoveUser(int userId) throws Exception {
		System.out.println("ScheduledDemo.onRemoveUser");
		
	}
}
