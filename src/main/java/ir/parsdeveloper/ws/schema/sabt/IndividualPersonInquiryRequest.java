package ir.parsdeveloper.ws.schema.sabt;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "SubmitInqDteSts")
@XmlType(propOrder = {"person", "username", "password"})
public class IndividualPersonInquiryRequest {


    private IndividualPersonInquirySchema person;


    private String username;


    private String password;

    @XmlElement(name = "req")
    public IndividualPersonInquirySchema getPerson() {
        return person;
    }

    public void setPerson(IndividualPersonInquirySchema person) {
        this.person = person;
    }

    @XmlElement(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
