package ir.parsdeveloper.model.entity.core;


import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.QueryHints;

import javax.persistence.*;
import java.util.Set;

/**
 * @author hadi tayebi
 */
@Entity
@Table(name = "CB_ROLE",
        uniqueConstraints = {
                @UniqueConstraint(name = "CB_ROLE_CODE_UN", columnNames = "CODE"),
                @UniqueConstraint(name = "CB_ROLE_NAME_UN", columnNames = "NAME")})
@NamedQueries({
        @NamedQuery(name = Role.GET_ROLES_BY_USER_ID, query = "select r from Role  r inner join r.users u where r.active=true and u.id= :userId",
                hints = {@QueryHint(name = QueryHints.COMMENT, value = "/*+ index(r,index1)*/")}),
        @NamedQuery(name = Role.GET_ROLES_BY_USER_ID_TEST_QUERY, query = "select r from Role  r inner join r.users u where u.id= :userId"),
        @NamedQuery(name = Role.GET_ROLES_BY_ROLE_ID,query = "from Role r where r.id in(:roleId)")
})
public class Role extends AuditModel<Long> {

    public final static String GET_ROLES_BY_USER_ID = "GET_ROLES_BY_USER_ID";
    public final static String GET_ROLES_BY_USER_ID_TEST_QUERY = "GET_ROLES_BY_USER_ID_TEST_QUERY";
    public final static String GET_ROLES_BY_ROLE_ID = "GET_ROLES_BY_ROLE_ID";


    public Role() {
    }

    public Role(Long id) {
        setId(id);
    }

    private String code;
    private String name;
    private String fixedName;
    private Boolean active = Boolean.TRUE;
    private String description;

    private Role parent;
    private Set<Role> subRoles;
    private Integer version;
    private Set<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 19)
    public Long getId() {
        return super.getId();
    }


    @Column(name = "CODE", unique = true, nullable = false, length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", unique = true, nullable = false, length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FIXED_NAME", unique = true, nullable = false, length = 60)
    public String getFixedName() {
        return fixedName;
    }

    public void setFixedName(String fixedName) {
        this.fixedName = fixedName;
    }

    @Column(name = "ACTIVE", nullable = false)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @BatchSize(size = 20)
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CB_USER_ROLE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"),
            uniqueConstraints = @UniqueConstraint(name = "CB_USER_ROLE_UN", columnNames = {"USER_ID", "ROLE_ID"}))
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", nullable = true)
    public Role getParent() {
        return parent;
    }

    public void setParent(Role parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    public Set<Role> getSubRoles() {
        return subRoles;
    }

    public void setSubRoles(Set<Role> subRoles) {
        this.subRoles = subRoles;
    }
}
