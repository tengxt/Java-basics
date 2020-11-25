package tengxt.util;

public class VerifyCode {
    private String code;

    private byte[] imgBytes;

    private long expireTime;

    public VerifyCode() {

    }

    public VerifyCode(String code, byte[] imgBytes, long expireTime) {
        this.code = code;
        this.imgBytes = imgBytes;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
