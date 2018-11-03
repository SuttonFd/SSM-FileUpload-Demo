package xin.sutton.test.common;

/**
 * TODO...
 *
 * @author codingZhengsz
 * @since 2018-10-24 14:22
 **/
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    USER_NOT_FOUNT(-1002,"用户不存在"),
    USER_NOT_LOGIN(-1003,"用户未登录"),
    USER_IS_LOGIN(-1004,"用户已经登录")
    ;

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
