package com.ssi.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @package   com.unistrong.appstore.common
 * @filename ApplicationContext.java
 * @author   zorro_li
 * @create_date Nov 21, 2013
 * @version V 1.0
 * @description 功能
 */

public class ApplicationContext {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private org.springframework.context.ApplicationContext appContext;

	/**
	 * ApplicationContext构造函数
	 */
	public ApplicationContext() {
		initialize();
	}

	/** 初始化服务器上下文 */
	public void initialize() {
		this.appContext = new ClassPathXmlApplicationContext(new String[] { "jtom_applicationContext.xml" });
	}

	@SuppressWarnings("unchecked")
	public <T> T loadBean(String name) {
		return (T) getAppContext().getBean(name);
	}

	public <T> T loadBean(Class<T> beanClass) {
		String[] names = getAppContext().getBeanNamesForType(beanClass);
		if ((names != null) && (names.length > 0)) {
			if (names.length == 1) {
				return loadBean(names[0]);
			}
		} else {
			this.logger.error("[{}]没有找到.",beanClass);
		}
		return null;
	}

	/***
	 * 获得上下文信息
	 * @return
	 */
	private org.springframework.context.ApplicationContext getAppContext() {
		if (this.appContext != null) {
			return this.appContext;
		}
		throw new RuntimeException("初始化失败.");
	}
}
