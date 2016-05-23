package ir.parsdeveloper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author hadi tayebi
 */
@Aspect
public class TestAspect {


    @Before("execution(public void ir.parsdeveloper.TestAround.testFunc(..))")// the pointcut expression
    public void testBefore() {
        System.out.println("testBefore");
    }

    @Pointcut("execution(public void ir.parsdeveloper.TestAround.testFunc(..))")// the pointcut expression
    public void pointcut() {
    }

    @Around("pointcut()")// the pointcut expression
    public Object anyOldTransfer(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start");
        joinPoint.proceed();
        System.out.println("end");
        return null;
    }


}
