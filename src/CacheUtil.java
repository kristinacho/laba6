import java.lang.reflect.Proxy;

public class CacheUtil {

    public static <T> T cache(T obj) {
        Class<?> clazz = obj.getClass();

        // Создаем прокси на основе интерфейсов или публичных методов класса
        Class<?>[] interfaces = clazz.getInterfaces();

        if (interfaces.length > 0) {
            return (T) Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    interfaces,
                    new CacheInvocationHandler(obj)
            );
        } else {
            return (T) Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                    new CacheInvocationHandler(obj)
            );
        }
    }
}
