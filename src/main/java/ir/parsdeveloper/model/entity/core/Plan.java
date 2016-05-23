package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "PLAN")
public class Plan extends AuditModel<Long> {
    private Patient patient;
    private Period period;
    private Date startDate;
    private Date endDate;
    private PlanStatus planStatus;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PLAN_PATIENT",
            joinColumns = @JoinColumn(name = "PLAN_ID"), inverseJoinColumns = @JoinColumn(name = "PATIENT_ID")
    )
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    @ManyToOne
    @JoinColumn(name = "PLAN_STATUS")
    public PlanStatus getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(PlanStatus planStatus) {
        this.planStatus = planStatus;
    }

    @ManyToOne
    @JoinColumn(name = "PERIOD_ID")
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
