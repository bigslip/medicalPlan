package ir.parsdeveloper.model.entity.core;


import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "DOCTOR")
public class Doctor extends AuditModel<Long> {

    private Person person;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne()
    @JoinColumn(name = "PERSON_ID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
