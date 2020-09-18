package tengxt;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 分支/合并框架  工作窃取
 *      将一个大任务，进行拆分(fork)成 若干个小任务（拆到不可再拆时），再将一个个的小任务运算的结果进 行 join 汇总
 */
public class ForkJoinPoolTest {
    public static void main(String[] args) { // 22461
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        // RecursiveTask<V> extends ForkJoinTask<V>
        ForkJoinTask<Long> task = new ForkJoinTest(0L, 50000000000L);
        Long sum = pool.invoke(task);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

    @Test
    public void test2() { // 19287
        Instant start = Instant.now();
        long sum = 0;
        for (long i = 0; i <= 50000000000L; i++) {
            sum += i;
        }
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

    @Test
    public void test3() { // 14348
        //  对ForkJoin的改进
        Instant start = Instant.now();
        // rangeClosed 生成连续的数
        long sum1 = LongStream.rangeClosed(0, 50000000000L)
                .parallel()
                .reduce(0, Long::sum); // 第二个参数 是函数式接口LongBinaryOperator
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}

class ForkJoinTest extends RecursiveTask<Long> {

    // Recursive 递归
    // RecursiveAction 没有返回值
    // RecursiveTask 有返回值

    private long start;
    private long end;

    private static final long THRESHOLD = 10000;

    public ForkJoinTest(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinTest left = new ForkJoinTest(start, middle);
            left.fork(); // 拆分子任务，同时压入线程队列
            ForkJoinTest right = new ForkJoinTest(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
