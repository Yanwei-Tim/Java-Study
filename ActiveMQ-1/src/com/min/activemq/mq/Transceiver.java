package com.min.activemq.mq;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * 消息接收发送抽象类
 * @author min.zhao
 * @E-mail:zhuifeng1017@gmail.com
 * @version 创建时间：2014-3-6 下午5:24:22
 */
public abstract class Transceiver implements Runnable {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		private ActiveMQConnectionFactory connectionFactory;
	    // Connection ：JMS 客户端到JMS Provider 的连接
		protected Connection connection;
	    // Session： 一个发送或接收消息的线程
		protected Session session;
		protected int mqType;
		protected String username;
		protected String password;
		protected String brokerURL;
		
		protected boolean isStartUp;
		protected boolean isConnected;

	    abstract public void startUp();
	    abstract public void shutDown();
	    
	    /**
	     * 
	     * @param username 用户名
	     * @param password 密码
	     * @param brokerURL brokerURL
	     * @param mqType 消息队列类型, 值为 {@link Constants.MQ_TOPIC},  {@link Constants.MQ_QUEUE}
	     */
	    public Transceiver(String username, String password, String brokerURL, int mqType){
	    	this.username = username;
	    	this.password = password;
	    	this.brokerURL = brokerURL;
	    	this.mqType = mqType;
	    }
	    
	public void createConnection() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
		// ActiveMQConnection.DEFAULT_USER,
		// ActiveMQConnection.DEFAULT_PASSWORD,
				this.username, this.password, this.brokerURL
		// "tcp://localhost:61616"
		// "tcp://192.168.108.13:61616"
		);
		// 构造从工厂得到连接对象
		connection = connectionFactory.createConnection();
	}
	
	public boolean isConnected(){
		return this.isConnected;
	}
}
