package test.instant;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description TODO 传统的时间格式化，在多线程下是不安全的
 * @Date 2020/7/30 17:04
 * @Created by tengxt
 */
public class TestSimpleDateFormat {
    public static void main(String[] args) throws Exception {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // java 8 之前
        /*Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20200730");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(50);

        List<Future<Date>> results = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> result : results) {
            System.out.println(result.get());
        }

        pool.shutdown();*/

        // java 8 Instant

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> task = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20200730", dtf);
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(50);

        List<Future<LocalDate>> results = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            results.add(pool.submit(task));
        }

        for (Future<LocalDate> result : results) {
            System.out.println(result.get());
        }

        pool.shutdown();
    }
}
