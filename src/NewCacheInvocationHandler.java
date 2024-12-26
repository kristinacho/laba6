import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NewCacheInvocationHandler implements InvocationHandler {
    private final Object original;
    private final Map<Method, Object> cache = new HashMap<>();
    private final Map<Method, Object> lastState = new HashMap<>();
    private final Set<String> cachedMethods;

    public NewCacheInvocationHandler(Object original, Set<String> cachedMethods) {
        this.original = original;
        this.cachedMethods = cachedMethods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args == null || args.length == 0) {
            if (cachedMethods.isEmpty() || cachedMethods.contains(method.getName())) {
                if (!cache.containsKey(method) || hasStateChanged(method)) {
                    Object result = method.invoke(original, args);
                    cache.put(method, result);
                    lastState.put(method, getCurrentState());
                    return result;
                } else {
                    return cache.get(method);
                }
            }
        }
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
