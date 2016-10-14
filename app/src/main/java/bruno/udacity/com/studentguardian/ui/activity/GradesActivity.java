package bruno.udacity.com.studentguardian.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.adapter.SubjectsAdapter;
import bruno.udacity.com.studentguardian.model.Subject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GradesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_grades)
    RecyclerView recyclerGrades;

    private SubjectsAdapter adapter;
    private List<Subject> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        ButterKnife.bind(this);

        subjects = new ArrayList<>();
    }

    protected void onResume(){
        super.onResume();

        toolbar.setTitle(getString(R.string.description_grades));
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);

        addMockData();

        //Fill the Recycler view with the subjects
        adapter = new SubjectsAdapter(subjects);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerGrades.setLayoutManager(manager);
        recyclerGrades.setItemAnimator(new DefaultItemAnimator());
        recyclerGrades.setAdapter(adapter);
    }

    private void addMockData(){
        Subject subject1 = new Subject();
        subject1.setCode(1);
        subject1.setName("English");
        subjects.add(subject1);

        Subject subject2 = new Subject();
        subject1.setCode(2);
        subject1.setName("Mathematics");
        subjects.add(subject2);

        Subject subject3 = new Subject();
        subject1.setCode(3);
        subject1.setName("History");
        subjects.add(subject3);

        Subject subject4 = new Subject();
        subject1.setCode(4);
        subject1.setName("Chemistry");
        subjects.add(subject4);

        Subject subject5 = new Subject();
        subject1.setCode(5);
        subject1.setName("Physics");
        subjects.add(subject5);

        Subject subject6 = new Subject();
        subject1.setCode(6);
        subject1.setName("Geography");
        subjects.add(subject6);
    }
}
