package ir.parsdeveloper.model.entity.core;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "PERSON")
public class Person extends AuditModel<Long> {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Integer gender;
    private String phone;
    private String address;
    private Long nationalId;
    private String fixedName;
    protected final static String SEQ_GENERATOR_NAME = "SQ_CB_PERSON";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "BIRTH_DATE")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "GENDER")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "NATIONAL_ID")
    public Long getNationalId() {
        return nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    @Column(name = "FIXED_NAME")
    public String getFixedName() {
        return fixedName;
    }

    public void setFixedName(String fixedName) {
        this.fixedName = fixedName;
    }
}
