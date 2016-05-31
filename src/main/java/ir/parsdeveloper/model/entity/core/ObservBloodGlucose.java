package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "OBSERV_BLOOD_GLUCOSE")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
public class ObservBloodGlucose extends Observation {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID", length = 19)
//    public Long getId() {
//        return super.getId();
//    }


    @Column(name = "BLOODGLUCOSE")
    private Long bloodGlucose;

    public Long getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(Long bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }
}
