package ir.parsdeveloper.model.entity.core;

import javax.persistence.*;
import java.util.Date;


/**
 * @author hadi tayebi
 */
@Entity
@Table(name = "CB_LOG_EXCEPTION")
public class LogException extends BaseModel<Long> {

    private final static String SEQ_GENERATOR_NAME = "SQ_LOG_EXCEPTION";

    private String flowId;
    private String stateId;
    private String exceptionClass;
    private byte[] errorMessage;
    private Application application;
    private Long userId;
    private Date eventDate;
    private String className;
    private String methodName;
    private Integer lineNumber;

    public LogException() {
    }

    public LogException(Throwable exceptionCause, Application application, Long userId, String flowId,
                        String stateId) {
        StackTraceElement[] stackTraceElements = exceptionCause.getStackTrace();

        if (stackTraceElements != null && stackTraceElements.length > 0) {
            StackTraceElement stackTraceElement = getStackTraceElement(stackTraceElements);
            this.className = stackTraceElement.getClassName();
            this.methodName = stackTraceElement.getMethodName();
            this.lineNumber = stackTraceElement.getLineNumber();
        }

        this.exceptionClass = exceptionCause.getClass().getName();
        this.errorMessage = exceptionCause.toString().getBytes();
        this.application = application;
        this.userId = userId;
        this.stateId = stateId;
        this.flowId = flowId;
        eventDate = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    @SequenceGenerator(name = SEQ_GENERATOR_NAME, sequenceName = SEQ_GENERATOR_NAME)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @Column(name = "CLASS_NAME", length = 512)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Column(name = "METHOD_NAME", length = 512)
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Column(name = "LINE_NUMBER", scale = 4)
    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Column(name = "FLOW_ID", length = 255)
    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    @Column(name = "STATE_ID", length = 2505)
    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateName) {
        this.stateId = stateName;
    }

    @Column(name = "EXCEPTION_CLASS", length = 512)
    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY, optional = false)
    @Column(name = "ERROR_MESSAGE", nullable = false)/*, columnDefinition = "BLOB not null"*/
    public byte[] getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(byte[] errorMessage) {
        this.errorMessage = errorMessage;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    @Column(name = "USER_ID", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EVENT_DATE", nullable = false, columnDefinition = "Date")
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    private StackTraceElement getStackTraceElement(StackTraceElement[] stackTraceElements) {
        for (StackTraceElement element : stackTraceElements) {
            if (element.getClassName().startsWith("ir.parsdeveloper")) {
                return element;
            }
        }
        return stackTraceElements[stackTraceElements.length];
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) return true;
        if (!(other instanceof BaseModel)) return false;

        final BaseModel model = (BaseModel) other;

        if (this.getId() != null && model.getId() != null && this.getId().equals(model.getId())) {
            return true;
        }
        LogException that = (LogException) other;
        return this.getExceptionClass().equals(that.getExceptionClass()) &&
                this.getClassName().equals(that.getClassName()) &&
                this.getFlowId().equals(that.getFlowId()) &&
                this.getStateId().equals(that.getStateId()) &&
                this.getLineNumber().equals(that.getLineNumber());

    }

}