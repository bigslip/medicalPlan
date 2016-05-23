package ir.parsdeveloper.web.controller;

import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.business.core.TestService;
import ir.parsdeveloper.service.api.dao.DaoService;
import ir.parsdeveloper.service.api.ejb.EjbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hadi tayebi
 */
@Controller
public class TestController {

    EjbService ejbService;

    TestService testDao;
    DaoService daoService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {

        testDao.testDao();
        User user = daoService.findById(User.class, 1L);
        return "time : " + user.getNickName();
    }

    @Autowired
    public void setTestDao(TestService testDao) {
        this.testDao = testDao;
    }

    @Autowired
    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }



    //@Resource(mappedName = "java:comp/env/ejbService")
    public void setEjbService(EjbService ejbService) {
        this.ejbService = ejbService;
    }
}
