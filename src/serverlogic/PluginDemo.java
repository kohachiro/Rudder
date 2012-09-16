package serverlogic;

import java.util.Iterator;
import java.util.Map.Entry;

import net.combatserver.protobuf.DataStructures.Property;
import net.combatserver.protobuf.Plugin.PluginData;
import net.combatserver.protobuf.Plugin.PluginNoticeData;
import net.combatserver.protobuf.Plugin.PluginRequestData;
import net.combatserver.serverlogic.Plugin;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.User;

/**
 * @author kohachiro
 *
 */
public class PluginDemo extends Plugin {

	/**
	 * 
	 */
	public PluginDemo() {
		// TODO Auto-generated constructor stub
	}
	public PluginData roomlist(PluginRequestData data, User user) {
		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName(register());
		builder.setAction(data.getAction());
		builder.setResult(1);
		PluginData message=builder.build();
		return message;
	}
	/* (non-Javadoc)
	 * @see net.com.sunkey.serverlogic.Plugin#invoke(java.lang.Object, java.lang.Object)
	 */
	@Override
	public PluginData invoke(PluginRequestData data, User user) throws Exception {
		PluginData.Builder builder=PluginData.newBuilder();
		builder.setName(register());
		builder.setAction(data.getAction());
		PluginData message=builder.build();
		return message;
	}

	/* (non-Javadoc)
	 * @see net.com.sunkey.serverlogic.Plugin#register()
	 */
	@Override
	public String register() {
		return "PluginDemo";
	}
}
