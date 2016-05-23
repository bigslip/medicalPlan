package test;

import ir.parsdeveloper.commons.utils.ReflectionUtils;
import ir.parsdeveloper.model.entity.core.BaseModel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author hadi tayebi
 */

public class Reflection {

    public static void main(String[] args) {

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        //add include filters which matches all the classes (or use your own)
        //provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*Config")));
        provider.addIncludeFilter(new AssignableTypeFilter(BaseModel.class));

        //get matching classes defined in the package
        final Set<BeanDefinition> classes = provider.findCandidateComponents("ir.parsdeveloper.model.entity");

        //this is how you can load the class type from BeanDefinition instance
        for (BeanDefinition bean : classes) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(bean.getBeanClassName());
                Method[] methods = clazz.getMethods();
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (ReflectionUtils.isProperty(method)) {
                        System.out.println("method : " + method.getName());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("clazz : " + clazz);

        }

    }
}
