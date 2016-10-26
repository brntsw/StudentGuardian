package bruno.udacity.com.studentguardian.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.User;
import bruno.udacity.com.studentguardian.remote.task.LoginTask;
import bruno.udacity.com.studentguardian.utils.LoginUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.coordinator_login)
    CoordinatorLayout coordinatorLogin;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.title_activity_login));
    }

    public void onResume(){
        super.onResume();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUtils utils = new LoginUtils(coordinatorLogin);

                if(utils.validateLoginFields(editEmail, editPassword)){
                    User user = new User();
                    user.setEmail(editEmail.getText().toString().trim());
                    user.setPassword(editPassword.getText().toString().trim());

                    new LoginTask(LoginActivity.this, user, coordinatorLogin).execute();

                    //LoginSync.initializeSyncAdapter(LoginActivity.this);

                    /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                    LoginActivity.this.finish();*/
                }
            }
        });
    }
}
