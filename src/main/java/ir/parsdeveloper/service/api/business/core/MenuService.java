package ir.parsdeveloper.service.api.business.core;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Menu;

import java.util.List;

/**
 * @author hadi tayebi
 *         Date: 2/14/14
 */
public interface MenuService {

    List<Menu> getMenuLevelOne(String subsystemCode) throws ServiceException;


}
