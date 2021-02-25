package test.review;

import java.util.Objects;

public class Test02 {
    public static void main(String[] args) {
        // ORDERED
        System.out.println(Pizza.PizzaStatus.ORDERED.name());
        // ORDERED
        System.out.println(Pizza.PizzaStatus.ORDERED);
        // class java.lang.String
        System.out.println(Pizza.PizzaStatus.ORDERED.name().getClass());
        // class test.review.Pizza$PizzaStatus
        System.out.println(Pizza.PizzaStatus.ORDERED.getClass());

        // 使用 == 比较枚举类型
        Pizza pizza = new Pizza();
        // 报错：NullPointerException
        /*if (pizza.getStatus().equals(Pizza.PizzaStatus.DELIVERED)) {
            System.out.println(true);
        }

        if (Objects.equals(pizza.getStatus(), Pizza.PizzaStatus.DELIVERED)){}
        if (pizza.getStatus() == Pizza.PizzaStatus.DELIVERED){}*/


        Integer days = pizza.getDeliveryTimeInDays(Pizza.PizzaStatus.DELIVERED);
        System.out.println(days);


        // 调用短信验证码的时候可能有几种不同的用途
        System.out.println(PinType.FORGET_PASSWORD.getCode());
        System.out.println(PinType.FORGET_PASSWORD.getMessage());
        System.out.println(PinType.FORGET_PASSWORD.toString());
    }
}
