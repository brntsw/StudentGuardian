package bruno.udacity.com.studentguardian.model;

/**
 * Created by BPardini on 19/10/2016.
 */

public class TypeEvaluation {

    //The types of evaluation are:
    /**
     *
      - WRITTEN TEST
      - ALTERNATIVE TEST
      - ORAL TEST
      - HOMEWORK
     *
     * */

    private int code;
    private int codeSubject;
    private String nameEvaluation;
    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCodeSubject() {
        return codeSubject;
    }

    public void setCodeSubject(int codeSubject) {
        this.codeSubject = codeSubject;
    }

    public String getNameEvaluation() {
        return nameEvaluation;
    }

    public void setNameEvaluation(String nameEvaluation) {
        this.nameEvaluation = nameEvaluation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
