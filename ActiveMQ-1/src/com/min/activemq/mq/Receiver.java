package com.min.activemq.mq;

import javax.jms.Destination;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;


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
    private boolean isDurableSubscriber; // 是否为持久订阅者
    private String subscriberName; // 订阅者名称
    
    /**
     * 
     * @param username 用户名
     * @param password 密码
     * @param brokerURL brokerURL
     * @param mqType 消息队列类型, 值为 {@link Constants.MQ_TOPIC}或{@link Constants.MQ_QUEUE}
     * @param isDurableSubscriber 是否为持久订阅者
     * @param subscriberName 订阅者名称，必须唯一
     */
    public Receiver(String username, String password, String brokerURL, int mqType, boolean isDurableSubscriber, String subscriberName){
    	super(username, password, brokerURL, mqType);
    	this.isDurableSubscriber = isDurableSubscriber;
    	this.subscriberName = subscriberName;
    	messageListener  = new MessageListener() {
    		@Override
    		public void onMessage(Message message) {
    			if (null != message) {
    				if (message instanceof TextMessage)
						try {
							System.out.println(Thread.currentThread().getName() + "--收到消息--" + ((TextMessage)message).getText());
							//Thread.sleep(1000);		// 阻塞1s试一试
						} catch (Exception e) {
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
    		this.isStartUp = true;
    		//this.setDaemon(true);
    		Thread thread = new Thread(this);
    		thread.start();
    	}
    }
    
    /**
     * 停止接收消息
     */
    public  void shutDown(){
    	synchronized (this) {
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
        	this.isConnected = false;
    	}
    }
    
	public void run() {
		try {
			super.createConnection();
			connection.setClientID( this.subscriberName);
			// 启动
			connection.start();
			// 获取操作连
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// Destination ：消息的目的地;消息发送给谁.
			Destination destination;
            if (this.mqType  == Constants.MQ_TOPIC){
            	destination = session.createTopic(Constants.MQ_TOPIC_NAME);
            	if (this.isDurableSubscriber){ //持久订阅者
            		consumer = session.createDurableSubscriber((Topic) destination, this.subscriberName);
            	}else{
            		consumer = session.createConsumer(destination);
            	}
            }else{
            	destination = session.createQueue(Constants.MQ_QUEUE_NAME);
            	consumer = session.createConsumer(destination);
            }
			consumer.setMessageListener(this.messageListener);
			this.isConnected = true;
		}catch (Exception e) {
			e.printStackTrace();
			this.shutDown();// 出异常就停止接收消息并作清理
			if (e instanceof InvalidClientIDException){
				System.out.println(this.subscriberName + " already connected");
			}
		}
		System.out.println("receiver connect thread exited");
	}
	
	/**
	 * 取消持久化订阅
	 * @return 
	 * @throws Exception
	 */
	public  boolean stopDurableSubscibe() throws Exception{
		synchronized (this) {
 	       if ( (this.subscriberName!=null) && this.isStartUp && this.isConnected){
 	    	  session.unsubscribe(this.subscriberName);
 	    	  System.out.println(this.subscriberName +" stop DurableSubscibe");
 	    	  return true;
 	       }else{
 	    	   return false;
 	       }
		}
	}
	
	public static void main(String[] args) {
		int type = Constants.MQ_TOPIC;
    	boolean isDurableSubscriber = false;
		if (args != null && args.length > 0) {
			for (String arg : args) {
				System.out.println("arg=" + arg);
			}
			if (args.length >= 2) {
				type = Integer.parseInt(args[0]);
				isDurableSubscriber = Integer.parseInt(args[1]) == 0?false:true;
			}
		}else{
			System.out.println("第1个参数 0:topic  1:queue");
			System.out.println("第2个参数 0:非持久化订阅  1:持久化订阅");
			return;
		}
		
		String ttQueue =  (type==Constants.MQ_TOPIC)?"topic":"queue";
		String ttPersistent = (isDurableSubscriber) ? "持久化订阅":"非持久化订阅";
		System.out.println("Receiver：" + ttQueue + "--" + ttPersistent);
		
		int size = 1;
		Receiver receivers[] = new Receiver[size];
		for (int i = 0; i < size; i++) {
			receivers[i] = new Receiver("system", "manager", 
					"tcp://192.168.108.13:61616",
					type,
					isDurableSubscriber,
					"subscriber1");
			receivers[i].startUp();
		}
		
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		for (int i = 0; i < size; i++) {
//			try {
//				receivers[i].stopDurableSubscibe();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		for (int i = 0; i < 90; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		for (int i = 0; i < size; i++) {
			receivers[i].shutDown();
		}
		
		System.out.println("Receiver main thread exited");
	}
	
}



