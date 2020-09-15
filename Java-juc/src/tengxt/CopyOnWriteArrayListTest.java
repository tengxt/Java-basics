package tengxt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        HelloDemo demo = new HelloDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(demo).start();
        }
    }

}

/**
 * CopyOnWriteArrayList 写入并复制，添加操作多时，效率低，因为每次添加时都会进行复制，开销很大
 * 并发迭代操作多时可以选择
 */
class HelloDemo implements Runnable {

    // 会出现并发修改异常 ConcurrentModificationException
//    private static List<String> list = Collections.synchronizedList(new ArrayList<>());
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AAA");
        list.add("BBB");
        list.add("CCC");
    }


    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());

            list.add("AA");
        }
    }
}
