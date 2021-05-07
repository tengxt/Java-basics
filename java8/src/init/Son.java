package init;

/**
 * 类初始化过程
 * 1. 一个类要创建实例需要先加载并初始化该类
 *    main 方法所在的类需要先加载和初始化
 * 2. 一个子类要初始化需要先初始化父类
 * 3. 一个类初始化就是执行 <clinit>() 方法
 *    <clinit>() 方法由静态类变量显示赋值代码和静态代码块组成
 *    类变量显示赋值代码和静态代码块代码从上到下顺序执行
 *    <clinit>()方法只执行一次
 */
public class Son extends Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(6)");
    }

    Son() {
        System.out.print("(7)");
    }

    {
        System.out.print("(8)");
    }

    @Override
    public int test() {
        System.out.print("(9)");
        return 1;
    }

    public static int method() {
        System.out.print("(10)");
        return 1;
    }

    public static void main(String[] args) {
        Son s1 = new Son();
        System.out.println();
        Son s2 = new Son();
    }

}
