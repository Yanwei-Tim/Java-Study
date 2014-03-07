package com.min.activemq.mq;
/**
 * 静态变量
 * @author min.zhao
 * @E-mail:zhuifeng1017@gmail.com
 * @version 创建时间：2014-3-6 下午2:18:58
 */
public interface Constants {
	/**队列消息，值为{@value}*/ 
	public  int MQ_TOPIC = 0; 
	/**订阅消息，值为{@value}*/
	public  int MQ_QUEUE = 1; 
	public  String MQ_TOPIC_NAME  = "FirstTopic";
	public  String MQ_QUEUE_NAME  = "FirstQueue";
}
