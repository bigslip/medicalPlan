package test;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author hadi tayebi
 */

@ComponentScan(basePackages = "test", scopedProxy = ScopedProxyMode.TARGET_CLASS)
@EnableScheduling()
public class TestApplicationContext {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadGroupName("hooooooooooooot");
        taskScheduler.setPoolSize(20);
        return taskScheduler;
    }
    public static final String DEFAULT_MESSAGE_SOURCE = "ir.parsdeveloper.messages.messages";
    public static void main(String arg[]){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(DEFAULT_MESSAGE_SOURCE);
        messageSource.setDefaultEncoding(Constants.UTF8_ENCODING);
        messageSource.setUseCodeAsDefaultMessage(true);
        MessageBundleUtil.setMessageSource(messageSource);
        System.out.println( MessageBundleUtil.getMessage("lbl.core.btn.add",new Locale("fa","IR")));
        ResourceBundle bundleFa= ResourceBundle.getBundle("ir.parsdeveloper.messages.messages", new Locale("fa", "IR"));
        System.out.println(bundleFa.getString("lbl.core.btn.add"));

    }

}
