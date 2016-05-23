package ir.parsdeveloper.model.entity.core;

import ir.parsdeveloper.commons.annotations.Searchable;

import javax.persistence.*;

/**
 * @author hadi tayebi
 */
@Entity
@Table(name = "CB_APPLICATION")
//@Immutable
@NamedQueries({
        @NamedQuery(name = Application.FIND_BY_APPLICATION_CODE,
                query = "select a from Application as a where a.code=:applicationCode")
})
public class Application extends BaseModel<Long> {

    public final static String FIND_BY_APPLICATION_CODE = "FIND_BY_APPLICATION_CODE";

    private String code;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", length = 3)
    public Long getId() {
        return super.getId();
    }


    @Column(name = "CODE", nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Searchable()
    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
