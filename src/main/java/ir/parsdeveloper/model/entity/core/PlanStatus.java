package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table(name = "PLAN_STATUS")
public class PlanStatus extends BaseModel<Long> {
    public PlanStatus() {
    }

    public PlanStatus(Long id) {
        setId(id);
    }

    @Id
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }
}
