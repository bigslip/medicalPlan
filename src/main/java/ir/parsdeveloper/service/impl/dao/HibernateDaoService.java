package ir.parsdeveloper.service.impl.dao;

import ir.parsdeveloper.model.entity.core.AuditModel;
import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.model.entity.core.User;
import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hadi tayebi
 */
@Repository
//@Transactional(readOnly = true)
public class HibernateDaoService implements Serializable {// implements DaoService, Serializable {

    private SessionFactory sessionFactory;

    public Query createQuery(String jqlQuery) {
        return getSession().createQuery(jqlQuery);
    }

    @SuppressWarnings("unchecked")
    public <E> E loadById(Class<E> clazz, Serializable Id) {
        return (E) getSession().load(clazz, Id);
    }

    @SuppressWarnings("unchecked")
    public <E> E loadById(Class<E> clazz, Serializable Id, LockOptions LockOptions) {
        return (E) getSession().load(clazz, Id, LockOptions);
    }

    @SuppressWarnings("unchecked")
    public <E> E findById(Class<? extends E> clazz, Serializable id) {
        return (E) getSession().get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQuery(String namedQuery, Map<String, Object> params) {
        Query query = getSession().getNamedQuery(namedQuery);
        if (params != null && params.size() > 0) {
            query.setProperties(params);
        }
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQuery(String namedQuery, int firstResult, int maxResult, Map<String, Object> params) {
        Query query = getSession().getNamedQuery(namedQuery);
        if (params != null && params.size() > 0) {
            query.setProperties(params);
        }
        query.setFirstResult(firstResult).setMaxResults(maxResult);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQuery(String namedQuery, int firstResult, int maxResult, Object[] params) {
        Query query = getSession().getNamedQuery(namedQuery);
        if (params != null && params.length > 0) {
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
        }
        query.setFirstResult(firstResult).setMaxResults(maxResult);

        return query.list();
    }


    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQuery(String namedQuery, Object[] params) {
        Query query = getSession().getNamedQuery(namedQuery);
        if (params != null && params.length > 0) {
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
        }
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> find(String query, int firstResult, int maxResult) {
        return getSession().createQuery(query).setFirstResult(firstResult).setMaxResults(maxResult).list();
    }

    @SuppressWarnings("unchecked")
    public List find(String query) {
        return getSession().createQuery(query).list();
    }


    public Criteria createCriteria(Class clazz, Example example) {
        return getSession().createCriteria(clazz).add(example);
    }

    public <E> Criteria createCriteria(Class<? extends E> clazz) {
        return getSession().createCriteria(clazz);
    }

    public Object uniqueResult(Criteria criteria) {
        return criteria.uniqueResult();
    }


    public List listResult(Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> loadAll(Class<? extends E> clazz) {
        return getSession().createCriteria(clazz).list();
    }

    @SuppressWarnings("unchecked")
    public <E> E save(E entity) {
        if (entity instanceof AuditModel) {
            fillHistoricalColumns((AuditModel) entity);
        }
        return (E) getSession().merge(entity);
    }

    protected void fillHistoricalColumns(AuditModel model) {
        User user = getCurrentUser();
        Date today = new Date();
        if (model.getId() == null) {
            model.setCreationDate(today);
            model.setCreator(user);
        }

    }

    public User getCurrentUser() throws PersistenceException {
        try {
            User user = new User();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user;
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }


    public void saveOrUpdate(BaseModel entity) {
        getSession().saveOrUpdate(entity);
    }

    public void delete(BaseModel entity) {
        getSession().delete(entity);
    }

    public void evict(BaseModel entity) {
        getSession().evict(entity);
    }

    public void flush() {
        getSession().flush();
    }


    public Query getNamedQuery(String namedQuery) {
        return getSession().getNamedQuery(namedQuery);
    }

    protected Session getSession() {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.setFlushMode(FlushMode.COMMIT);
        return currentSession;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
