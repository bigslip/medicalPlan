package ir.parsdeveloper.commons.listener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

/**
 * @author hadi tayebi
 */
public class CustomSpringBeanAutowiringInterceptor extends SpringBeanAutowiringInterceptor {
    static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        CustomSpringBeanAutowiringInterceptor.applicationContext = applicationContext;
    }


    protected BeanFactory getBeanFactory(Object target) {
        return applicationContext.getAutowireCapableBeanFactory();
    }
}
