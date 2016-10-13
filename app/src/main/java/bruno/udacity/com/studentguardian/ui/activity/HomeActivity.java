package bruno.udacity.com.studentguardian.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.tv_grades)
    TextView tvGrades;

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

        tvGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Grades", Toast.LENGTH_SHORT).show();
            }
        });

        tvAbsences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Absences", Toast.LENGTH_SHORT).show();
            }
        });

        tvSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Subjects", Toast.LENGTH_SHORT).show();
            }
        });

        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Notes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
