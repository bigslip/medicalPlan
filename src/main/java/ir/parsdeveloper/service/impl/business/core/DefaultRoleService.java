package ir.parsdeveloper.service.impl.business.core;


import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.CommonUtils;
import ir.parsdeveloper.model.entity.core.Application;
import ir.parsdeveloper.model.entity.core.Role;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.business.core.QueryRestriction;
import ir.parsdeveloper.service.api.business.core.RoleService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author m.namzi & h.tayebi
 */
@Service
public class DefaultRoleService extends DefaultBasicService<Role> implements RoleService, QueryRestriction<Role> {


    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void setRestriction(Role domain, User currentUser) {
        domain.setActive(Boolean.TRUE);
        domain.setCode("%_ADMIN");
    }

    @Override
    public void doPrepareExample(Role domain, Example example) {
        example.enableLike(MatchMode.START);
    }

    @Override
    public void doPrepareCriteria(Role domain, Criteria criteria) {
        criteria.add(Restrictions.like("name", "%admin%"));
        criteria.add(Restrictions.eq("name", "admin"));
        criteria.addOrder(Order.desc("id"));
    }

    public List<Role> getUserRole(User user) {
        if (user == null || user.getId() == null) {
            return null;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userId", user.getId());
        return daoService.findByNamedQuery(Role.GET_ROLES_BY_USER_ID, params);
    }

    @Transactional(rollbackFor = Throwable.class, timeout = -1)
    public Role addRole(Role role, User currentUser) throws ServiceException {
        role.setFixedName(CommonUtils.fixString(role.getName()));
        return saveEntity(role);
    }

    public List<Application> getSubSystemList() throws ServiceException {
        return daoService.find("select q from Application q");
    }

    @Transactional(timeout = 600, readOnly = false, rollbackFor = Throwable.class)
    public void test() {
        try {
            StoredProcedure procedure = new StoredProcedure(jdbcTemplate, "TEST_FUNC") {
                {
                    declareParameter(new SqlOutParameter("RESULT", Types.VARCHAR));
                    declareParameter(new SqlParameter("FIRSTNAME", Types.VARCHAR));
                    declareParameter(new SqlParameter("LASTNAME", Types.VARCHAR));
                }
            };
            procedure.setFunction(true);
            Map<String, Object> execute1 = procedure.execute("hadi", "taybi");

            System.out.println(execute1);
           /* StoredProcedureQuery storedProcedureQuery = daoService.createStoredProcedureQuery("TEST_FUNC");
            storedProcedureQuery.registerStoredProcedureParameter("P_AGE", Long.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("P_AGE", 10L);
            //storedProcedureQuery.
            storedProcedureQuery.execute();
            Object outputParameterValue = storedProcedureQuery.getOutputParameterValue(1);
            System.out.println("result : "+outputParameterValue);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //storedProcedureQuery.
        //storedProcedureQuery.execute();
    }

    public String execute(Connection connection) throws SQLException {
        CallableStatement call = connection.prepareCall("{ ? = call TEST_FUNC(?,?) }");
        call.setString(1, "hadi");
        call.setString(2, " tayebi");
        call.executeUpdate();
        return call.getString(1);
    }

}
