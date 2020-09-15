package tengxt;

/**
 * 模拟 CAS 算法
 */
public class CompareAndSwapTest {
    public static void main(String[] args) {
        CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.getValue();
                    boolean swap = cas.CompareAndSet(expectedValue, (int) (Math.random() * 101));
                    System.out.println(swap);
                }
            }).start();
        }
    }
}

class CompareAndSwap {
    private int value;

    // 获取内存值
    public synchronized int getValue() {
        return value;
    }

    // 比较
    public synchronized int CompareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            this.value = newValue;
        }
        return oldValue;
    }

    // 设置
    public synchronized boolean CompareAndSet(int expectedValue, int newValue) {
        return expectedValue == CompareAndSwap(expectedValue, newValue);
    }
}
