package test.jvm;

import com.sun.net.ssl.internal.ssl.Provider;
import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;

public class ClassLoaderTest1 {
    public static void main(String[] args) {
        System.out.println("*****启动类加载器*****");
        // 获取BootstrapClassLoader能够加载的api的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL elements : urLs) {
            System.out.println(elements.toExternalForm());
        }
        // 从获取到的路径中随意选择一个类，查看这个类的加载器是什么：引导类加载器
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader); // null


        System.out.println("*****扩展类加载器*****");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }
        // 从获取到的路径中随意选择一个类，查看这个类的加载器是什么：扩展类加载器
        ClassLoader classLoader1 = CurveDB.class.getClassLoader();
        System.out.println(classLoader1); // sun.misc.Launcher$ExtClassLoader@5e2de80c
    }
}
