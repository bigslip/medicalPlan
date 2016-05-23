package ir.parsdeveloper.ws.endpoint.client.sabt;


import javax.xml.ws.WebServiceException;

public class IndividualPersonInquiryException extends WebServiceException {

    public IndividualPersonInquiryException() {
    }

    public IndividualPersonInquiryException(String message) {
        super(message);
    }

    public IndividualPersonInquiryException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndividualPersonInquiryException(Throwable cause) {
        super(cause);
    }
}
