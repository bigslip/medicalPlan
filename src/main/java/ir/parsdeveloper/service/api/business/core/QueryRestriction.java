package ir.parsdeveloper.service.api.business.core;

import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.model.entity.core.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

/**
 * @auther hadi tayebi
 */
public interface QueryRestriction<T extends BaseModel> {

    void setRestriction(T domain, User currentUser);

    void doPrepareExample(T domain, Example example);

    void doPrepareCriteria(T domain, Criteria criteria);

}
