package ir.parsdeveloper.service.impl.business.core;

import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.business.core.TestService;
import ir.parsdeveloper.service.api.dao.DaoService;
import ir.parsdeveloper.service.impl.dao.HibernateDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by h.tayebi on 2/16/2016.
 */
@Service
public class DefaultTestService implements TestService {

    DaoService daoService;
    HibernateDaoService hibernateDaoService;

    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Throwable.class, timeout = 20000)
    public void testDao() {
        User user = daoService.findById(User.class, 1L);
        user.setNickName("hadi __");

    }

    /*   @Transactional( propagation = Propagation.REQUIRED,
               readOnly = false,
               rollbackFor = Throwable.class,timeout = 20000)
       public void testDao() {
           User user = hibernateDaoService.findById(User.class, 1L);
           user.setNickName("tayebi hoynnbnnnbnn");
       }
   */
    @Autowired
    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }

    @Autowired
    public void setHibernateDaoService(HibernateDaoService hibernateDaoService) {
        this.hibernateDaoService = hibernateDaoService;
    }
}
