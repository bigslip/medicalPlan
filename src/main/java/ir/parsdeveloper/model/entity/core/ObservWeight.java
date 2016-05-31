package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "OBSERV_WEIGHT")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "ID")
public class ObservWeight   extends Observation {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID", length = 19)
//    public Long getId() {
//        return super.getId();
//    }

    private Long weight;
    @Column(name = "WEIGHT")
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
