package net.combatserver.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;

import net.combatserver.protobuf.DataStructures.Room;




/**
 * Room list model
 */

public class RoomListModel extends AbstractListModel<Object>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5585282136072239902L;
	private final List<Room> roomList;
    
    public RoomListModel()
    {
        roomList = new ArrayList<Room>();
    }

    public int getSize()
    {
        return roomList.size();
    }

    public Room getElementAt(int index)
    {
        return roomList.get(index);
    }
    
    public void setRoomList(Map<Integer, Room> rooms)
    {
        if(roomList.size() > 0)
        {
            final int lastIndex = roomList.size() - 1;
            roomList.clear();
            fireIntervalRemoved(this, 0, lastIndex);
        }
        for(Map.Entry<Integer, Room> e : rooms.entrySet())
        {
            roomList.add(e.getValue());
        }
        if(roomList.size() > 0)
        {
            fireIntervalAdded(this, 0, roomList.size() - 1);
        }
    }
    public void addRoom(Room room)
    {
        roomList.add(room);
        fireIntervalAdded(this, roomList.size() - 1, roomList.size() - 1);
    }
    
    public void removeRoom(Room room)
    {
        int roomId = room.getId();
        for(int i = 0; i < roomList.size(); i++)
        {
            if(roomId == roomList.get(i).getId())
            {
                roomList.remove(i);
                fireIntervalRemoved(this, i, i);
                break;
            }
        }
    }
    
    public void updateRoom(Room room)
    {
        int roomId = room.getId();
        for(int i = 0; i < roomList.size(); i++)
        {
            if(roomId == roomList.get(i).getId())
            {
                fireContentsChanged(this, i, i);
                break;
            }
        }
    }
}
