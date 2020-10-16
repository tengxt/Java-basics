package tengxt.util;

/**
 * 统一 Ajax 请求返回的结果
 * @param <T>
 */
public class ResultEntity<T> {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";

    // 结果（成功/失败）
    private String result;

    // 错误信息
    private String message;

    // 要返回的数据
    private T data;

    /**
     * 请求处理成功且不需要返回数据
     *
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> successWithoutData() {
        return new ResultEntity<E>(SUCCESS, null, null);
    }

    /**
     * 请求处理成功且需要返回数据
     * @param data
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> successWithData(E data) {
        return new ResultEntity<E>(SUCCESS, null, data);
    }

    /**
     * 请求处理失败
     * @param message 错误消息
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity<E>(FAILED, message, null);
    }

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
}
