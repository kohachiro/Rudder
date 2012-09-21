package net.combatserver.util;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.combatserver.core.AbstractServer;
import net.combatserver.core.RoomManager;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Team;
import net.combatserver.serverlogic.Template;
import net.combatserver.serverlogic.Region;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author kohachiro
 *
 */
public class ParseXML {

	/**
	 * 
	 */
	public ParseXML() {
		// TODO Auto-generated constructor stub
	}
	public boolean parse(InputStream is) {
		try {
			DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			Document doc = dombuilder.parse(is);
			Element root = doc.getDocumentElement();
			NodeList nodeList = root.getChildNodes();
			if (nodeList != null) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node server = nodeList.item(i);
					if (server.getNodeType() == Node.ELEMENT_NODE) {
						parseServer(server);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void parseServer( Node server ) {
		for (Node node = server.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (node.getNodeName().equals("name")) {
					AbstractServer.name = parseString(node);	
				}else
				if (node.getNodeName().equals("port")) {
					AbstractServer.port = parseInteger(node);
				}else
				if (node.getNodeName().equals("policy_file_port")) {
					AbstractServer.PolicyFilePort = parseInteger(node);
				}else
				if (node.getNodeName().equals("logfile")) {
					AbstractServer.logFile = parseString(node);
				}else
				if (node.getNodeName().equals("logfile_limit_mb")) {
					AbstractServer.logFileLimit = parseInteger(node);
				}else
				if (node.getNodeName().equals("default_room_id")) {
					AbstractServer.defaultRoom = parseInteger(node);
				}else
				if (node.getNodeName().equals("region")) {
					NodeList regions = node.getChildNodes();
					RoomManager.initNewRegion(parseRegion(regions));
				}else
				if (node.getNodeName().equals("key")) {
					System.out.print(node.getFirstChild().getNodeValue());
				}
			}
		}		
	}

	private Region parseRegion(NodeList regions) {
		Region regionObj = new Region(parseInteger(findNode("id", regions)),
				parseString(findNode("name", regions)), parseInteger(findNode(
						"send_room_change_to", regions)), parseBoolean(findNode(
						"send_user_count_change", regions)),parseBoolean(findNode("create_room", regions)),parseInteger(findNode("max_rooms", regions)));

		for (int i = 0; i < regions.getLength(); i++) {
			Node region = regions.item(i);
			if (region.getNodeName().equals("property"))
				regionObj.addProperty(parseName(region), parseString(region));
			if (region.getNodeName().equals("team")) {
				NodeList teams = region.getChildNodes();
				//Team teamObj = 
						parseTeam(teams, regionObj);
			}
			if (region.getNodeName().equals("room")) {
				NodeList rooms = region.getChildNodes();
				Room roomObj = parseRoom(rooms, regionObj);
				RoomManager.initNewRoom(roomObj);
			}
			if (region.getNodeName().equals("room_template")) {
				NodeList templates = region.getChildNodes();
				Template templateObj = parseTemplate(templates, regionObj);
				RoomManager.initTemplate(templateObj);
			}
		}
		return regionObj;
	}

	private Team parseTeam(NodeList teams, Region region) {
		// TODO Auto-generated method stub
		return null;
	}

	private Template parseTemplate(NodeList templates, Region region) {
		Template templateObj = new Template(parseInteger(findNode("id",
				templates)), parseString(findNode("name", templates)), region
				,parseString(findNode("scheduled", templates)),parseInteger(findNode("max_users", templates)));
		for (int i = 0; i < templates.getLength(); i++) {
			Node template = templates.item(i);
			if (template.getNodeName().equals("property"))
				templateObj.addProperty(parseName(template),
						parseString(template));
		}
		return templateObj;
	}

	private Room parseRoom(NodeList rooms, Region region) {
		Room roomObj = new Room(parseInteger(findNode("id", rooms)),
				parseString(findNode("name", rooms)), 
				parseBoolean(findNode("visible", rooms)), 
				parseBoolean(findNode("sendUserList", rooms)),				
				0, 
				parseInteger(findNode("max_users", rooms)), 
				parseString(findNode("password", rooms)), 
				region, AbstractServer.systemUser.getId());
		String[] tmp=parseStringArray(findNode("from", rooms));
		for(int i=0;i<tmp.length;i++)
		    roomObj.addFrom(Integer.parseInt(tmp[i]));		
		roomObj.setSpectatorLimit(parseInteger(findNode("spectator_limit",
				rooms)));
		for (int i = 0; i < rooms.getLength(); i++) {
			Node room = rooms.item(i);
			if (room.getNodeName().equals("property"))
				roomObj.addProperty(parseName(room), parseString(room));
		}
		return roomObj;
	}

	private Node findNode(String name,NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals(name))
				return node;
		}
		return null;
	}
	private String[] parseStringArray(Node node) {
		return node.getAttributes().getNamedItem("value").getNodeValue().split(",");
	}
	private String parseName(Node node) {
		return node.getAttributes().getNamedItem("name").getNodeValue();
	}	
	private String parseString(Node node) {
		return node.getAttributes().getNamedItem("value").getNodeValue();
	}
	private boolean parseBoolean(Node node) {
		return Boolean.parseBoolean(node.getAttributes().getNamedItem("value").getNodeValue());
	}
	private int parseInteger(Node node) {
		return Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue());
	}
}
