/**
 * 
 */
package serverlogic;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import net.combatserver.protobuf.Plugin.Attribute;
import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Scheduled;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class ScheduledCombat1V1 extends Scheduled {
	private AtomicInteger trun = new AtomicInteger(0);

	public ScheduledCombat1V1() throws Exception {
		combatInvite();
	}

	@Override
	public void run() {
		if (getRoom()==null)
			return;
		try {
			if (trun.get()==0)
				combatPosition();
			else
				combatTrun();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public int register() {
		return 240;
	}

	@Override
	public void onNewUser(User newUser) throws Exception {
//		if (getRoom().getUserList().size()==getRoom().getMaxUsers()){
//			combatData();
//		}			
	}

	@Override
	public void onRemoveUser(int userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void combatPosition() throws Exception {
		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName("PluginCombat");
		builder.setAction("position");
		builder.setResult(trun.incrementAndGet());
		PluginData message=builder.build();
		getRoom().pluginNotice(Server.getSystemUser().getId(),message);
	}	
	private void combatInvite() throws Exception {
		Room room=Room.get(Server.getDefaultRoomId());
		Iterator<Entry<Integer, User>> it = room.getUserList().entrySet().iterator(); 
		User user = it.next().getValue();
		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName("PluginCombat");
		builder.setAction("invite");
		builder.setResult(getRoom().getId());
		PluginData message=builder.build();
		user.pluginNotice(message);
	}
	private void combatTrun() throws Exception {
		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName("PluginCombat");
		builder.setAction("trun");
		builder.setResult(trun.incrementAndGet());
		PluginData message=builder.build();
		getRoom().pluginNotice(Server.getSystemUser().getId(),message);
	}
	private void combatData() throws Exception {
		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName("PluginCombat");
		builder.setAction("data");
		builder.setResult(1);
		User user;
		Attribute.Builder userBuilder;
		Attribute.Builder creatureBuilder;
		Attribute.Builder propertyBuilder;
		for (Iterator<Entry<Integer, User>> it = getRoom().getUserList().entrySet().iterator(); it.hasNext();) {
			user = it.next().getValue();
			String value=this.getProperty("data_"+user.getId());
			String creatureids[]=value.split(",");
			userBuilder=Attribute.newBuilder();
			userBuilder.setName(String.valueOf(user.getId()));			
			for (String creatureid : creatureids){
				creatureBuilder=Attribute.newBuilder();
				creatureBuilder.setName(creatureid);
				propertyBuilder=Attribute.newBuilder();
				propertyBuilder.setName("type");
				propertyBuilder.setValue("cAngel");
				creatureBuilder.addAttributes(propertyBuilder);				
				propertyBuilder=Attribute.newBuilder();
				propertyBuilder.setName("skill");
				propertyBuilder.setValue("100020,100021,100022");
				creatureBuilder.addAttributes(propertyBuilder);
				userBuilder.addAttributes(creatureBuilder);
			}
			builder.addProperties(userBuilder);
		}
		PluginData message=builder.build();
		getRoom().pluginNotice(Server.getSystemUser().getId(),message);
	}
	
}
