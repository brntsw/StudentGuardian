package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
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
import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
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

        Cursor cursor = getActivity().getContentResolver().query(StudentGuardianContract.SubjectEntry.CONTENT_URI, null, null, null, StudentGuardianContract.SubjectEntry.COLUMN_NAME);
        if(cursor != null){
            while(cursor.moveToNext()){
                Subject subject = new Subject();
                subject.setCode(cursor.getInt(cursor.getColumnIndex(StudentGuardianContract.SubjectEntry.COLUMN_CODE)));
                subject.setName(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.SubjectEntry.COLUMN_NAME)));

                subjects.add(subject);
            }

            cursor.close();
        }

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

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        Subject subject = adapter.getItem(position);

        if(args != null){
            String activity = args.getString(getString(R.string.bundle_activity));

            if(activity != null) {
                args.putInt(getString(R.string.bundle_code_subject), subject.getCode());
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
