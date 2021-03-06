package bruno.udacity.com.studentguardian.model;

import java.io.Serializable;

/**
 * Created by BPardini on 14/10/2016.
 */

public class User implements Serializable {

    private String email;
    private String password;
    private String name;
    private String dateBirth;
    private int logged;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getLogged() {
        return logged;
    }

    public void setLogged(int logged) {
        this.logged = logged;
    }
}
