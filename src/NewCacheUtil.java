import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NewCacheUtil {

    public static <T> T cache(T obj) {
        Class<?> clazz = obj.getClass();
        Set<String> cachedMethods = new HashSet<>();

        if (clazz.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = clazz.getAnnotation(Cache.class);
            cachedMethods.addAll(Arrays.asList(cacheAnnotation.value()));
        }

        Class<?>[] interfaces = clazz.getInterfaces();

        if (interfaces.length > 0) {
            return (T) Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    interfaces,
                    new NewCacheInvocationHandler(obj, cachedMethods)
            );
        } else {
            return (T) Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                    new NewCacheInvocationHandler(obj, cachedMethods)
            );
        }
    }
}
