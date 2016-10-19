package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.adapter.SubjectsAdapter;
import bruno.udacity.com.studentguardian.model.Subject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 19/10/2016.
 */

public class FragmentSubjects extends Fragment {

    @BindView(R.id.recycler_grades)
    RecyclerView recyclerSubjects;

    private SubjectsAdapter adapter;
    private List<Subject> subjects;

    private Unbinder unbinder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Activity activity = getActivity();
        activity.setTitle(R.string.description_subjects);

        View view = inflater.inflate(R.layout.fragment_subjects, container, false);
        unbinder = ButterKnife.bind(this, view);

        subjects = new ArrayList<>();

        return view;
    }

    public void onStart(){
        super.onStart();

        addMockData();

        //Fill the Recycler view with the subjects
        adapter = new SubjectsAdapter(subjects);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerSubjects.setLayoutManager(manager);
        recyclerSubjects.setItemAnimator(new DefaultItemAnimator());
        recyclerSubjects.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addMockData(){
        Subject subject1 = new Subject();
        subject1.setCode(1);
        subject1.setName("English");
        subjects.add(subject1);

        Subject subject2 = new Subject();
        subject2.setCode(2);
        subject2.setName("Mathematics");
        subjects.add(subject2);

        Subject subject3 = new Subject();
        subject3.setCode(3);
        subject3.setName("History");
        subjects.add(subject3);

        Subject subject4 = new Subject();
        subject4.setCode(4);
        subject4.setName("Chemistry");
        subjects.add(subject4);

        Subject subject5 = new Subject();
        subject5.setCode(5);
        subject5.setName("Physics");
        subjects.add(subject5);

        Subject subject6 = new Subject();
        subject6.setCode(6);
        subject6.setName("Geography");
        subjects.add(subject6);
    }

}
