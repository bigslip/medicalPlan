package ir.parsdeveloper.model.entity.core;

/**
 * @author hadi tayebi
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CB_ENTITY_LOG")
public class EntityLog extends BaseModel<Long> {

    public final static Integer INSERT_ENTITY_EVENT_TYPE = 1;
    public final static Integer UPDATE_ENTITY_EVENT_TYPE = 2;
    public final static Integer DELETE_ENTITY_EVENT_TYPE = 3;
    private final static String SEQ_GENERATOR_NAME = "SQ_CB_ENTITY_LOG";

    private Long entityId;
    private Long userId;
    private Integer eventType;
    private Date eventTime;
    private String entityName;
    private String tableName;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    @SequenceGenerator(name = SEQ_GENERATOR_NAME, sequenceName = SEQ_GENERATOR_NAME)
    @Column(name = "ID", length = 3)
    public Long getId() {
        return super.getId();
    }

    @Column(name = "ENTITY_ID", nullable = false)
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Column(name = "USER_ID", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "EVENT_TYPE", nullable = false)
    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "EVENT_TIME", columnDefinition = "Date", nullable = false)
    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    @Column(name = "ENTITY_NAME", length = 512, nullable = false)
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Column(name = "TABLE_NAME", length = 60, nullable = false)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
