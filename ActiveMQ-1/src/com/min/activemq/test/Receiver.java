package com.min.activemq.test;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver extends Thread{
	 // ConnectionFactory ：连接工厂，JMS 用它创建连接
	ActiveMQConnectionFactory connectionFactory;
    // Connection ：JMS 客户端到JMS Provider 的连接
    private Connection connection = null;
    // Session： 一个发送或接收消息的线程
    private Session session;
    
    private boolean running =false;
  
    public Receiver(){
    }
    
    public void startRecv(){
    	if (!this.running){
    		//this.setDaemon(true);
    		super.start();
    	}
    }
    
    public void stopRecv(){
    	this.running = false;
    }
    
	public void run() {
		this.running = true;
		try {
			 connectionFactory = new ActiveMQConnectionFactory(
			// ActiveMQConnection.DEFAULT_USER,
			// ActiveMQConnection.DEFAULT_PASSWORD,
					"system", "manager",
					// "tcp://localhost:61616"
					"tcp://192.168.108.13:61616");
			
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// Destination ：消息的目的地;消息发送给谁.
			Destination destination = session.createQueue("FirstQueue");
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(this.messageListener);
			while (this.running) {
				this.recvMessage(session, consumer);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != session) {
					session.close();
				}
				if (null != connection) {
					connection.close();
				}
			} catch (Throwable ignore) {
			}
		}
		
		this.running = false;
	}
	
    public  void recvMessage(Session session, MessageConsumer consumer) throws JMSException {
    	// 设置接收者接收消息超时时间
		TextMessage message = (TextMessage) consumer.receive(1000);
		if (null != message) {
			System.out.println(Thread.currentThread().getName() + "--收到消息" + message.getText());
		}
    }
    
    private  MessageListener messageListener = new MessageListener() {
		@Override
		public void onMessage(Message message) {
			// TODO Auto-generated method stub
			if (null != message) {
				System.out.println(Thread.currentThread().getName() + "--收到消息" + message);
			}
		}
	};
}



