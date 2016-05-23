package ir.parsdeveloper.ws.schema.sabt;

import javax.xml.bind.annotation.*;

@XmlType(propOrder = {"inquiryResult"})
@XmlRootElement(name = "SubmitInqDteStsResponse")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IndividualPersonInquiryResponse {

    IndividualPersonInquiryResultSchema inquiryResult;

    @XmlElement(name = "SubmitInqDteStsResult")
    public IndividualPersonInquiryResultSchema getInquiryResult() {
        return inquiryResult;
    }

    public void setInquiryResult(IndividualPersonInquiryResultSchema inquiryResult) {
        this.inquiryResult = inquiryResult;
    }
}

