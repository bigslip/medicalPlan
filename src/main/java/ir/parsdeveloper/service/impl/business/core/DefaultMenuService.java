package ir.parsdeveloper.service.impl.business.core;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Menu;
import ir.parsdeveloper.service.api.business.core.MenuService;
import ir.parsdeveloper.service.api.dao.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hadi tayebi
 *         Date: 2/14/14
 */
@Service
public class DefaultMenuService implements MenuService {

    protected DaoService daoService;

    public List<Menu> getMenuLevelOne(String applicationCode) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        params.put("applicationCode", applicationCode);
        return daoService.findByNamedQuery(Menu.GET_MAIN_MENU_LIST, params);
    }

    @Autowired
    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }
}
