package test.review;

public interface InterfaceTest {

    default String setTime(String newTime) {
        return "time set " + newTime;
    }

}
