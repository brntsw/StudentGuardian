package bruno.udacity.com.studentguardian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.User;
import bruno.udacity.com.studentguardian.remote.task.LoginTask;
import bruno.udacity.com.studentguardian.utils.LoginUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static String TAG = "LoginActivity";
    private final int RC_SIGN_IN = 1;

    @BindView(R.id.coordinator_login)
    CoordinatorLayout coordinatorLogin;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.bt_login)
    SignInButton btLogin;

    private GoogleApiClient mGoogleApiClient;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.title_activity_login));

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        btLogin.setSize(SignInButton.SIZE_STANDARD);
        btLogin.setScopes(options.getScopeArray());
    }

    public void onResume(){
        super.onResume();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUtils utils = new LoginUtils(coordinatorLogin);

                if(utils.validateLoginFields(editEmail, editPassword)){
                    user = new User();
                    user.setEmail(editEmail.getText().toString().trim());
                    user.setPassword(editPassword.getText().toString().trim());

                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(intent, RC_SIGN_IN);

                    //DataSyncAdapter.initializeSyncAdapter(LoginActivity.this);

                    /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                    LoginActivity.this.finish();*/
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            new LoginTask(LoginActivity.this, user, coordinatorLogin).execute();
        }
        else{
            Snackbar.make(coordinatorLogin, R.string.user_not_found, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, connectionResult.getErrorMessage());
    }
}
