package bruno.udacity.com.studentguardian.model;

/**
 * Created by BPardini on 21/10/2016.
 */

public class ContentClass {

    private int codeSubject;
    private String content;
    private String date;

    public int getCodeSubject() {
        return codeSubject;
    }

    public void setCodeSubject(int codeSubject) {
        this.codeSubject = codeSubject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
