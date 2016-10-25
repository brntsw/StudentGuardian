package bruno.udacity.com.studentguardian.utils;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.widget.EditText;

import bruno.udacity.com.studentguardian.R;

/**
 * Created by Bruno on 12/10/2016.
 */
public class LoginUtils {

    private CoordinatorLayout layout;

    public LoginUtils(CoordinatorLayout layout){
        this.layout = layout;
    }

    public static boolean isFieldEmpty(EditText editText){
        return editText.toString().equals("");
    }

    public boolean validateLoginFields(EditText editEmail, EditText editPassword){
        if(editEmail.getText().toString().trim().equals("")){
            Snackbar.make(layout, R.string.type_email, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()){
            Snackbar.make(layout, R.string.provide_valid_email, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(editPassword.getText().toString().trim().equals("")){
            Snackbar.make(layout, R.string.type_password, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
