package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "OBSERV_HEART_RATE")
public class ObservHeartRate extends AuditModel<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    private Long hearRate;
    @Column(name = "HEAR_RATE")
    public Long getHearRate() {
        return hearRate;
    }

    public void setHearRate(Long hearRate) {
        this.hearRate = hearRate;
    }
}
