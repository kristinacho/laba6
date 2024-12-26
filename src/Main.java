public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        A original = new A("first");
        AInterface cached = CacheUtil.cache(original);

        System.out.println("Задание 1.3. Рефлексия. Кеширование.");

        // 1 вызов метода
        System.out.println(cached.cacheTest());
        // Изменяем состояние объекта
        original.stringField = "second";
        // 2 вызов метода
        System.out.println(cached.cacheTest());
        // (возвращается закэшированное значение)
        System.out.println(cached.cacheTest());

        System.out.println("\nЗадание 3.3.Обработка аннотаций.");
        A original1 = new A("first");
        AInterface cachedd = NewCacheUtil.cache(original1);
        System.out.println(cachedd.cacheTest());
        original1.stringField = "second";
        System.out.println(cachedd.cacheTest());
        System.out.println(cachedd.cacheTest());
    }
}
