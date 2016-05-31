package ir.parsdeveloper.ws.client;

import ir.parsdeveloper.ws.schema.HelloWorldRequest;
import ir.parsdeveloper.ws.schema.HelloWorldResponse;
import org.springframework.stereotype.Component;

/**
 * @author hadi tayebi
 */
//@Component
public class SampleWebServiceClient extends WebServiceClient {

    public HelloWorldResponse callWs() {
        HelloWorldRequest request = new HelloWorldRequest();
        request.setName(" hadi ");
        HelloWorldResponse response = (HelloWorldResponse) super.callWebService(request, "helloRequest");
        System.out.println(response.getMessage());
        return response;
    }

    @Override
    public String getWebServiceUrl() {
        return "http://localhost:9001/core/ws";
    }

    @Override
    public String getContextPath() {
        return "ir.parsdeveloper.ws.schema";
    }
}
