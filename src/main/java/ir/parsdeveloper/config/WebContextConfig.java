package ir.parsdeveloper.config;

import ir.parsdeveloper.commons.convert.IntegerToString;
import ir.parsdeveloper.commons.convert.LongToString;
import ir.parsdeveloper.commons.convert.MultiPartFileToObject;
import ir.parsdeveloper.commons.convert.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ExpressionParser;
import org.springframework.binding.expression.beanwrapper.BeanWrapperExpressionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.execution.ViewFactory;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.view.FlowAjaxTiles3View;
import org.springframework.webflow.validation.ValidationHintResolver;

import java.util.Arrays;
import java.util.Locale;


/**
 * @author hadi tayebi
 */
@Configuration
@EnableWebMvc
public class WebContextConfig extends WebMvcConfigurerAdapter {

    private static final String TILES_DEFINITION_PATH = "/tiles/**/*-tiles.xml";
    @Autowired
    protected ApplicationContext applicationContext;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/help").setViewName("help");
        registry.addViewController("/accessDeniedPage").setViewName("accessDeniedPage");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("/", "classpath:/META-INF/web-resources/");
    }

    /**
     * create new instance for nested path property
     */

    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator(BeanWrapperExpressionParser beanWrapperExpressionParser) {
        MvcViewFactoryCreator mvcViewFactoryCreator = new MvcViewFactoryCreator() {
            {
                setUseSpringBeanBinding(false);//prevent create default bean
            }

            @Override
            public ViewFactory createViewFactory(Expression viewId
                    , ExpressionParser expressionParser
                    , ConversionService conversionService
                    , BinderConfiguration binderConfiguration
                    , Validator validator
                    , ValidationHintResolver validationHintResolver) {
                //customize for create new instance for nested path property
                return super.createViewFactory(viewId, beanWrapperExpressionParser, conversionService, binderConfiguration, validator, validationHintResolver);
            }
        };
        mvcViewFactoryCreator.setViewResolvers(Arrays.asList(tilesViewResolver(), beanViewResolver()));
        mvcViewFactoryCreator.setApplicationContext(applicationContext);
        return mvcViewFactoryCreator;
    }



    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver viewResolver = new TilesViewResolver();
        viewResolver.setViewClass(FlowAjaxTiles3View.class);
        viewResolver.setApplicationContext(applicationContext);
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean
    public BeanNameViewResolver beanViewResolver() {
        BeanNameViewResolver viewResolver = new BeanNameViewResolver();
        viewResolver.setOrder(2);
        viewResolver.setApplicationContext(applicationContext);
        return viewResolver;
    }

    @Bean
    public TilesConfigurer configureTilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions(TILES_DEFINITION_PATH);
        return configurer;
    }

    @Bean
    public ThemeSource themeSource() {
        ResourceBundleThemeSource source = new ResourceBundleThemeSource();
        source.setBasenamePrefix("ir.parsdeveloper.themes.theme-");
        return source;
    }

    @Bean
    public ThemeResolver themeResolver() {
        CookieThemeResolver resolver = new CookieThemeResolver();
        resolver.setCookieMaxAge(-1);//unlimited
        resolver.setCookieName("theme");
        resolver.setDefaultThemeName("default");
        return resolver;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieMaxAge(-1);//unlimited
        resolver.setDefaultLocale(new Locale("fa","IR"));
        resolver.setCookieName("language");
        return resolver;
    }


    //Register interceptors
    public void addInterceptors(InterceptorRegistry registry) {
        //Theme specific
        ThemeChangeInterceptor themeInterceptor = new ThemeChangeInterceptor();
        themeInterceptor.setParamName("theme");
        registry.addInterceptor(themeInterceptor);

        //Internationalization and Localization specific
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        registry.addInterceptor(interceptor);

    }
}