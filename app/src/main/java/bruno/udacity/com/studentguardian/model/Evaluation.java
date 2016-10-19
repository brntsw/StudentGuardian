package bruno.udacity.com.studentguardian.model;

/**
 * Created by BPardini on 19/10/2016.
 */

public class Evaluation {

    private int codeSubject;
    private TypeEvaluation typeEvaluation;
    private String description;
    private String date;

    public TypeEvaluation getTypeEvaluation() {
        return typeEvaluation;
    }

    public void setTypeEvaluation(TypeEvaluation typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
