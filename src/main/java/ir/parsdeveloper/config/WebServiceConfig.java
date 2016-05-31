package ir.parsdeveloper.config;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.ws.endpoint.HelloEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadRootSmartSoapEndpointInterceptor;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

/**
 * @author hadi tayebi
 */
//@Configuration
public class WebServiceConfig extends WsConfigurationSupport {
    public final static int WEB_SERVICE_TIMEOUT = 60000;

  /*  @Bean
    public HttpComponentsMessageSender messageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setConnectionTimeout(WEB_SERVICE_TIMEOUT);
        return messageSender;
    }*/

    /*@PostConstruct
    void init() {
        registerEndpointInterceptor();
    }*//*

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("ir.parsdeveloper.ws.schema");
        return marshaller;
    }*/
    private XwsSecurityInterceptor securityInterceptor;

    @Bean
    public SaajSoapMessageFactory soapMessageFactory11() {
        SaajSoapMessageFactory soapMessageFactory = new SaajSoapMessageFactory();
        soapMessageFactory.setSoapVersion(SoapVersion.SOAP_11);
        return soapMessageFactory;
    }

    @Bean
    public SaajSoapMessageFactory soapMessageFactory12() {
        SaajSoapMessageFactory soapMessageFactory = new SaajSoapMessageFactory();
        soapMessageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return soapMessageFactory;
    }

    @Bean
    public DefaultWsdl11Definition helloServiceWsdl(XsdSchema helloSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setLocationUri(Constants.WEB_SERVICE_LOCATION_URL);
        wsdl11Definition.setTargetNamespace(HelloEndpoint.HELLO_WS_NAMESPACE);
        wsdl11Definition.setPortTypeName(HelloEndpoint.HELLO_WS_REQUEST);
        wsdl11Definition.setSchema(helloSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema helloSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/ir/parsdeveloper/ws/schema/helloService.xsd"));
    }

    protected void addInterceptors(List<EndpointInterceptor> interceptors) {
        PayloadRootSmartSoapEndpointInterceptor interceptor = new PayloadRootSmartSoapEndpointInterceptor(securityInterceptor, HelloEndpoint.HELLO_WS_NAMESPACE, HelloEndpoint.HELLO_WS_REQUEST);
        interceptors.add(interceptor);
    }

    @Autowired
    public void setSecurityInterceptor(XwsSecurityInterceptor securityInterceptor) {
        this.securityInterceptor = securityInterceptor;
    }
}
