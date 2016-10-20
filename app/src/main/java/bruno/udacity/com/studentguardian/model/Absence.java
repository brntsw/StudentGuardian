package bruno.udacity.com.studentguardian.model;

/**
 * Created by BPardini on 20/10/2016.
 */

public class Absence {

    private int codeSubject;
    private int countAbsences;

    public int getCodeSubject() {
        return codeSubject;
    }

    public void setCodeSubject(int codeSubject) {
        this.codeSubject = codeSubject;
    }

    public int getCountAbsences() {
        return countAbsences;
    }

    public void setCountAbsences(int countAbsences) {
        this.countAbsences = countAbsences;
    }
}
