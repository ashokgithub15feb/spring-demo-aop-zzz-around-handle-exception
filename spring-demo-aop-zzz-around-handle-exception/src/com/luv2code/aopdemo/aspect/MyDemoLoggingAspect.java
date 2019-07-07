package com.luv2code.aopdemo.aspect;

import java.awt.Desktop.Action;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
	
	private static Logger myLogger = Logger.getLogger(MyDemoLoggingAspect.class.getName());

	@Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
	public Object afterGetFortune(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Object result = null;
		long begin = System.currentTimeMillis();
		
		try
		{
			result = joinPoint.proceed();
		}
		catch (Exception e) {
			myLogger.warning("@Around advice we have a problem: "+e.getMessage());
			
			//result = "Mjor Poblem! but no warries your pri ate AOP helicopter is on the way";
			
			throw e;
		}
		
		long end = System.currentTimeMillis();
		
		long duration = end - begin;
		
		myLogger.info("\n==========>>>>> Duration: "+duration/1000.0 +" seconds");
		
		return result;
	}
	
	@Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
	public Object aroundGetFortune(ProceedingJoinPoint joinPoint) throws Throwable
	{
		//print out method we are advising on
		String method = joinPoint.getSignature().toShortString();
		
		myLogger.info("\n=======>>>>> Execution @Around on method: "+method);
		
		//get begin timestamp
		long begin = System.currentTimeMillis();
		
		//now 
		Object result = joinPoint.proceed();
		
		long end = System.currentTimeMillis();
		
		long duration = end - begin;
		
		myLogger.info("\n==========>>>>> Duration: "+duration/1000.0 +" seconds");
		return result;
	}

	@After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccount(..))")
	public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint)
	{
		String method = joinPoint.getSignature().toShortString();
		
		myLogger.info("\n=======>>>>> Execution @After finally on method: "+method);
		
	}
	
	@AfterThrowing(
				pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccount(..))",
				throwing = "theEx"
			)
	public void afterThrowingFindAccountsAdvice(JoinPoint joinPoint, Throwable theEx)
	{
		String method = joinPoint.getSignature().toShortString();
		
		myLogger.info("\n=======>>>>> Execution @AfterThrowing on method: "+method);
		
		//log the exception
		myLogger.info("\n=======>>>>> The Exception is: "+theEx);
		
	}
	
	//add a new advice for @AfterReturning on the findAccount method
	@AfterReturning(
				pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccount(..))",
				returning = "result"
			)
	public void afterReturningFindAccountAdvice(JoinPoint joinPoint, List<Account> result)
	{
		//print out which method we are advising on
		String method = joinPoint.getSignature().toShortString();
		
		myLogger.info("\n=======>>>>> Execution @AfterReturning on method: "+method);
		
		//print out the result of the method call
		myLogger.info("\n=======>>>>> Result is: "+result);
		
		//let's post-process the data ---- let;s modify the data
		
		
		//convert the account name to uppercase
		convertAccountNamesToUppercase(result);
	
		myLogger.info("\n=======>>>>> After convert Upper case Result is: "+result);
	}
	
	
	
	private void convertAccountNamesToUppercase(List<Account> result) {

		//loop through account
		
		List<String> collect = result.stream().map(theAccount -> theAccount.getName().toUpperCase()).collect(Collectors.toList());
		
		myLogger.info(collect+"");
		
		
		result.get(0).setName(collect.get(0));
		result.get(1).setName(collect.get(1));
		result.get(2).setName(collect.get(2));
		
//		
//		for(Account theAccount : result)
//		{
//			String upperCase = theAccount.getName().toUpperCase();
//			
//			theAccount.setName(upperCase);
//		}
		//get uppercase version of name
		
		//update the name on the account
	}



	@Before("com.luv2code.aopdemo.aspect.util.LuvAopExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint)
	{
		myLogger.info("\n=======>>>>> Execution Logging Aspect on method");
		
		//display the method signature
		MethodSignature methodSig = (MethodSignature)theJoinPoint.getSignature();
		myLogger.info("Method: "+methodSig);
		
		//display method argument
		
		Object[] args = theJoinPoint.getArgs();
		
		for(Object arg : args)
		{
			myLogger.info(arg+" ");
			
			if(arg instanceof Account)
			{
				//downcast and print Account specific stuff
				
				Account account = (Account) arg;
				
				myLogger.info("Account Name: "+account.getName());
				myLogger.info("Account Level: "+account.getLevel());
				
			}
			
		}
	}
	
	
}





















