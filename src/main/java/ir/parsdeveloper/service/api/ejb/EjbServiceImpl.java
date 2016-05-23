package ir.parsdeveloper.service.api.ejb;

import ir.parsdeveloper.commons.listener.CustomSpringBeanAutowiringInterceptor;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * @author hadi tayebi
 */
@Remote(EjbService.class)
@Stateless(name = "EjbService", mappedName = "EjbService")
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class EjbServiceImpl implements EjbService {


    public EjbServiceImpl() {
        System.out.println("EjbServiceImpl created");
    }

    @Override
    public List userList() {
        return null;
    }


    public void setServletContext(ServletContext servletContext) {

    }

}
