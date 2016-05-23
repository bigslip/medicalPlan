package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;

/**
 * @author hadi tayebi
 */
@Entity
@Table(name = "CT_STOPWATCH_RESULT")
public class StopwatchResult extends BaseModel<Long> {

    protected final static String SEQ_GENERATOR_NAME = "SQ_STOPWATCH_RESULT";
    private String pointcut;
    private String methodName;
    private Long startTime;
    private Long endTime;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    @SequenceGenerator(name = SEQ_GENERATOR_NAME, sequenceName = SEQ_GENERATOR_NAME)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @Column(name = "POINTCUT")
    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    @Column(name = "METHOD_NAME")
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Column(name = "START_TIME")
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
