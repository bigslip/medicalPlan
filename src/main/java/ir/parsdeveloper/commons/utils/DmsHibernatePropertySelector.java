package ir.parsdeveloper.commons.utils;


import ir.parsdeveloper.model.entity.core.BaseModel;
import org.hibernate.criterion.Example;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DmsHibernatePropertySelector implements Example.PropertySelector {

    @Override
    public boolean include(Object propertyValue, String propertyName, Type type) {
        return propertyValue != null &&
                (((propertyValue instanceof String) && !StringUtils.isEmpty(((String) propertyValue).trim()))
                        || ((propertyValue instanceof Number) && ((Number) propertyValue).longValue() > 0)
                        || (propertyValue instanceof Date)
                        || (propertyValue instanceof BaseModel)
                );
    }
}
