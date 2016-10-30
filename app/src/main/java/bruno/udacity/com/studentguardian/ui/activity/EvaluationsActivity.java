package bruno.udacity.com.studentguardian.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.ui.fragment.FragmentSubjects;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluationsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentSubjects fragSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        ButterKnife.bind(this);

        setupComponents(savedInstanceState);

        setSupportActionBar(toolbar);
    }

    protected void onResume(){
        super.onResume();

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupComponents(Bundle savedInstanceState) {
        if (findViewById(R.id.fragment_container) != null && savedInstanceState != null) {
            return;
        }

        Bundle args = new Bundle();
        args.putString(getString(R.string.bundle_activity), getString(R.string.bundle_evaluations));

        fragSubjects = new FragmentSubjects();
        fragSubjects.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragSubjects)
                .commit();
    }
}
