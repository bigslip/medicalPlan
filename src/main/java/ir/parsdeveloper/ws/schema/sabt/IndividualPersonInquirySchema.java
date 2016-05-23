package ir.parsdeveloper.ws.schema.sabt;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder = {"nationalCode",
        "firstName",
        "lastName",
        "fatherName",
        "shenasnameseri",
        "shenasnameserial",
        "shenasnameNo",
        "birthDate",
        "dateHasPostfix",
        "gender",
        "officeCode",
        "bookNo",
        "bookRow",
        "nameHasPrefix",
        "nameHasPostFix",
        "familyHasPrefix",
        "familyHasPostFix",
        "deathStatus",
        "message",
        "exceptionMessage"
})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IndividualPersonInquirySchema {


    private String nationalCode;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String shenasnameseri;
    private Integer shenasnameserial = 0;
    private Integer shenasnameNo = 0;
    private Integer birthDate;
    private Integer gender = 0;
    private Integer officeCode = 0;
    private Integer bookNo = 0;
    private Integer bookRow = 0;
    private Integer nameHasPrefix = 0;
    private Integer nameHasPostFix = 0;
    private Integer familyHasPrefix = 0;
    private Integer familyHasPostFix = 0;
    private Integer dateHasPostfix = 1;
    private Integer deathStatus = 0;
    private IndividualPersonMessage message;
    private String exceptionMessage;

    @XmlElement(name = "Nin")
    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    @XmlElement(name = "Name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "Family")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement(name = "FatherName")
    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    @XmlElement(name = "Shenasnameseri")
    public String getShenasnameseri() {
        return shenasnameseri;
    }

    public void setShenasnameseri(String shenasnameseri) {
        this.shenasnameseri = shenasnameseri;
    }

    @XmlElement(name = "Shenasnameserial")
    public Integer getShenasnameserial() {
        return shenasnameserial;
    }

    public void setShenasnameserial(Integer shenasnameserial) {
        this.shenasnameserial = shenasnameserial;
    }

    @XmlElement(name = "ShenasnameNo")
    public Integer getShenasnameNo() {
        return shenasnameNo;
    }

    public void setShenasnameNo(Integer shenasnameNo) {
        this.shenasnameNo = shenasnameNo;
    }

    @XmlElement(name = "BirthDate")
    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    @XmlElement(name = "Gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @XmlElement(name = "OfficeCode")
    public Integer getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(Integer officeCode) {
        this.officeCode = officeCode;
    }

    @XmlElement(name = "BookNo")
    public Integer getBookNo() {
        return bookNo;
    }

    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    @XmlElement(name = "BookRow")
    public Integer getBookRow() {
        return bookRow;
    }

    public void setBookRow(Integer bookRow) {
        this.bookRow = bookRow;
    }

    @XmlElement(name = "NameHasPrefix")
    public Integer getNameHasPrefix() {
        return nameHasPrefix;
    }

    public void setNameHasPrefix(Integer nameHasPrefix) {
        this.nameHasPrefix = nameHasPrefix;
    }

    @XmlElement(name = "NameHasPostFix")
    public Integer getNameHasPostFix() {
        return nameHasPostFix;
    }

    public void setNameHasPostFix(Integer nameHasPostFix) {
        this.nameHasPostFix = nameHasPostFix;
    }

    @XmlElement(name = "FamilyHasPrefix")
    public Integer getFamilyHasPrefix() {
        return familyHasPrefix;
    }

    public void setFamilyHasPrefix(Integer familyHasPrefix) {
        this.familyHasPrefix = familyHasPrefix;
    }

    @XmlElement(name = "FamilyHasPostFix")
    public Integer getFamilyHasPostFix() {
        return this.familyHasPostFix;
    }

    public void setFamilyHasPostFix(Integer familyHasPostFix) {
        this.familyHasPostFix = familyHasPostFix;
    }

    @XmlElement(name = "DateHasPostfix")
    public Integer getDateHasPostfix() {
        return dateHasPostfix;
    }

    public void setDateHasPostfix(Integer dateHasPostfix) {
        this.dateHasPostfix = dateHasPostfix;
    }

    @XmlElement(name = "DeathStatus")
    public Integer getDeathStatus() {
        return deathStatus;
    }

    public void setDeathStatus(Integer deathStatus) {
        this.deathStatus = deathStatus;
    }

    @XmlElement(name = "Message")
    public IndividualPersonMessage getMessage() {
        return message;
    }

    public void setMessage(IndividualPersonMessage message) {
        this.message = message;
    }

    @XmlElement(name = "ExceptionMessage")
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
