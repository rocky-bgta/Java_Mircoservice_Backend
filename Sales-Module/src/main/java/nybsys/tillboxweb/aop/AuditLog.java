/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 05-Jan-18
 * Time: 3:54 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuditLog {
    private static final Logger log = LoggerFactory.getLogger(AuditLog.class);



    @Before("execution(* com.nybsys.tillboxweb.service.manager.*.get*(..))")
    public void getAllAdvice(){
        System.out.println("Service method getter called");
    }

    @After("allMethodsPointcut()")
    public void logBefore(JoinPoint joinPoint){
        System.out.println("The method after work done");
        System.out.println("Before running loggingAdvice on method="+joinPoint.toString());

        System.out.println("Agruments Passed=" + Arrays.toString(joinPoint.getArgs()));
    }


    //Pointcut to execute on all the methods of classes in a package
    @Pointcut("within(com.nybsys.tillboxweb.service.manager.*)")
    public void allMethodsPointcut(){}




    /*@Before("execution(* com.nybsys.tillboxweb.service.manager.UserServiceManager.get*())")
    public void getAllAdvice(){
        System.out.println("Service method getter called");
    }*/
}
