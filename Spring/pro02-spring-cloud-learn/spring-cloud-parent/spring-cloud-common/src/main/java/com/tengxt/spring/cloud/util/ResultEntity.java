package com.tengxt.spring.cloud.util;

public class ResultEntity<T> {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILD = "FAILD";
    private static final String NO_MESSAGE = "NO_MESSAGE";
    private static final String NO_DATA = "NO_DATA";

    private String result;
    private String message;
    private T data;

    public ResultEntity() {

    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 操作成功，不需要返回数据
     * @return
     */
    public static ResultEntity<String> successWithoutData(){
        return new ResultEntity<String>(SUCCESS, NO_MESSAGE, NO_DATA);
    }

    /**
     * 操作成功，需要返回数据
     * @param data
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> successWithData(E data){
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE,data);
    }

    /**
     * 操作失败，返回错误消息
     * @param message
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> faild(String message){
        return new ResultEntity<E>(FAILD,message,null);
    }
}
