package ir.parsdeveloper.model.medical;

import ir.parsdeveloper.model.entity.core.AuditModel;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "DOCTOR")
public class Doctor extends AuditModel<Long> {
    private Person person;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
