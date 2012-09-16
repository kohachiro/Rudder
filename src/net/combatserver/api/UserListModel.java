package net.combatserver.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import net.combatserver.protobuf.DataStructures.User;



/**
 * User list model
 */

public class UserListModel extends AbstractListModel<Object>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6557617649340135886L;
	private final List<User> userList;
    
    public UserListModel()
    {
        userList = new ArrayList<User>();
    }

    public int getSize()
    {
        return userList.size();
    }

    public User getElementAt(int index)
    {
        return userList.get(index);
    }
    
    public void setUserList(Map<Integer, User> users)
    {
        if(userList.size() > 0)
        {
            final int lastIndex = userList.size() - 1;
            userList.clear();
            fireIntervalRemoved(this, 0, lastIndex);
        }
        for(Map.Entry<Integer, User> e : users.entrySet())
        {
            userList.add(e.getValue());
        }
        if(userList.size() > 0)
        {
            fireIntervalAdded(this, 0, userList.size() - 1);
        }
    }

    /* (non-Javadoc)
	 * @see javax.swing.AbstractListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		super.addListDataListener(l);
	}

	public void addUser(User user)
    {
        userList.add(user);
        fireIntervalAdded(this, userList.size() - 1, userList.size() - 1);
    }
    
    public void removeUser(int userId)
    {
        for(int i = 0; i < userList.size(); i++)
        {
            if(userId == userList.get(i).getId())
            {
                userList.remove(i);
                fireIntervalRemoved(this, i, i);
                break;
            }
        }
    }
}
