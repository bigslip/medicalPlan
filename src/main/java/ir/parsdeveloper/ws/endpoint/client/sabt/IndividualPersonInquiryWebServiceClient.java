package ir.parsdeveloper.ws.endpoint.client.sabt;


import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.ws.schema.sabt.IndividualPersonInquiryResponse;

public interface IndividualPersonInquiryWebServiceClient {

    String WEB_SERVICE_USER_NAME_KEY = "${ws.cii.sabt.username}";
    String WEB_SERVICE_PASSWORD_KEY = "${ws.cii.sabt.password}";
    String WEB_SERVICE_URL = "${ws.cii.sabt.url}";

    String SERVICE_SUBMIT_INQUIRY = "http://tempuri.org/ISabtInq/SubmitInqDteSts";

    IndividualPersonInquiryResponse inquiryPerson(String nationalCode, Integer birthDate) throws ServiceException, IndividualPersonInquiryException;

    IndividualPersonInquiryResponse inquiryPersonByYear(String nationalCode, Integer birthYear) throws ServiceException, IndividualPersonInquiryException;
}