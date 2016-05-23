package ir.parsdeveloper.ws.schema;

import ir.parsdeveloper.ws.endpoint.HelloEndpoint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hadi tayebi
 */
@XmlRootElement(name = "helloRequest", namespace = HelloEndpoint.HELLO_WS_NAMESPACE)
public class HelloWorldRequest {

    private String name;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
