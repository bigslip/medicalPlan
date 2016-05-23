package ir.parsdeveloper.config;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;


/**
 * @author hadi tayebi
 */

@ComponentScan(basePackages = {"ir.parsdeveloper"}, scopedProxy = ScopedProxyMode.TARGET_CLASS)
@EnableScheduling()
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
/*@Import(value = {PersistenceContextConfig.class,  WebApplicationContextConfig.class,WebFlowConfig.class,
                 WebSecurityConfig.class, WebServiceConfig.class})*/
public class ApplicationContextConfig {

    public static final String DEFAULT_MESSAGE_SOURCE = "ir.parsdeveloper.messages.messages";


    @Value("${core.fileupload.maxsize:524288}")
    private Integer maxUploadSize;

    @Bean
    public MessageSource messageSource() throws IOException {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(DEFAULT_MESSAGE_SOURCE);
        messageSource.setDefaultEncoding(Constants.UTF8_ENCODING);
        messageSource.setUseCodeAsDefaultMessage(true);
        MessageBundleUtil.setMessageSource(messageSource);
        return messageSource;
    }

   /* @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(maxUploadSize);
        return multipartResolver;
    }*/

    @Bean
    public PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath:config/*.properties"));
        return ppc;
    }

    @Bean
    public PropertiesFactoryBean propertiesFactoryBean() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath:config/*.properties"));
        return propertiesFactoryBean;
    }

    /*
        @Bean(name = "validator")
        public LocalValidatorFactoryBean validator(MessageSource messageSource) {
            LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
            validatorFactoryBean.setValidationMessageSource(messageSource);
            return validatorFactoryBean;
        }
    */

    @Bean
    public VelocityEngine velocityEngine() throws IOException {
        VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();
        Properties props = new Properties();
        props.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
        props.put("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        velocityEngineFactoryBean.setVelocityProperties(props);
        return velocityEngineFactoryBean.createVelocityEngine();
    }


}
