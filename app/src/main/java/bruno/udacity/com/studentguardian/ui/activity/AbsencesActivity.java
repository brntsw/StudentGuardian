package bruno.udacity.com.studentguardian.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.ui.fragment.FragmentAbsences;
import bruno.udacity.com.studentguardian.ui.fragment.FragmentSubjects;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BPardini on 19/10/2016.
 */

public class AbsencesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentAbsences fragAbsences;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absences);

        ButterKnife.bind(this);

        setupComponents(savedInstanceState);

        setSupportActionBar(toolbar);
    }

    protected void onResume(){
        super.onResume();

        toolbar.setTitle(getString(R.string.description_absences));
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

        fragAbsences = new FragmentAbsences();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragAbsences)
                .commit();
    }

}
