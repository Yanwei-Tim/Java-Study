package com.min.activemq.mq;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 
* 消息发送，内部使用线程创建Connection
* @author min.zhao
* @E-mail:zhuifeng1017@gmail.com
* @version 创建时间：2014-3-6 下午6:11:35
 */
public class Sender extends Transceiver{
    // 消息发送
    private MessageProducer producer;
    // 消息持久化模式
    private int deliveryMode;
    
    /**
     * 
     * @param username 用户名
     * @param password  密码
     * @param brokerURL brokerURL
     * @param mqType 消息队列类型, 值为 {@link Constants.MQ_TOPIC}或{@link Constants.MQ_QUEUE}
     * @param deliveryMode 持久化模式,值为 {@link DeliveryMode.NON_PERSISTENT} 或{@link DeliveryMode.PERSISTENT}
     */
    public Sender(String username, String password, String brokerURL, int mqType, int deliveryMode){
    	super(username, password, brokerURL, mqType);
    	this.deliveryMode = deliveryMode;
    }
    
    public void run(){
        try {
        	super.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination;
            if (this.mqType == Constants.MQ_TOPIC){
            	destination = session.createTopic(Constants.MQ_TOPIC_NAME);
            }else{
            	destination = session.createQueue(Constants.MQ_QUEUE_NAME);
            }
            // 得到消息生成者【发送者】
            producer = session.createProducer(destination);
            // 设置持久化 实际根据项目决定
            producer.setDeliveryMode(this.deliveryMode);
            this.isConnected = true;
        } catch (Exception e) {
        	e.printStackTrace();
			this.shutDown();// 出异常就停止接收消息并作清理
        }
        System.out.println("sender connect thread exited");
    }
    
    public void startUp() {
    	if (!this.isStartUp){
    		this.isStartUp = true;
    		//this.setDaemon(true);
    		Thread thread = new Thread(this);
    		thread.start();
    	}
    }
    
    public void shutDown() {
    	synchronized (this) {
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
        	this.isConnected = false;
		}
    }
    
    /**
     * 
     * @param obj, 值为String, Serializable object, Map(其key为String类型，value只能为原始数据类型(Integer, Double, Long ...), String objects, and byte arrays)
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public boolean sendMessage(Object obj) throws Exception{
		synchronized (this) {
			if (this.isStartUp && this.isConnected) {
				if (obj instanceof String) {
					TextMessage textMsg = session
							.createTextMessage((String) obj);
					producer.send(textMsg);
					System.out.println("发送消息 : " + obj);
				} else if (obj instanceof Map) {
					Map map = (Map) obj;
					MapMessage mapMsg = session.createMapMessage();
					Iterator iter = map.keySet().iterator();
					while (iter.hasNext()) {
						String key = (String) iter.next();
						Object value = map.get(key);
						mapMsg.setObject(key, value);
					}
					producer.send(mapMsg);
				} else {
					ObjectMessage objMsg = session.createObjectMessage();
					objMsg.setObject((Serializable) obj);
					producer.send(objMsg);
				}
				session.commit();
				return true;
			} else {
				return false;
			}
		}
    }

    public static void main(String[] args) {
    	int type = Constants.MQ_TOPIC;
    	int deliveryMode = DeliveryMode.NON_PERSISTENT;
		if (args != null && args.length > 0) {
			for (String arg : args) {
				System.out.println("arg=" + arg);
			}
			if (args.length >= 2) {
				type = Integer.parseInt(args[0]);
				deliveryMode = Integer.parseInt(args[1]) == 0 ? DeliveryMode.NON_PERSISTENT
						: DeliveryMode.PERSISTENT;
			}
		}else{
			System.out.println("第1个参数 0:topic  1:queue");
			System.out.println("第2个参数 0:不持久化  1:持久化");
			return;
		}
		
		String ttQueue =  (type==Constants.MQ_TOPIC)?"topic":"queue";
		String ttPersistent = (deliveryMode == DeliveryMode.NON_PERSISTENT) ? "不持久化":"持久化";
		System.out.println("Sender:  " + ttQueue + "--" + ttPersistent);
		
		Sender sender = new Sender("system", "manager", 
				"tcp://192.168.108.13:61616",type, deliveryMode);
		sender.startUp();
		
		// 等待连接OK
		while (!sender.isConnected()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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