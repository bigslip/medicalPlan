package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * Created by bahram on 5/3/16.
 */
@Entity
@Table
public class PlanStatus extends BaseModel<Long> {
    @Id
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }
}
