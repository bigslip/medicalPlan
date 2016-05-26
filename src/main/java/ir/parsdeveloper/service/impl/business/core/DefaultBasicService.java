package ir.parsdeveloper.service.impl.business.core;

import ir.parsdeveloper.commons.MessageCodes;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.StringUtils;
import ir.parsdeveloper.model.entity.core.AuditModel;
import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.service.api.business.core.QueryRestriction;
import ir.parsdeveloper.service.api.dao.DaoService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.execution.RequestContext;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author m.namzi & h.tayebi
 */
@Service
@Transactional(readOnly = true)
public class DefaultBasicService<T extends BaseModel> implements BasicService<T>, Serializable {

    public static final int MAX_EXAMPLE_DEPTH = 2;
    protected DaoService daoService;
    protected Example.PropertySelector propertySelector;

    public <E extends BaseModel> E saveEntity(E entity) throws ServiceException {
        try {
            detachTransientObject(entity);
        } catch (Exception e) {
            throw new ServiceException(MessageCodes.UNKNOWN_ERROR_MESSAGE);
        }
        return daoService.save(entity);
    }

    public <E extends AuditModel> E saveEntity(E entity, User user) throws ServiceException {
        try {
            detachTransientObject(entity);
        } catch (Exception e) {
            throw new ServiceException(MessageCodes.UNKNOWN_ERROR_MESSAGE);
        }
        entity.setCreator(user);
        return daoService.save(entity);
    }

    protected void detachTransientObject(BaseModel entity) throws Exception {
        Class<? extends BaseModel> clazz = entity.getClass();
        Method[] clazzMethods = clazz.getDeclaredMethods();

        for (Method method : clazzMethods) {
            ManyToOne manyToOne = method.getAnnotation(ManyToOne.class);
            if (manyToOne != null) {
                BaseModel obj = (BaseModel) method.invoke(entity);
                if (obj != null &&
                        (obj.getId() == null ||
                                (obj.getId().longValue() == -1))) {
                    String methodName = method.getName();
                    methodName = methodName.replaceFirst("g", "s");
                    clazz.getMethod(methodName, method.getReturnType()).invoke(entity, new Object[]{null});
                }

            }
        }

    }

    public <E extends BaseModel> E deleteEntity(E entity, User currentUser) {
        return null;
    }

    @Transactional(readOnly = true, timeout = 60)
    public List<T> prepareEntityList(T entity, User currentUser, int currentPage, int pageSize) throws BaseException {
        Criteria criteria = createCriteria(entity, currentUser);
        criteria.setFirstResult((currentPage - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    public Long getCountEntity(T entity, User currentUser) throws BaseException {
        Criteria criteria = createCriteria(entity, currentUser);
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    private Criteria createCriteria(T entity, User currentUser) {
        entity = prepareExampleDomain(entity, currentUser);
        Example example = prepareExample(entity);
        return prepareCriteria(entity, example);
    }

    protected T createExampleDomain() {
        try {
            return (T) ((Class) ((ParameterizedType) this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private T prepareExampleDomain(T exampleDomain, User currentUser) {
        if (exampleDomain == null) {
            exampleDomain = createExampleDomain();
        }
        if (this instanceof QueryRestriction) {
            ((QueryRestriction) this).setRestriction(exampleDomain, currentUser);
        }
        return exampleDomain;
    }

    private Example prepareExample(T domain) {
        Example example = Example.create(domain);
        example.setPropertySelector(propertySelector);
        if (this instanceof QueryRestriction) {
            ((QueryRestriction) this).doPrepareExample(domain, example);
        }
        return example;
    }

/*    private  Map<String, Collection<? extends BaseModel>>  internalPreLoadLov(String flowName, String viewState, List<String> entities) {
        //load file and find preloads
         preLoadLov(context);
    }*/

    private Criteria prepareCriteria(T domain, Example example) {
        Criteria criteria = null;// daoService.createCriteria(domain.getClass(), example);
        criteria.setReadOnly(true);
        addExample(domain, criteria, 0);
        if (this instanceof QueryRestriction) {
            ((QueryRestriction) this).doPrepareCriteria(domain, criteria);
        }
        return criteria;
    }

    private void addExample(BaseModel domain, Criteria criteria, int depth) {
        if (depth >= MAX_EXAMPLE_DEPTH) {
            return;
        }


        Method[] declaredMethods = domain.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {

            if (method.getName().substring(0, 3).equals("get")) {
                //if (AnnotationUtils.findAnnotation(method, Searchable.class) != null) {
                String methodName = StringUtils.uncapitalize(method.getName().substring(3));
                try {
                    Object result = method.invoke(domain, null);
                    if (result != null && BaseModel.class.isAssignableFrom(result.getClass())) {
                        addExample((BaseModel) result, criteria, depth + 1);
                        Example example = Example.create(result);
                        example.setPropertySelector(propertySelector);
                        JoinType joinType = JoinType.INNER_JOIN;
                        ManyToOne manyToOne = AnnotationUtils.findAnnotation(method, ManyToOne.class);
                        if (manyToOne != null) {
                            if (manyToOne.optional()) {
                                joinType = JoinType.LEFT_OUTER_JOIN;
                            }
                        } else {
                            OneToOne oneToOne = AnnotationUtils.findAnnotation(method, OneToOne.class);
                            if (oneToOne != null) {
                                if (oneToOne.optional()) {
                                    joinType = JoinType.LEFT_OUTER_JOIN;
                                }
                            }
                        }

                        Criteria criteriaAdd = criteria.createCriteria(methodName, joinType).add(example);
                        if (((BaseModel) result).getId() != null && ((BaseModel) result).getId().longValue() > 0L) {
                            criteriaAdd.add(Restrictions.eq("id", ((BaseModel) result).getId()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                //}
            }
        }
    }

    public void preLoadLov(RequestContext context, String viewState, Map<String, Collection<? extends BaseModel>> preloadLovs) {

    }

    @Autowired
    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }

    @Autowired
    public void setPropertySelector(Example.PropertySelector propertySelector) {
        this.propertySelector = propertySelector;
    }
}


/*
*    Method[] declaredMethods = domain.getClass().getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (m.getName().substring(3).equals("get")) {
                try {
                    Object invoke = m.invoke(domain, null);
                    if (invoke instanceof BaseModel) {
                        Method[] declaredMethodsInner = invoke.getClass().getDeclaredMethods();
                        for (Method mInner : declaredMethodsInner) {
                            if (mInner.getName().substring(3).equals("get")) {
                                Object invokedInner = m.invoke(invoke, null);

                                if (invokedInner != null && !invokedInner.getClass().isAssignableFrom(BaseModel.class)) {

                                    if (invokedInner instanceof String) {
                                        if (((String) invokedInner).trim().isEmpty()) {
                                            continue;
                                        }
                                    }else if(invokedInner instanceof Number){
                                        if(((Number) invokedInner).longValue() < 0){
                                             continue;
                                        }
                                    }

                                    criteria.add(
                                            Restrictions.eq(
                                                    StringUtils.capitalize(m.getName().substring(3)) + "." + StringUtils.capitalize(mInner.getName().substring(3))
                                                    , invokedInner));
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
* */