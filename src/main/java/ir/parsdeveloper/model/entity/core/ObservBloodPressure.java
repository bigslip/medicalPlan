package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "OBSERV_HEART_RATE")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
public class ObservBloodPressure extends Observation {


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID", length = 19)
//    public Long getId() {
//        return super.getId();
//    }

    private Float systolic;
    private Float diastolic;

    @Column(name = "SYSTOLIC")
    public Float getSystolic() {
        return systolic;
    }

    public void setSystolic(Float systolic) {
        this.systolic = systolic;
    }

    @Column(name = "DIASTOLIC")
    public Float getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Float diastolic) {
        this.diastolic = diastolic;
    }
}
