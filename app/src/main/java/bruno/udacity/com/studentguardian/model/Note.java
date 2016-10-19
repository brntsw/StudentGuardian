package bruno.udacity.com.studentguardian.model;

/**
 * Created by BPardini on 19/10/2016.
 */

public class Note {

    private int id;
    private int subjectId;
    private String title;
    private String description;
    private String date;
    private String pathEvidenceImage;
    private int colorGravity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathEvidenceImage() {
        return pathEvidenceImage;
    }

    public void setPathEvidenceImage(String pathEvidenceImage) {
        this.pathEvidenceImage = pathEvidenceImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColorGravity() {
        return colorGravity;
    }

    public void setColorGravity(int colorGravity) {
        this.colorGravity = colorGravity;
    }
}
