package ir.parsdeveloper.config;

import ir.parsdeveloper.commons.Constants;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author hadi tayebi
 */
public class WebInitializer implements WebApplicationInitializer {


    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext applicationContext = getApplicationContext();

        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/");
        characterEncodingFilter.setInitParameter("encoding", Constants.UTF8_ENCODING);
        characterEncodingFilter.setInitParameter("forceEncoding", "true");

        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"));
        //securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/");
        securityFilter.addMappingForServletNames(EnumSet.allOf(DispatcherType.class), true, "spring");


        servletContext.addListener(new ContextLoaderListener(applicationContext));
        servletContext.addListener(new HttpSessionEventPublisher());
        servletContext.setInitParameter("log4jConfigLocation", "classpath:log4j.properties");
        servletContext.addListener(RequestContextListener.class);
        servletContext.setInitParameter("defaultHtmlEscape", "true");


        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setApplicationContext(applicationContext);
        dispatcherServlet.setPublishEvents(true);
        ServletRegistration.Dynamic springServlet = servletContext.addServlet("spring", dispatcherServlet);
        springServlet.setLoadOnStartup(1);
        springServlet.setAsyncSupported(true);

        //springServlet.setInitParameter("spring.profiles.active", Profiles.PRODUCTION.name());
        springServlet.addMapping("/");
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(applicationContext);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        servletContext.addServlet("ws", messageDispatcherServlet).addMapping("/ws/*", "/ws/unsecured/*");
    }

    protected WebApplicationContext getApplicationContext() throws ServletException {
        try {
            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
            //Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:ir/parsdeveloper/config/*Config.class");
            //Resource resource = resources[0];
            context.setConfigLocation("ir.parsdeveloper.config");
            context.register(ApplicationContextConfig.class);
            return context;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}