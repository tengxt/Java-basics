package single;

/**
 * 饿汉式：
 *  在类初始化时直接创建实例对象，不管你是否需要这个对象都会创建
 *
 *  1. 构造器私有化
 *  2. 自行创建，并且用静态变量保存
 *  2. 向外提供这个实例
 *  4. 强调这是一个单例，用 final 修饰
 */
public class Singleton1 {

    public final static Singleton1 INSTANCE = new Singleton1();

    private Singleton1(){

    }

}
