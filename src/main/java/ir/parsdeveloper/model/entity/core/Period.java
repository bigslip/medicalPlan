package ir.parsdeveloper.model.entity.core;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "PERIOD")
public class Period extends AuditModel<Long> {

    private Date startDate;
    private Date endDate;
    private Boolean disabled = false;
    private PlanTemplate planTemplate;
    private Doctor doctor;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "DISABLED")
    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }


    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID")
    public PlanTemplate getPlanTemplate() {
        return planTemplate;
    }

    public void setPlanTemplate(PlanTemplate planTemplate) {
        this.planTemplate = planTemplate;
    }

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    public Doctor getDoctor() {
        return doctor;
    }


    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}