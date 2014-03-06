package com.min.activemq.mq;
import javax.jms.Connection;
import javax.jms.Session;


/**
 * 消息接收发送抽象类
 * @author min.zhao
 * @E-mail:zhuifeng1017@gmail.com
 * @version 创建时间：2014-3-6 下午5:24:22
 */
public abstract class Transceiver implements Runnable {
	    // Connection ：JMS 客户端到JMS Provider 的连接
		protected Connection connection = null;
	    // Session： 一个发送或接收消息的线程
		protected Session session;
		protected int mqType;
		protected String username;
		protected String password;
		protected String brokerURL;
		
		protected boolean isStartUp;
	    
	    abstract public void startUp();
	    abstract public void shutDown();
	    
	    public Transceiver(String username, String password, String brokerURL, int mqType){
	    	this.username = username;
	    	this.password = password;
	    	this.brokerURL = brokerURL;
	    	this.mqType = mqType;
	    }
}
