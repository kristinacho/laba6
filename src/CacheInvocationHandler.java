import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CacheInvocationHandler implements InvocationHandler {
    private final Object original;
    private final Map<Method, Object> cache = new HashMap<>();
    private final Map<Method, Object> lastState = new HashMap<>();

    public CacheInvocationHandler(Object original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Проверяем, что метод без параметров
        if (args == null || args.length == 0) {
            // Если метод не закэширован или состояние изменилось
            if (!cache.containsKey(method) || hasStateChanged(method)) {
                Object result = method.invoke(original, args);
                cache.put(method, result);
                lastState.put(method, getCurrentState());
                return result;
            } else {
                // Возвращаем закэшированное значение
                return cache.get(method);
            }
        }
        // Если метод имеет параметры, вызываем его обычным образом
        return method.invoke(original, args);
    }

    private boolean hasStateChanged(Method method) throws IllegalAccessException {
        Object currentState = getCurrentState();
        Object lastMethodState = lastState.get(method);
        return lastMethodState == null || !lastMethodState.equals(currentState);
    }

    private Object getCurrentState() throws IllegalAccessException {
        StringBuilder state = new StringBuilder();
        for (java.lang.reflect.Field field : original.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            state.append(field.get(original));
        }
        return state.toString();
    }
}
