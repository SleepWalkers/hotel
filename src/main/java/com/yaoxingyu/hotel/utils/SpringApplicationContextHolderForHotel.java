package com.yaoxingyu.hotel.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContextHolderForHotel implements ApplicationContextAware {  
	
	  private static ApplicationContext applicationContext;     
	  
	  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
		  SpringApplicationContextHolderForHotel.applicationContext = applicationContext;  
	  }  
	  
	  public static ApplicationContext getApplicationContext() {
		  return applicationContext;  
	  }  
	  
	  public static Object getBean(String name) throws BeansException {
		  return applicationContext.getBean(name);  
	  }  
	  
	  public static Object getBean(String name, Class requiredType) throws BeansException {
		  return applicationContext.getBean(name, requiredType);  
	  }  
	  
	  public static boolean containsBean(String name) {
		  return applicationContext.containsBean(name);  
	  }
}