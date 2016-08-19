package com.gpdi.searchengine.searchindexclient.server.search.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class GpdiSpringFactory implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		this.beanFactory = beanFactory;
	}

	/**
	 * 通过bean名称获取容器中bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static <T> T getBean(String beanName) {
		if (null != beanFactory) {
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}
}
