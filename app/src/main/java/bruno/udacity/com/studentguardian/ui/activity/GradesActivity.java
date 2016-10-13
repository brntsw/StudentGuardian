package bruno.udacity.com.studentguardian.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import bruno.udacity.com.studentguardian.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GradesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        ButterKnife.bind(this);
    }

    protected void onResume(){
        super.onResume();

        toolbar.setTitle(getString(R.string.description_grades));
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
    }
}
