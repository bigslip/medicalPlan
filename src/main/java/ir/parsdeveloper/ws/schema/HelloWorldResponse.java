package ir.parsdeveloper.ws.schema;

import ir.parsdeveloper.ws.endpoint.HelloEndpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hadi tayebi
 */
@XmlRootElement(name = HelloEndpoint.HELLO_WS_RESPONSE, namespace = HelloEndpoint.HELLO_WS_NAMESPACE)
@XmlAccessorType(XmlAccessType.PROPERTY)
public class HelloWorldResponse {

    private String message;

    @XmlElement
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
