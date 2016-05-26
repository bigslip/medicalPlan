package ir.parsdeveloper.model.entity.core;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author hadi tayebi
 */
@Entity
@Table(name = "CB_USER", uniqueConstraints = @UniqueConstraint(name = "CB_USER_USERNAME_UN", columnNames = "USERNAME"))
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USER_NAME, query = "select u from User  u where u.username=:username"),
        @NamedQuery(name = User.FIND_BY_USER_NAME_WITH_ROLES, query = "select u from User u left join fetch u.roles where u.username=:username")
})
@NamedNativeQuery(name = "findUserFunc", query = "{ ? = call FUN_FIND_USER_BY_NAME(:P_USERNAME)}"
        , resultClass = User.class, hints = {
        @javax.persistence.QueryHint(name = "org.hibernate.callable", value = "true")}
)
public class User extends AuditModel<Long> {

    public final static String FIND_BY_USER_NAME = "FIND_BY_USER_NAME";
    public final static String FIND_BY_USER_NAME_WITH_ROLES = "FIND_BY_USER_NAME_WITH_ROLES";
    //    protected final static String SEQ_GENERATOR_NAME = "SQ_CB_USER";
    private String nickName;
    private String fixedName;
    private String username;
    private String password;
    private String confirmPassword;//transient
    private String email;
    private Date registerDate;
    private Boolean active = true;
    private Boolean locked = false;
    private Boolean expired = false;
    private Set<Role> roles;


    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }


    @Column(name = "NICK_NAME", length = 60, nullable = false)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "FIXED_NAME", length = 60, nullable = false)
    public String getFixedName() {
        return fixedName;
    }

    public void setFixedName(String fixedName) {
        this.fixedName = fixedName;
    }

    @Column(name = "USERNAME", unique = true, nullable = false, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    @Column(name = "PASSWORD", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //@Email(groups = ,message = "errors.user.email_is_invalid")
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGISTER_DATE", columnDefinition = "Date", nullable = false)
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Column(name = "ACTIVE", nullable = false, length = 1)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "LOCKED", nullable = false, length = 1)
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Column(name = "EXPIRED", nullable = false, length = 1)
    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }


    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinTable(name = "CB_USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            uniqueConstraints = @UniqueConstraint(name = "CB_USER_ROLE_UN", columnNames = {"USER_ID", "ROLE_ID"}))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
