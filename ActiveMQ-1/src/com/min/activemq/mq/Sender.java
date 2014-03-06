package com.min.activemq.mq;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
* 消息发送，内部使用线程创建Connection
* @author min.zhao
* @E-mail:zhuifeng1017@gmail.com
* @version 创建时间：2014-3-6 下午6:11:35
 */
public class Sender extends Transceiver{
    // MessageProducer：消息发送者
    private MessageProducer producer;
    
    public Sender(String username, String password, String brokerURL, int mqType){
    	super(username, password, brokerURL, mqType);
    }
    
    public void run(){
    	// ConnectionFactory ：连接工厂，JMS 用它创建连接
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                //ActiveMQConnection.DEFAULT_USER,
               // ActiveMQConnection.DEFAULT_PASSWORD,
    			this.username, this.password,
				this.brokerURL
        		//"tcp://localhost:61616"
        		//"tcp://192.168.108.13:61616"
        		);
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            Destination destination;
            if (this.mqType == Constants.MQ_TOPIC){
            	destination = session.createTopic(Constants.MQ_TOPIC_NAME);
            }else{
            	destination = session.createQueue(Constants.MQ_QUEUE_NAME);
            }
            // 得到消息生成者【发送者】
            producer = session.createProducer(destination);
            // 设置持久化 实际根据项目决定
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (Exception e) {
        	e.printStackTrace();
			this.shutDown();// 出异常就停止接收消息并作清理
        }
        System.out.println("sender connect thread exited");
    }
    
    public void startUp() {
    	if (!this.isStartUp){
    		//this.setDaemon(true);
    		Thread thread = new Thread(this);
    		thread.start();
    		this.isStartUp = true;
    	}
    }
    
    public void shutDown() {
    	try {
    		if (null !=producer){
    			producer.close();
    			producer = null;
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
    
    public void sendMessage(String text) throws Exception{
       if (this.isStartUp && session != null){
    	   TextMessage message = session.createTextMessage(text);
           producer.send(message);
           System.out.println("发送消息 : " + text);
           session.commit();
       }
    }
    
    public static void main(String[] args) {
    	int type = Constants.MQ_TOPIC;
		if (args != null && args.length > 0) {
			for (String arg : args) {
				System.out.println("arg=" + arg);
			}
			if (args.length >= 1) {
				type = Integer.parseInt(args[0]);
			}
		}else{
			System.out.println("参数0:topic  1:queue");
			return;
		}
		String tt =  (type==Constants.MQ_TOPIC)?"topic msg":"queue msg";
		System.out.println("发送" + tt);
		
		Sender sender = new Sender("system", "manager", 
				"tcp://192.168.108.13:61616",type);
		sender.startUp();
		
		// 测试消息发送
		for (int i = 0; i < 100; i++) {
			try {
				sender.sendMessage(String.valueOf(i));
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				sender.shutDown();
			}
		}
		
		sender.shutDown();
		
		System.out.println("Sender main thread exited");
    }
}