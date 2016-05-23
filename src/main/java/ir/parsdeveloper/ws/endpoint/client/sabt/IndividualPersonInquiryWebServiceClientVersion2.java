package ir.parsdeveloper.ws.endpoint.client.sabt;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.ws.client.WebServiceClient;
import ir.parsdeveloper.ws.schema.sabt.IndividualPersonInquiryRequest;
import ir.parsdeveloper.ws.schema.sabt.IndividualPersonInquiryResponse;
import ir.parsdeveloper.ws.schema.sabt.IndividualPersonInquiryResultSchema;
import ir.parsdeveloper.ws.schema.sabt.IndividualPersonInquirySchema;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.ws.WebServiceException;

//@Service(value = "IndividualPersonInquiryWebServiceV2")
public class IndividualPersonInquiryWebServiceClientVersion2 extends WebServiceClient implements IndividualPersonInquiryWebServiceClient {

    @Value(WEB_SERVICE_URL)
    private String url;

    @Value(WEB_SERVICE_USER_NAME_KEY)
    private String userName;

    @Value(WEB_SERVICE_PASSWORD_KEY)
    private String password;


    public IndividualPersonInquiryResponse inquiryPerson(String nationalCode, Integer birthDate) throws ServiceException, IndividualPersonInquiryException {
        IndividualPersonInquiryRequest request = new IndividualPersonInquiryRequest();
        IndividualPersonInquirySchema personInquirySchema = new IndividualPersonInquirySchema();
        personInquirySchema.setNationalCode(nationalCode);
        personInquirySchema.setBirthDate(birthDate);
        personInquirySchema.setDateHasPostfix(0);

        request.setPerson(personInquirySchema);
        return inquiryPerson(request);
    }

    public IndividualPersonInquiryResponse inquiryPersonByYear(String nationalCode, Integer birthYear) throws ServiceException, IndividualPersonInquiryException {
        IndividualPersonInquiryRequest request = new IndividualPersonInquiryRequest();
        IndividualPersonInquirySchema personInquirySchema = new IndividualPersonInquirySchema();
        personInquirySchema.setNationalCode(nationalCode);
        personInquirySchema.setBirthDate(birthYear);
        personInquirySchema.setDateHasPostfix(1);

        request.setPerson(personInquirySchema);
        return inquiryPerson(request);
    }

    private IndividualPersonInquiryResponse inquiryPerson(IndividualPersonInquiryRequest request) throws ServiceException, IndividualPersonInquiryException {
        IndividualPersonInquiryResponse response = null;
        try {
            request.setUsername(userName);
            request.setPassword(password);

            response = (IndividualPersonInquiryResponse) callWebService(request, SERVICE_SUBMIT_INQUIRY);

        } catch (Exception e) {
            logger.error(this, e);
            throw new WebServiceException(e);
        }
        IndividualPersonInquiryResultSchema inquiryResult = response.getInquiryResult();
        //Check for Null Result
        if (inquiryResult == null || inquiryResult.getPerson() == null) {
            return null;
        }
        //Check For Error Result
        String errors = inquiryResult.getErrors();
        if (errors != null && !errors.trim().equals("")) {
            throw new WebServiceException(errors);
        }

        //Check For Person Result
        IndividualPersonInquirySchema personInquiry = inquiryResult.getPerson();

        if (personInquiry.getNationalCode() == null ||
                personInquiry.getNationalCode().equals("0")) {
            if (personInquiry.getMessage() != null &&
                    personInquiry.getMessage().getString() != null &&
                    !personInquiry.getMessage().getString().isEmpty()) {
                throw new IndividualPersonInquiryException(personInquiry.getMessage().getString().get(0));
            }
        } else {
            personInquiry.setNationalCode(request.getPerson().getNationalCode());
        }
        return response;
    }

    @Override
    public String getWebServiceUrl() {
        return url;
    }

    @Override
    public String getContextPath() {
        return null;
    }
}
