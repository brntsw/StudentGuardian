package bruno.udacity.com.studentguardian.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.User;
import bruno.udacity.com.studentguardian.ui.fragment.DialogFragmentAbout;
import bruno.udacity.com.studentguardian.ui.fragment.DialogFragmentEvaluate;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_responsible_name)
    TextView tvResponsibleName;

    @BindView(R.id.tv_student_name)
    TextView tvStudentName;

    @BindView(R.id.tv_evaluations)
    TextView tvEvaluations;

    @BindView(R.id.tv_absences)
    TextView tvAbsences;

    @BindView(R.id.tv_subjects)
    TextView tvSubjects;

    @BindView(R.id.tv_notes)
    TextView tvNotes;

    private Bundle extras;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.app_name));

        setSupportActionBar(toolbar);

        extras = getIntent().getExtras();
    }

    @Override
    protected void onResume(){
        super.onResume();

        handleListeners();

        user = (User) extras.getSerializable("user");

        if(user != null) {
            tvResponsibleName.setText(user.getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_share:
                String shareText = "Download the app StudentGuardian at Google Play clicking on this link: https://play.google.com/store/apps/details?id=br.gov.sp.educacao.leitorresposta";

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            case R.id.action_evaluate:
                showDialogFragmentEvaluate();
                return true;
            case R.id.action_about:
                showDialogFragmentAbout();
                return true;
            case R.id.action_logoff:
                //Updates the user, setting the logged = 0
                ContentValues contentValues = new ContentValues();
                contentValues.put(StudentGuardianContract.UserEntry.COLUMN_LOGGED, 0);

                getContentResolver().update(StudentGuardianContract.UserEntry.CONTENT_URI, contentValues, "email = ?", new String[]{user.getEmail()});

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialogFragmentAbout() {
        DialogFragmentAbout fragmentAbout = new DialogFragmentAbout();
        fragmentAbout.show(getSupportFragmentManager(), DialogFragmentAbout.TAG);
    }

    private void showDialogFragmentEvaluate() {
        DialogFragmentEvaluate fragmentEvaluate = new DialogFragmentEvaluate();
        fragmentEvaluate.show(getSupportFragmentManager(), DialogFragmentEvaluate.TAG);
    }

    private void handleListeners(){
        //Replace a contentholder with the fragment

        tvEvaluations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EvaluationsActivity.class);
                startActivity(intent);
            }
        });

        tvAbsences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AbsencesActivity.class);
                startActivity(intent);
            }
        });

        tvSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubjectsActivity.class);
                startActivity(intent);
            }
        });

        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });
    }
}
