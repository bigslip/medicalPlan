package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;
import java.util.Date;

/**
 * @author h.zare on 9/27/2015.
 */
@MappedSuperclass
public class AuditModel<T extends Number> extends BaseModel<T> {

    private User creator;
    private Date creationDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID", updatable = false, nullable = false)
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Column(name = "CREATION_DATE", updatable = false, nullable = false, columnDefinition = "Date")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
