package bruno.udacity.com.studentguardian.model;

/**
 * Created by BPardini on 14/10/2016.
 */

public class Grade {

    private int codeEvaluation;
    private int codeTypeEvaluation;
    private double grade;

    public int getCodeEvaluation() {
        return codeEvaluation;
    }

    public void setCodeEvaluation(int codeEvaluation) {
        this.codeEvaluation = codeEvaluation;
    }

    public int getCodeTypeEvaluation() {
        return codeTypeEvaluation;
    }

    public void setCodeTypeEvaluation(int codeTypeEvaluation) {
        this.codeTypeEvaluation = codeTypeEvaluation;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
