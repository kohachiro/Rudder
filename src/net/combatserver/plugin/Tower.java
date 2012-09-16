/**
 * 
 */
package net.combatserver.plugin;

/**
 * @author Administrator
 *
 */
public class Tower {

    int HP;
    int attack;
    int x;
    int y;
    public int postion[][];
	/**
	 * 
	 */
	public Tower() {
		// TODO Auto-generated constructor stub
	}

    public Tower(int hp, int att)
    {
        HP = hp;
        attack = att;
    }

    public static int getTowerNumber(String str)
    {
        if(str.length() == 0)
            return 0;
        int n = 0;
        String strArray[] = str.split("\001");
        for(int i = 0; i < strArray.length; i++)
        {
            String string[] = strArray[i].split("\004");
            if(Integer.parseInt(string[0]) > 0)
                n++;
        }

        return n;
    }

    public void initPostion(int i)
    {
        x = postion[i][0];
        y = postion[i][1];
    }

    public String toMessage()
    {
        StringBuffer towerBuffer = new StringBuffer();
        towerBuffer.append(HP);
        towerBuffer.append('\004');
        towerBuffer.append(attack);
        towerBuffer.append('\004');
        towerBuffer.append(x);
        towerBuffer.append('\004');
        towerBuffer.append(y);
        return towerBuffer.toString();
    }

    public static Tower[] getTowersFromString(String message)
    {
        if(message.length() == 0)
            return new Tower[0];
        String strings[] = message.split("\001");
        Tower towers[] = new Tower[strings.length];
        for(int i = 0; i < strings.length; i++)
        {
            String values[] = strings[i].split("\004");
            towers[i] = new Tower(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            towers[i].x = Integer.parseInt(values[2]);
            towers[i].y = Integer.parseInt(values[3]);
        }

        return towers;
    }

    public int getHP()
    {
        return HP;
    }

    public int getAttack()
    {
        return attack;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setHP(int hP)
    {
        HP = hP;
    }

    public void setAttack(int attack)
    {
        this.attack = attack;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

}
