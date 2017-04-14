package code.vera.myblog.bean;

/**
 * Created by vera on 2017/4/10 0010.
 */

public class ApiExceptionBean {
    private String error;
    private String error_code;
    private String status;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
