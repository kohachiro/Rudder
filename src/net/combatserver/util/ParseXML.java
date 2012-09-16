package net.combatserver.util;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.combatserver.core.AbstractServer;
import net.combatserver.core.RoomManager;
import net.combatserver.serverlogic.Room;
import net.combatserver.serverlogic.Team;
import net.combatserver.serverlogic.Template;
import net.combatserver.serverlogic.Zone;

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
				if (node.getNodeName().equals("zone")) {
					NodeList zones = node.getChildNodes();
					RoomManager.initNewZone(parseZone(zones));
				}else
				if (node.getNodeName().equals("key")) {
					System.out.print(node.getFirstChild().getNodeValue());
				}
			}
		}		
	}

	private Zone parseZone(NodeList zones) {
		Zone zoneObj = new Zone(parseInteger(findNode("id", zones)),
				parseString(findNode("name", zones)), parseInteger(findNode(
						"send_room_change_to", zones)), parseBoolean(findNode(
						"send_user_count_change", zones)),parseBoolean(findNode("create_room", zones)),parseInteger(findNode("max_rooms", zones)));

		for (int i = 0; i < zones.getLength(); i++) {
			Node zone = zones.item(i);
			if (zone.getNodeName().equals("property"))
				zoneObj.addProperty(parseName(zone), parseString(zone));
			if (zone.getNodeName().equals("team")) {
				NodeList teams = zone.getChildNodes();
				//Team teamObj = 
						parseTeam(teams, zoneObj);
			}
			if (zone.getNodeName().equals("room")) {
				NodeList rooms = zone.getChildNodes();
				Room roomObj = parseRoom(rooms, zoneObj);
				RoomManager.initNewRoom(roomObj);
			}
			if (zone.getNodeName().equals("room_template")) {
				NodeList templates = zone.getChildNodes();
				Template templateObj = parseTemplate(templates, zoneObj);
				RoomManager.initTemplate(templateObj);
			}
		}
		return zoneObj;
	}

	private Team parseTeam(NodeList teams, Zone zone) {
		// TODO Auto-generated method stub
		return null;
	}

	private Template parseTemplate(NodeList templates, Zone zone) {
		Template templateObj = new Template(parseInteger(findNode("id",
				templates)), parseString(findNode("name", templates)), zone
				,parseString(findNode("scheduled", templates)),parseInteger(findNode("max_users", templates)));
		for (int i = 0; i < templates.getLength(); i++) {
			Node template = templates.item(i);
			if (template.getNodeName().equals("property"))
				templateObj.addProperty(parseName(template),
						parseString(template));
		}
		return templateObj;
	}

	private Room parseRoom(NodeList rooms, Zone zone) {
		Room roomObj = new Room(parseInteger(findNode("id", rooms)),
				parseString(findNode("name", rooms)), 
				parseBoolean(findNode("visible", rooms)), 
				parseBoolean(findNode("sendUserList", rooms)),				
				0, 
				parseInteger(findNode("max_users", rooms)), 
				parseString(findNode("password", rooms)), 
				zone, AbstractServer.systemUser.getId());
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
