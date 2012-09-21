package net.combatserver.core;

import java.nio.ByteBuffer;

/**
 * net framework����Ϣ�ദ��ӿ�<br/>
 * ͨ��net framework��ʵ�����ﵽ��������� AbstractServer<br/>
 * �������������ӵĸ߼�����ʵ��,�����ⲿ�ӿ��������»����<br/>
 * @author kohachiro
 *
 */
public interface ServerHandler {	
	/**
	 * ������Ϣ�� 
	 * @param methodID
	 * @param message
	 * @param channel
	 * @throws Exception
	 */
	public abstract void sendResponse(int methodID,ByteBuffer message,Object channel) throws Exception;
	/**
	 * ������Ϣ�� ��ͬ�� sendResponse���Ƿ�����͹ر� ����ʵ������crossdomain.xml֮���http����
	 * @param message
	 * @param channel
	 * @throws Exception
	 */
	public abstract void sendResponse(ByteBuffer message,Object channel,boolean isClose) throws Exception;
	/**
	 * �ر�����
	 * @param channel
	 * @throws Exception
	 */
	public abstract void close(Object channel) throws Exception;
	/**
	 * ��ȡIP��ַ
	 * @param channel
	 */
	public abstract String getIP(Object channel);
	/**
	 * ��ȡ�˿�
	 * @param channel
	 */
	public abstract int getServerPort(Object channel) ;
}