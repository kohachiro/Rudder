package serverlogic;

import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Plugin.Attribute;
import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.protobuf.Plugin.PluginRequestData;
import net.combatserver.serverlogic.Plugin;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Server;
import net.combatserver.serverlogic.User;

public class PluginCombat extends Plugin {
	public PluginData castSkill(PluginRequestData data, User sender) throws Exception {
		return null;
	}
	public PluginData attack(PluginRequestData data, User sender) throws Exception {
		return null;
	}
	public PluginData move(PluginRequestData data, User sender) throws Exception {
		return null;
	}
	public PluginData join(PluginRequestData data, User sender) throws Exception {
		String key="data_"+sender.getId();
		String value="";
		Property property;
		for (Iterator<Property> it = data.getPropertiesList().iterator(); it
				.hasNext();) {
			property=it.next();
			if (value!="")
				value+=",";
			value+=property.getName();
		}
		sender.getRoom().getScheduled().removeProperty(key);
		sender.getRoom().getScheduled().addProperty(key, value);
		for (Iterator<Entry<Integer, User>> it = sender.getRoom().getUserList().entrySet().iterator(); it.hasNext();) {
			if (sender.getRoom().getScheduled().getProperty("data_"+it.next().getValue().getId())==null){
				return null;				
			}
		}
		data(sender.getRoom());
		return null;
	}

	public PluginData loadFinish(PluginRequestData data, User sender) throws Exception {
		String key="load_"+sender.getId();	
		sender.getRoom().getScheduled().removeProperty(key);
		sender.getRoom().getScheduled().addProperty(key, "1");
		for (Iterator<Entry<Integer, User>> it = sender.getRoom().getUserList().entrySet().iterator(); it.hasNext();)
			if (sender.getRoom().getScheduled().getProperty("load_"+it.next().getValue().getId())==null)
				return null;				

		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName("PluginCombat");
		builder.setAction("start");
		PluginData message=builder.build();
		sender.getRoom().pluginNotice(Server.getSystemUser().getId(),message);
		sender.getRoom().runScheduled();
		return null;
	}
	
	@Override
	public PluginData invoke(PluginRequestData data, User user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register() {
		// TODO Auto-generated method stub
		return "PluginCombat";
	}

	
	private void data(Room room) throws Exception {
		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName("PluginCombat");
		builder.setAction("data");
		builder.setResult(1);
		User user;
		Attribute.Builder userBuilder;
		Attribute.Builder creatureBuilder;
		Attribute.Builder propertyBuilder;
		for (Iterator<Entry<Integer, User>> it = room.getUserList().entrySet().iterator(); it.hasNext();) {
			user = it.next().getValue();
			String value=room.getScheduled().getProperty("data_"+user.getId());
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
		room.pluginNotice(Server.getSystemUser().getId(),message);
	}	
}
