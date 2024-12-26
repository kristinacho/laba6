@Default(value = String.class)
@ToString(value = ToString.ToStringValue.YES)
@Validate(value = {Integer.class, Double.class})
@Two(first = "example", second = 42)
@Cache(value = {"key1", "key2"})
public class Example {

    @Default(value = Integer.class)
    private int defaultField;

    @Invoke
    public static void itsWorked() {
        System.out.println("Аннотация Invoke работает");
    }

    @Default(value = DefaultExample.class)
    public static class DefaultExample {
        @Default(value = String.class)
        private String defaultField;

        public static void example() {
            System.out.println("Аннотация Default работает для класса и поля");
        }
    }

    @ToString(value = ToString.ToStringValue.NO)
    public static class ToStringExample {
        @ToString()
        private String defaultField;

        public static void example() {
            System.out.println("Аннотация ToString работает для класса и поля");
        }
    }

    @Validate(value = {String.class, ValidateExample.class})
    public static class ValidateExample {
        public static void example() {
            System.out.println("Аннотация Validate работает для класса и аннотации");
        }
    }

    @Validate(value = {Double.class, Long.class})
    @interface CustomAnnotation {
    }

    public static void main(String[] args) {
        // Invoke
        itsWorked();
        System.out.println();

        // Default
        DefaultExample.example();
        Class<?> classValue = DefaultExample.class.getAnnotation(Default.class).value();
        System.out.println("Значение аннотации для класса: " + classValue.getName());
        Class<?> fieldValue = DefaultExample.class.getDeclaredFields()[0].getAnnotation(Default.class).value();
        System.out.println("Значение аннотации для поля: " + fieldValue.getName());
        System.out.println();

        // ToString
        ToStringExample.example();
        ToString.ToStringValue toStringClassValue = ToStringExample.class.getAnnotation(ToString.class).value();
        System.out.println("Значение аннотации для класса: " + toStringClassValue);
        ToString.ToStringValue toStringFieldValue = ToStringExample.class.getDeclaredFields()[0].getAnnotation(ToString.class).value();
        System.out.println("Значение аннотации для поля: " + toStringFieldValue);
        System.out.println();

        // Validate
        ValidateExample.example();
        Class<?>[] classArray = ValidateExample.class.getAnnotation(Validate.class).value();
        System.out.println("Значение аннотации для класса: " + result(classArray));
        Class<?>[] annotationArray = CustomAnnotation.class.getAnnotation(Validate.class).value();
        System.out.println("Значение аннотации для аннотации: " + result(annotationArray));
        System.out.println();

        // Two
        if (Example.class.isAnnotationPresent(Two.class)) {
            Two twoAnnotation = Example.class.getAnnotation(Two.class);
            System.out.println("Аннотация Two работает для класса");
            System.out.println("Значение аннотации Two: first = " + twoAnnotation.first() + ", second = " + twoAnnotation.second());
        }
        System.out.println();

        // Cache
        if (Example.class.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = Example.class.getAnnotation(Cache.class);
            System.out.println("Аннотация Cache работает для класса");
            System.out.println("Значение аннотации Cache: " + result(cacheAnnotation.value()));
        }
        System.out.println();
    }

    public static String result(String[] stringArray) {
        StringBuilder strings = new StringBuilder();
        for (String str : stringArray) {
            strings.append(str).append("  ");
        }
        return strings.toString();
    }

    public static String result(Class<?>[] classArray) {
        StringBuilder classes = new StringBuilder();
        for (Class<?> clazz : classArray) {
            classes.append(clazz.getName()).append("  ");
        }
        return classes.toString();
    }
}
