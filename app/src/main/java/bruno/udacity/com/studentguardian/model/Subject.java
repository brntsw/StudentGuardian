package bruno.udacity.com.studentguardian.model;

/**
 * Created by BPardini on 14/10/2016.
 */

public class Subject {

    //Subject Codes
    //100 - English
    //101 - Mathematics
    //102 - History
    //103 - Chemistry
    //104 - Physics
    //105 - Geography

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
