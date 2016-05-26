package ir.parsdeveloper.model.entity.core;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author hadi tayebi
 */
@Entity
@Table(name = "CB_LOGIN_HISTORY")
public class LoginHistory extends BaseModel<Long> {

    private final static String SEQ_GENERATOR_NAME = "SQ_LOGIN_HISTORY";

    private User user;
    private Application application;
    private Date loginTime;
    private String ipAddress;
    private Integer duration;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOGIN_TIME", nullable = false, columnDefinition = "DATETIME")
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Column(name = "IP_ADDRESS", nullable = false, length = 30)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String addressIP) {
        this.ipAddress = addressIP;
    }

    @Column(name = "DURATION")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @NotNull(message = "err.user_is_required")
    @Fetch(FetchMode.JOIN)
    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NotNull(message = "err.application_is_required")
    @Fetch(FetchMode.JOIN)
    @ManyToOne(optional = false)
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
