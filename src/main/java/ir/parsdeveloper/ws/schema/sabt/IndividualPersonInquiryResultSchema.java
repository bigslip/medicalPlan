package ir.parsdeveloper.ws.schema.sabt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author h.tayebi
 */
@XmlType(propOrder = {"person", "errors"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IndividualPersonInquiryResultSchema {


    private IndividualPersonInquirySchema person;


    private String errors;

    @XmlElement(name = "CiiEstelamResult", namespace = "http://schemas.datacontract.org/2004/07/SabtLogic")
    public IndividualPersonInquirySchema getPerson() {
        return person;
    }

    public void setPerson(IndividualPersonInquirySchema person) {
        this.person = person;
    }

    @XmlElement(name = "ErrorNams")
    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
