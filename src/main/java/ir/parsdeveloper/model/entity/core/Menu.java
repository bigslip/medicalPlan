package ir.parsdeveloper.model.entity.core;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

/**
 * @author hadi tayebi
 */
@Entity
@Table(name = "CB_MENU")
@Proxy(lazy = false)
@NamedQueries({
        @NamedQuery(name = Menu.GET_MAIN_MENU_LIST,
                query = "select m from Menu as m  where m.application.code=:applicationCode and m.parent is null order by m.priority"),
        @NamedQuery(name = Menu.GET_USER_MAIN_MENU_LIST,
                query = "select m from Menu as m join m.roles as r join r.users as u join fetch m.parent where m.application=:application and u=:user and m.parent.id>0 order by m.priority"),
        @NamedQuery(name = Menu.GET_ROLES_BY_MENU_ID,
                query = "select r from Menu as m join m.roles as r where m.id=?1")
})
public class Menu extends AuditModel<Long> {
    public final static String GET_MAIN_MENU_LIST = "GET_MAIN_MENU_LIST";
    public final static String GET_USER_MAIN_MENU_LIST = "GET_USER_MAIN_MENU_LIST";
    public final static String GET_ROLES_BY_MENU_ID = "GET_ROLES_BY_MENU_ID";
    private final static String SEQ_GENERATOR_NAME = "SQ_CB_MENU";

    private String name;
    private String flowName;
    private String params;
    private Integer priority;
    private Boolean active;
    private Menu parent;
    private List<Role> roles;
    private Application application;
    private Long roleId;
    private List<Menu> subMenu;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    @SequenceGenerator(name = SEQ_GENERATOR_NAME, sequenceName = SEQ_GENERATOR_NAME)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }

    @Column(name = "NAME", length = 60, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ACTIVE", nullable = false)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "FLOW_NAME", length = 128, nullable = false)
    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    @Column(name = "PARAMS", length = 255, nullable = true)
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Column(name = "PRIORITY", length = 3, nullable = false)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", nullable = true)
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Immutable
    @BatchSize(size = 20)
    @ManyToMany()
    @JoinTable(name = "CB_MENU_ROLE", joinColumns = @JoinColumn(name = "MENU_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            uniqueConstraints = @UniqueConstraint(name = "CB_MENU_ROLE_UN", columnNames = {"MENU_ID", "ROLE_ID"}))
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Transient
    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
    }

}
