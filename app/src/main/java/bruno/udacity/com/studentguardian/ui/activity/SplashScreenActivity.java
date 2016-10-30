package bruno.udacity.com.studentguardian.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.User;

public class SplashScreenActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Cursor cursor = getContentResolver().query(StudentGuardianContract.UserEntry.CONTENT_URI, null, "logged = 1", null, StudentGuardianContract.UserEntry.COLUMN_NAME);
        if(cursor != null) {
            if (cursor.moveToNext()) {
                User user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.UserEntry.COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.UserEntry.COLUMN_PASSWORD)));
                user.setDateBirth(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.UserEntry.COLUMN_DATE_BIRTH)));
                user.setName(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.UserEntry.COLUMN_NAME)));
                user.setLogged(cursor.getInt(cursor.getColumnIndex(StudentGuardianContract.UserEntry.COLUMN_LOGGED)));

                intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                intent.putExtra(getString(R.string.bundle_user), user);
            }
            else{
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            }

            cursor.close();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                    startActivity(intent);
                }
            }
        }).start();
    }
}
