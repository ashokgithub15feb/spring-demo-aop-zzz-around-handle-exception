package com.luv2code.aopdemo;

import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.luv2code.aopdemo.service.TrafficFortuneService;

public class AroundWithLoggerDemoApp {

	private static Logger myLogger = Logger.getLogger(AroundWithLoggerDemoApp.class.getName());
	
	public static void main(String[] args) {
		
		//read the spring config java class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);
		
		//get the bean from spring contatiner
		TrafficFortuneService fortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);
		
		myLogger.info("\n Main Program is AroundWithLoggerDemoApp: ");
		
		myLogger.info("Calling getFortune method");
		
		
		String fortune = fortuneService.getFortune();
		
		myLogger.info("\n Main fortune is: "+fortune);
		
		myLogger.info("Finished!!!!");
		
		//close the context
		context.close();
	}

}
