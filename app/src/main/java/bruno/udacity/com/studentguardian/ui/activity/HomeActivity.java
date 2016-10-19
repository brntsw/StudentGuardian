package bruno.udacity.com.studentguardian.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import bruno.udacity.com.studentguardian.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onResume(){
        super.onResume();

        handleListeners();
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
