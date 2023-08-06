package framework.utility.globalConst;

/**
 * @author Prateek Sethi
 */
public enum StatusCode {
    CODE_200(200,"Success Code"),
    CODE_201(201,"Resource Created"),
    CODE_400(400,"Bad Request"),
    CODE_401(401,"Invalid Access Token");

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    StatusCode(int code, String message) {
        this.code=code;
        this.message=message;
    }
}
