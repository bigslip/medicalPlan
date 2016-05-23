package ir.parsdeveloper.config;

import ir.parsdeveloper.commons.convert.IntegerToString;
import ir.parsdeveloper.commons.convert.LongToString;
import ir.parsdeveloper.commons.convert.MultiPartFileToObject;
import ir.parsdeveloper.commons.convert.StringToDateConverter;
import ir.parsdeveloper.commons.helper.DefaultFlowExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.binding.expression.beanwrapper.BeanWrapperExpressionParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Value("${core.productionMode:true}")
    private Boolean productionMode;


    @Bean
    public FlowHandlerAdapter flowHandlerAdapter(FlowExecutor flowExecutor, DefaultFlowExceptionHandler flowExceptionHandler) {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter() {
            protected void defaultHandleException(String flowId, FlowException flowException, HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException {
                if (flowException instanceof NoSuchFlowExecutionException && flowId != null) {
                    super.defaultHandleException(flowId, flowException, request, response);
                } else {
                    flowExceptionHandler.handleFlowException(flowId, flowException, request, response);
                }
            }
        };
        handlerAdapter.setFlowExecutor(flowExecutor);
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping(FlowDefinitionRegistry flowRegistry) {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(2);
        handlerMapping.setFlowRegistry(flowRegistry);
        return handlerMapping;
    }

    @Bean
    public FlowExecutor flowExecutor(FlowDefinitionRegistry flowRegistry) {
        return getFlowExecutorBuilder(flowRegistry)
                .setMaxFlowExecutionSnapshots(0)//for use SimpleFlowExecutionSnapshotFactory bean
                .addFlowExecutionListener(new SecurityFlowExecutionListener())
                .build();
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry(FlowBuilderServices flowBuilderService) {
        return getFlowDefinitionRegistryBuilder(flowBuilderService)
                .addFlowLocationPattern("/WEB-INF/flows/**/*-flow.xml")
                .build();
    }


    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder()
                .setViewFactoryCreator(mvcViewFactoryCreator)//.setValidator(validator)
                .setConversionService(conversionService())
                .setDevelopmentMode(productionMode).build();
    }

    @Bean
    public DefaultConversionService conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToDateConverter());
        conversionService.addConverter(new MultiPartFileToObject());
        conversionService.addConverter(new IntegerToString());
        conversionService.addConverter(new LongToString());
        return conversionService;
    }

    @Bean
    public BeanWrapperExpressionParser beanWrapperExpressionParser() {
        BeanWrapperExpressionParser beanWrapperExpressionParser = new BeanWrapperExpressionParser(conversionService());
        beanWrapperExpressionParser.setAutoGrowNestedPaths(true);
        beanWrapperExpressionParser.setAutoGrowCollectionLimit(5);
        return beanWrapperExpressionParser;
    }


    @Bean
    public DefaultFlowExceptionHandler flowExceptionHandler() {
        return new DefaultFlowExceptionHandler();
    }

    MvcViewFactoryCreator mvcViewFactoryCreator;
    //Validator validator;

    @Autowired
    public void setMvcViewFactoryCreator(MvcViewFactoryCreator mvcViewFactoryCreator) {
        this.mvcViewFactoryCreator = mvcViewFactoryCreator;
    }


}
