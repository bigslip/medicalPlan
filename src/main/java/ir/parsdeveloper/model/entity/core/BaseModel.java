package ir.parsdeveloper.model.entity.core;

import ir.parsdeveloper.commons.listener.PersistEntityListener;

import javax.persistence.EntityListeners;
import java.io.Serializable;
//import org.hibernate.annotations.Cache;

/**
 * @author hadi tayebi
 */

@EntityListeners(value = {PersistEntityListener.class})
public abstract class BaseModel<T extends Number> implements Serializable {

    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) return true;
        if (!(other instanceof BaseModel)) return false;

        final BaseModel model = (BaseModel) other;

        return this.getId() != null && model.getId() != null && this.getId().equals(model.getId());
    }

    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        int result;
        result = 31 * getId().hashCode();
        return result;
    }

}
