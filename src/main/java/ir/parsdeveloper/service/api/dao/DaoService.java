package ir.parsdeveloper.service.api.dao;

import ir.parsdeveloper.model.entity.core.BaseModel;

import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author hadi tayebi
 */
public interface DaoService {

    <E> E findById(Class<? extends E> clazz, Serializable id);

    Query createQuery(String jqlQuery);

    StoredProcedureQuery createStoredProcedureQuery(String storedProcedureName, Class... resultClasses);

    Query createQuery(String jqlQuery, Map<String, Object> params);

    Query createNamedQuery(String namedQuery, Map<String, Object> params);

    <E> List<E> findByNamedQuery(String namedQuery, Map<String, Object> params);

    <E> List<E> findByNamedQuery(String namedQuery, int firstResult, int maxResult, Map<String, Object> params);

    <E> List<E> find(String query, int firstResult, int maxResult);

    <E> List<E> find(String query);

    <E> E save(E entity);

    void delete(BaseModel entity);

    void refresh(BaseModel entity);

    void evict(BaseModel entity);

    void flush();
}
