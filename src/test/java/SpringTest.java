import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.GenericXmlContextLoader;

import java.io.IOException;

/**
 * @author hadi tayebi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"/config/spring-context.xml"}, loader = GenericXmlContextLoader.class)
//@ActiveProfiles(profiles = {"TEST"})
public class SpringTest {


    @Test
    public void testResourcePatternResolver() throws IOException {
        System.out.println("hello test");
    }


}
