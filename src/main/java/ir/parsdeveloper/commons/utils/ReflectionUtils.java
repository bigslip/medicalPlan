package ir.parsdeveloper.commons.utils;


import org.springframework.beans.BeanWrapperImpl;

import javax.management.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author hadi tayebi
 */
public class ReflectionUtils extends org.springframework.util.ReflectionUtils {
    public final static String PREFIX_GET_METHOD = "get";
    public final static String PREFIX_SET_METHOD = "set";

    public static Object getProperty(Object object, String property) throws ReflectionException {
        return new BeanWrapperImpl(object).getPropertyValue(property);
    }

    public static Class getPropertyType(Object object, String property) throws ReflectionException {
        return new BeanWrapperImpl(object).getPropertyType(property);
    }

    public static void setProperty(Object object, String property, Object value) throws ReflectionException {
        new BeanWrapperImpl(object).setPropertyValue(property, value);
    }

    /**
     * Is this member publicly accessible.
     *
     * @param clazz  The class which defines the member
     * @param member The memeber.
     * @return True if the member is publicly accessible, false otherwise.
     */
    public static boolean isPublic(Class clazz, Member member) {
        return Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(clazz.getModifiers());
    }


    /**
     * Determine if the given class is declared abstract.
     *
     * @param clazz The class to check.
     * @return True if the class is abstract, false otherwise.
     */
    public static boolean isAbstractClass(Class clazz) {
        int modifier = clazz.getModifiers();
        return Modifier.isAbstract(modifier) || Modifier.isInterface(modifier);
    }

    /**
     * Determine is the given class is declared final.
     *
     * @param clazz The class to check.
     * @return True if the class is final, flase otherwise.
     */
    public static boolean isFinalClass(Class clazz) {
        return Modifier.isFinal(clazz.getModifiers());
    }

    public static void setMethod(Object target, String methodName, Object[] args, Class<?>... paramTypes) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        methodName = StringUtils.capitalize(methodName);
        Method setMethod = findMethod(target.getClass(), PREFIX_SET_METHOD + methodName, paramTypes);
        invokeMethod(setMethod, target, args);
    }

    public static boolean isProperty(Method m) {
        return !m.getReturnType().equals(Void.TYPE)
                && !m.isSynthetic()
                && !m.isBridge()
                && !Modifier.isStatic(m.getModifiers())
                && m.getParameterTypes().length == 0
                && (m.getName().startsWith("get") || m.getName().startsWith("is"));
        // TODO should we use stronger checking on the naming of getters/setters, or just leave this to the validator?
    }

}
