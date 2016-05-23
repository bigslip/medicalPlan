package ir.parsdeveloper.service.api.business.core;

import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.model.entity.core.User;

import java.util.List;

/**
 * @author m.namzi & h.tayebi
 */
public interface BasicService<T extends BaseModel> {

    <E extends BaseModel> E saveEntity(E entity) throws ServiceException;

    <E extends BaseModel> E deleteEntity(E entity, User currentUser);

    List<T> prepareEntityList(T entity, User currentUser, int currentPage, int pageSize) throws BaseException;

    Long getCountEntity(T entity, User currentUser) throws BaseException;
}
