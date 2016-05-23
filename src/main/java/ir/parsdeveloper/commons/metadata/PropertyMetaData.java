package ir.parsdeveloper.commons.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author hadi tayebi
 */

public class PropertyMetaData {

    private String name;
    private Object type;
    private Method getMethod;
    private Method setMethod;
    private Boolean isSearchable = false;
    private Boolean isTransient = false;
    private Boolean isBasicColumn = false;
    private Boolean isOneToMany = false;
    private Boolean isOneToOne = false;
    private Boolean isManyToOne = false;
    private Annotation[] annotations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    public Boolean getSearchable() {
        return isSearchable;
    }

    public void setSearchable(Boolean searchable) {
        isSearchable = searchable;
    }

    public Boolean getTransient() {
        return isTransient;
    }

    public void setTransient(Boolean aTransient) {
        isTransient = aTransient;
    }

    public Boolean getBasicColumn() {
        return isBasicColumn;
    }

    public void setBasicColumn(Boolean basicColumn) {
        isBasicColumn = basicColumn;
    }

    public Boolean getOneToMany() {
        return isOneToMany;
    }

    public void setOneToMany(Boolean oneToMany) {
        isOneToMany = oneToMany;
    }

    public Boolean getOneToOne() {
        return isOneToOne;
    }

    public void setOneToOne(Boolean oneToOne) {
        isOneToOne = oneToOne;
    }

    public Boolean getManyToOne() {
        return isManyToOne;
    }

    public void setManyToOne(Boolean manyToOne) {
        isManyToOne = manyToOne;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
}
