package test.review;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test03 {
    public static void main(String[] args) {
        try {
            // 获取TargetObject类的Class对象并且创建TargetObject类实例
            Class<?> targetClass = Class.forName("test.review.TargetObject");
            TargetObject targetObject = (TargetObject) targetClass.newInstance();

            // 获取所有类中所有定义的方法
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
            }

            // 获取指定方法并调用
            Method publicMethod = targetClass.getDeclaredMethod("publicMethod", String.class);
            publicMethod.invoke(targetObject, "TargetObject");
            // 获取指定参数并对参数进行修改
            Field field = targetClass.getDeclaredField("value");
            // 为了对类中的参数进行修改我们取消安全检查
            field.setAccessible(true);
            field.set(targetObject, "TargetObject");

            // 调用 private 方法
            Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
            // 为了调用private方法我们取消安全检查
            privateMethod.setAccessible(true);
            privateMethod.invoke(targetObject);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
