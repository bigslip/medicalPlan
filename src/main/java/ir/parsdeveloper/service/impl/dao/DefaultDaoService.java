package ir.parsdeveloper.service.impl.dao;

import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.service.api.dao.DaoService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hadi tayebi
 */
@Repository
@SuppressWarnings("unchecked")
public class DefaultDaoService implements DaoService, Serializable {


    protected EntityManager entityManager;

    public <E> E findById(Class<? extends E> clazz, Serializable id) {
        return entityManager.find(clazz, id);
    }

    public Query createQuery(String jqlQuery) {
        return createQuery(jqlQuery, null);
    }

    public StoredProcedureQuery createStoredProcedureQuery(String storedProcedureName, Class... resultClasses) {
        return entityManager.createStoredProcedureQuery(storedProcedureName, resultClasses);
    }

    public Query createQuery(String jqlQuery, Map<String, Object> params) {
        Query query = entityManager.createQuery(jqlQuery);
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                query.setParameter(key, params.get(key));
            }
        }
        return query;
    }

    public Query createNamedQuery(String namedQuery, Map<String, Object> params) {
        Query query = entityManager.createNamedQuery(namedQuery);
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                query.setParameter(key, params.get(key));
            }
        }
        return query;
    }

    public <E> List<E> findByNamedQuery(String namedQuery, Map<String, Object> params) {
        return createNamedQuery(namedQuery, params).getResultList();
    }

    public <E> List<E> findByNamedQuery(String namedQuery, int firstResult, int maxResult, Map<String, Object> params) {
        Query query = createNamedQuery(namedQuery, params);
        query.setFirstResult(firstResult).setMaxResults(maxResult);
        return query.getResultList();
    }

    public List findByNamedQuery(String namedQuery, int firstResult, int maxResult, Object[] params) {
        Query query = entityManager.createNamedQuery(namedQuery);
        if (params != null && params.length > 0) {
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
        }
        query.setFirstResult(firstResult).setMaxResults(maxResult);

        return query.getResultList();
    }


    public List findByNamedQuery(String namedQuery, Object[] params) {
        Query query = entityManager.createNamedQuery(namedQuery);
        if (params != null && params.length > 0) {
            int i = 1;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
        }
        return query.getResultList();
    }

    public <E> List<E> find(String query, int firstResult, int maxResult) {
        return entityManager.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
    }

    public <E> List<E> find(String query) {
        return entityManager.createQuery(query).getResultList();
    }

    public <E> E save(E entity) {
        return entityManager.merge(entity);
    }

    public void delete(BaseModel entity) {
        entityManager.remove(entity);
    }

    public void refresh(BaseModel entity) {
        entityManager.refresh(entity);
    }

    public void evict(BaseModel entity) {
        entityManager.detach(entity);
    }

    public void flush() {
        entityManager.flush();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
