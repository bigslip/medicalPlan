package ir.parsdeveloper.service.api.ejb;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author hadi tayebi
 */
@Remote
public interface EjbService {

    List userList();
}
