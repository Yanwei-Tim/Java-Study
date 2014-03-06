package com.min.activemq.test;

import java.io.Console;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.min.activemq.mq.Constants;

public class Sender {
    private static final int SEND_NUMBER = 100;
    
    // Connection ：JMS 客户端到JMS Provider 的连接
    static Connection connection;
    // Session： 一个发送或接收消息的线程
    static Session session;
    // Destination ：消息的目的地;消息发送给谁.
    static Destination destination;
    // MessageProducer：消息发送者
    static MessageProducer producer;
    
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
		
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                //ActiveMQConnection.DEFAULT_USER,
               // ActiveMQConnection.DEFAULT_PASSWORD,
        		"system","manager",
                //"tcp://localhost:61616"
        		"tcp://192.168.108.13:61616"
        		);
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue((type == Constants.MQ_TOPIC) ? 
					Constants.MQ_TOPIC_NAME
					: Constants.MQ_QUEUE_NAME);
            // 得到消息生成者【发送者】
            producer = session.createProducer(destination);
            // 设置不持久化，此处学习，实际根据项目决定
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            // 构造消息，此处写死，项目就是参数，或者方法获取
            sendMessage(session, producer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	if (null !=producer){
            		producer.close();
            		producer = null;
            	}
            	if (null != session){
            		session.close();
            		session = null;
            	}
                if (null != connection){
                	connection.close();
                	connection = null;
                }     
            } catch (Throwable ignore) {
            }
        }
    }

    public static void sendMessage(Session session, MessageProducer producer) throws Exception{
        for (int i = 1; i <= SEND_NUMBER; i++) {
            TextMessage message = session
                    .createTextMessage("ActiveMq 发送的消息" + i);
            // 发送消息到目的地方
            System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);
            producer.send(message);
            session.commit();
            Thread.sleep(100);
        }
    }
}