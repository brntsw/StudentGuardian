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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.adapter.SubjectsAdapter;
import bruno.udacity.com.studentguardian.model.Subject;
import bruno.udacity.com.studentguardian.ui.activity.listener.OnRecyclerViewItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 19/10/2016.
 */

public class FragmentSubjects extends Fragment implements OnRecyclerViewItemClickListener {

    @BindView(R.id.recycler_grades)
    RecyclerView recyclerSubjects;

    private SubjectsAdapter adapter;
    private List<Subject> subjects;

    private Unbinder unbinder;
    private Bundle args;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Activity activity = getActivity();
        activity.setTitle(R.string.description_subjects);

        View view = inflater.inflate(R.layout.fragment_subjects, container, false);
        unbinder = ButterKnife.bind(this, view);

        subjects = new ArrayList<>();

        args = getArguments();

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
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addMockData(){
        Subject subject1 = new Subject();
        subject1.setCode(100);
        subject1.setName("English");
        subjects.add(subject1);

        Subject subject2 = new Subject();
        subject2.setCode(101);
        subject2.setName("Mathematics");
        subjects.add(subject2);

        Subject subject3 = new Subject();
        subject3.setCode(102);
        subject3.setName("History");
        subjects.add(subject3);

        Subject subject4 = new Subject();
        subject4.setCode(103);
        subject4.setName("Chemistry");
        subjects.add(subject4);

        Subject subject5 = new Subject();
        subject5.setCode(104);
        subject5.setName("Physics");
        subjects.add(subject5);

        Subject subject6 = new Subject();
        subject6.setCode(105);
        subject6.setName("Geography");
        subjects.add(subject6);
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        Subject subject = adapter.getItem(position);

        if(args != null){
            String activity = args.getString("activity");

            if(activity != null) {
                args.putInt("codeSubject", subject.getCode());
                switch (activity) {
                    case "evaluations":
                        FragmentEvaluations fragEvaluations = new FragmentEvaluations();
                        fragEvaluations.setArguments(args);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragEvaluations, FragmentEvaluations.TAG)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "subjectdetails":
                        FragmentSubjectDetails fragSubjectDetails = new FragmentSubjectDetails();
                        fragSubjectDetails.setArguments(args);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragSubjectDetails, FragmentSubjectDetails.TAG)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        }
    }
}
