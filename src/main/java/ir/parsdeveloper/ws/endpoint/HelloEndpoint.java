package ir.parsdeveloper.ws.endpoint;

import ir.parsdeveloper.ws.schema.HelloWorldRequest;
import ir.parsdeveloper.ws.schema.HelloWorldResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author hadi tayebi
 */

@Endpoint
public class HelloEndpoint {

    public final static String HELLO_WS_NAMESPACE = "http://www.parsdeveloper.com/ws/helloService";
    public final static String HELLO_WS_REQUEST = "helloRequest";
    public final static String HELLO_WS_RESPONSE = "helloResponse";

    @Secured("ROLE_ANONYMOUS")
    @PayloadRoot(localPart = HELLO_WS_REQUEST, namespace = HELLO_WS_NAMESPACE)
    @ResponsePayload()
    public HelloWorldResponse hello(@RequestPayload HelloWorldRequest request) {
        HelloWorldResponse response = new HelloWorldResponse();
        response.setMessage("hello " + request.getName());
        return response;
    }
}
