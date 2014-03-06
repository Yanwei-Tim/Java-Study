package com.min.activemq.mq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * 
* 消息接收者，内部使用线程创建Connection
* @author min.zhao
* @E-mail:zhuifeng1017@gmail.com
* @version 创建时间：2014-3-6 下午1:37:37
 */
public class Receiver extends Transceiver{
	private MessageConsumer consumer;
    private  MessageListener messageListener;
    private String subscriberName; // 订阅者名称
    
    public Receiver(String username, String password, String brokerURL, int mqType, String subscriberName){
    	super(username, password, brokerURL, mqType);
    	this.subscriberName = subscriberName;
    	messageListener  = new MessageListener() {
    		@Override
    		public void onMessage(Message message) {
    			if (null != message) {
    				if (message instanceof TextMessage)
						try {
							System.out.println(Thread.currentThread().getName() + "--收到消息--" + ((TextMessage)message).getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
    			}
    		}
    	};
    }
    
    /**
     * @param messageListener
     */
    public void setMessageListener(MessageListener messageListener){
    	this.messageListener = messageListener;
    }
    
    /**
     * 启动接收消息
     */
    public void startUp(){
    	if (!this.isStartUp){
    		//this.setDaemon(true);
    		Thread thread = new Thread(this);
    		thread.start();
    		this.isStartUp = true;
    	}
    }
    
    /**
     * 停止接收消息
     */
    public void shutDown(){
    	try {
    		if (null !=consumer){
    			consumer.setMessageListener(null);
    			consumer.close();
    			consumer = null;
    		}
			if (null != session) {
				session.close();
				session = null;
			}
			if (null != connection) {
				connection.close();
				connection = null;
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	this.isStartUp = false;
    }
    
	public void run() {
		try {
			// ConnectionFactory ：连接工厂，JMS 用它创建连接
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
			// ActiveMQConnection.DEFAULT_USER,
			// ActiveMQConnection.DEFAULT_PASSWORD,
					this.username, this.password,
					this.brokerURL
					// "tcp://localhost:61616"
					//"tcp://192.168.108.13:61616"
					);
			
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 设置客户端唯一ID，如果mq已存在将会报错
			connection.setClientID( this.subscriberName);
			// 启动
			connection.start();
			// 获取操作连
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// Destination ：消息的目的地;消息发送给谁.
			Destination destination;
            if (this.mqType  == Constants.MQ_TOPIC){
            	destination = session.createTopic(Constants.MQ_TOPIC_NAME);
            	consumer = session.createDurableSubscriber((Topic) destination, this.subscriberName);
            }else{
            	destination = session.createQueue(Constants.MQ_QUEUE_NAME);
            	consumer = session.createConsumer(destination);
            }
			consumer.setMessageListener(this.messageListener);
		} catch (Exception e) {
			e.printStackTrace();
			this.shutDown();// 出异常就停止接收消息并作清理
		}
		System.out.println("receiver connect thread exited");
	}
}



