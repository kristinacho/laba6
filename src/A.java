@Cache(value = {"cacheTest"})
public class A implements AInterface {
    String stringField;

    public A(String str) {
        stringField = str;
    }

    @Override
    public int cacheTest() {
        System.out.println("original method");
        return 42;
    }
}
