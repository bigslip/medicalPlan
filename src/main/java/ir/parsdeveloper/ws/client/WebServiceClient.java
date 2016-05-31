package ir.parsdeveloper.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.annotation.PostConstruct;

/**
 * @author hadi tayebi
 */

//@Service
public abstract class WebServiceClient extends WebServiceGatewaySupport {

    public final static Integer SOAP_VERSION_11 = 1;
    public final static Integer SOAP_VERSION_12 = 2;

    public static final int DEFAULT_CONNECTION_TIMEOUT_MILLISECONDS = (60 * 1000);

    public static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

    protected WebServiceTemplate webServiceTemplate;
    protected Jaxb2Marshaller marshaller;


    protected SaajSoapMessageFactory soapMessageFactory11;
    protected SaajSoapMessageFactory soapMessageFactory12;

    @PostConstruct
    public void init() {
        marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(getContextPath());
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setConnectionTimeout(getConnectTimeOut());
        messageSender.setReadTimeout(getReadTimeout());
        super.setMessageSender(messageSender);

        super.setDefaultUri(getWebServiceUrl());

        super.setMarshaller(marshaller);
        super.setUnmarshaller(marshaller);

        if (getSoapVersion().equals(SOAP_VERSION_11)) {
            setMessageFactory(soapMessageFactory11);
        } else {
            setMessageFactory(soapMessageFactory12);
        }
        webServiceTemplate = getWebServiceTemplate();
    }

    protected Object callWebService(Object request, String soapAction) {
        return callWebService(request, new SoapActionCallback(soapAction));
    }

    protected Object callWebService(Object request, SoapActionCallback soapActionCallback) {
        return webServiceTemplate.marshalSendAndReceive(request, soapActionCallback);
    }

    public String createPasswordDigest(String nonce, String createdTime, String password) {

        return "";
    }


    @Autowired
    public void setSoapMessageFactory11(SaajSoapMessageFactory soapMessageFactory11) {
        this.soapMessageFactory11 = soapMessageFactory11;
    }

    @Autowired
    public void setSoapMessageFactory12(SaajSoapMessageFactory soapMessageFactory12) {
        this.soapMessageFactory12 = soapMessageFactory12;
    }

    public abstract String getWebServiceUrl();

    public abstract String getContextPath();

    protected int getConnectTimeOut() {
        return DEFAULT_CONNECTION_TIMEOUT_MILLISECONDS;
    }

    protected int getReadTimeout() {
        return DEFAULT_READ_TIMEOUT_MILLISECONDS;
    }

    public Integer getSoapVersion() {
        return SOAP_VERSION_11;
    }

}
