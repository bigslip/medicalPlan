package ir.parsdeveloper.commons.helper;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hadi tayebi
 */
public class DispatchMethodInvoker implements Serializable {

    private static Map<String, Method> methodCache;

    static {
        methodCache = new ConcurrentHashMap<>(1000);
    }

    private Object target;
    private Class<?> targetClass;
    private String keyPrefix;
    private Class<?>[] parameterTypes;

    public DispatchMethodInvoker(Object target, Class<?>... parameterTypes) {
        Assert.notNull(target, "The target of a dispatch method invocation is required");
        this.target = target;
        this.parameterTypes = parameterTypes;
        this.targetClass = target.getClass();
        this.keyPrefix = this.targetClass.getName() + ".";
    }

    public Object invoke(String methodName, Object... arguments) throws Exception {
        Method method = getDispatchMethod(methodName);
        return method.invoke(target, arguments);
    }

    private Method getDispatchMethod(String methodName) throws NoSuchMethodException {
        Method method = methodCache.get(keyPrefix + methodName);
        if (method == null) {
            //target.getClass().getMethod(methodName, parameterTypes);
            method = targetClass.getMethod(methodName, parameterTypes);
            methodCache.put(keyPrefix + methodName, method);
        }
        return method;
    }

}
