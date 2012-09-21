package net.combatserver.core;

import java.nio.ByteBuffer;

/**
 * net framework的消息类处理接口<br/>
 * 通过net framework的实现来达到抽象服务器 AbstractServer<br/>
 * 独立于网络链接的高级抽象实现,便于外部接口升级更新换框架<br/>
 * @author kohachiro
 *
 */
public interface ServerHandler {	
	/**
	 * 发送消息类 
	 * @param methodID
	 * @param message
	 * @param channel
	 * @throws Exception
	 */
	public abstract void sendResponse(int methodID,ByteBuffer message,Object channel) throws Exception;
	/**
	 * 发送消息类 不同于 sendResponse的是发送完就关闭 用于实现类似crossdomain.xml之类的http请求
	 * @param message
	 * @param channel
	 * @throws Exception
	 */
	public abstract void sendResponse(ByteBuffer message,Object channel,boolean isClose) throws Exception;
	/**
	 * 关闭连接
	 * @param channel
	 * @throws Exception
	 */
	public abstract void close(Object channel) throws Exception;
	/**
	 * 获取IP地址
	 * @param channel
	 */
	public abstract String getIP(Object channel);
	/**
	 * 获取端口
	 * @param channel
	 */
	public abstract int getServerPort(Object channel) ;
}