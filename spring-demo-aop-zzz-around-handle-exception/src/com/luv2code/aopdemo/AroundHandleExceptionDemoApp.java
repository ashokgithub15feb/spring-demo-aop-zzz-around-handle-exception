package com.luv2code.aopdemo;

import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.luv2code.aopdemo.service.TrafficFortuneService;

public class AroundHandleExceptionDemoApp {

	private static Logger myLogger = Logger.getLogger(AroundHandleExceptionDemoApp.class.getName());
	
	public static void main(String[] args) {
		
		//read the spring config java class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);
		
		//get the bean from spring contatiner
		TrafficFortuneService fortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);
		
		myLogger.info("\n Main Program is AroundHandleExceptionDemoApp: ");
		
		myLogger.info("Calling getFortune method");
		
		boolean tripwise = true;
		
		
		String fortune = fortuneService.getFortune(tripwise);
		
		myLogger.info("\n Main fortune is: "+fortune);
		
		myLogger.info("Finished!!!!");
		
		//close the context
		context.close();
	}

}
