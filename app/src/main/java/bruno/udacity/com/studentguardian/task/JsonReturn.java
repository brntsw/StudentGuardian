package bruno.udacity.com.studentguardian.task;

/**
 * Created by BPardini on 25/10/2016.
 */

public class JsonReturn {

    public static final int SUCCESS_RETURN = 200;
    public static final int NO_RESPONSE_SUCCESS_RETURN = 204;
    public static final int ERROR = 500;
    public static final int NOT_FOUND = 404;
    public static final int NOT_AVAILABLE = 503;

    private int returnStatus;
    private String resultString;

    public JsonReturn(int returnStatus, String resultString) {
        this.returnStatus = returnStatus;
        this.resultString = resultString;
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }
}
