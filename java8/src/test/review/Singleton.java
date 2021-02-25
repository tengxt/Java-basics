package test.review;

/**
 * 单例模式 不仅具有延迟初始化的好处，而且由 JVM 提供了对线程安全的支持。
 */
public class Singleton {
    /**
     * 声明为 private 避免调用默认构造方法创建对象
     */
    private Singleton() {

    }

    /**
     * 声明为 private 表明静态内部该类只能在该 Singleton 类中被访问
     */
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getUniqueInstance() {
        return SingletonHolder.INSTANCE;
    }
}
