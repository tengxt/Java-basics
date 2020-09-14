package test.lambda1;

@FunctionalInterface
public interface MyFunction2<T, R> {
    public Long getValue(T t, R r);
}
