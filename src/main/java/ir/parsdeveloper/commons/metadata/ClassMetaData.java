package ir.parsdeveloper.commons.metadata;

import ir.parsdeveloper.model.entity.core.BaseModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author hadi tayebi
 * @since 1.0
 */

public class ClassMetaData {

    private Class<? extends BaseModel> clazz;
    private Annotation[] annotations;
    private Method[] methods;
    private PropertyMetaData[] searchableList;
    private PropertyMetaData[] preloadLovList;

    public Class<? extends BaseModel> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends BaseModel> clazz) {
        this.clazz = clazz;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public PropertyMetaData[] getSearchableList() {
        return searchableList;
    }

    public void setSearchableList(PropertyMetaData[] searchableList) {
        this.searchableList = searchableList;
    }

    public PropertyMetaData[] getPreloadLovList() {
        return preloadLovList;
    }

    public void setPreloadLovList(PropertyMetaData[] preloadLovList) {
        this.preloadLovList = preloadLovList;
    }
}
