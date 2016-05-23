package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "PLAN_TEMPLATE")
public class PlanTemplate extends AuditModel<Long> {
    private String description;
    private Doctor doctor;
    private Period period;
    private Long doctorId;


    public PlanTemplate() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @ManyToOne
    @JoinColumn(name = "PERIOD_ID")
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Transient
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }


}