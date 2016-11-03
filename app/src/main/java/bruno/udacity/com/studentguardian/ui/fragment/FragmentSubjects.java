package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class FragmentSubjects extends Fragment implements OnRecyclerViewItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.recycler_grades)
    RecyclerView recyclerSubjects;

    private SubjectsAdapter adapter;
    private List<Subject> subjects;

    private Unbinder unbinder;
    private Bundle args;

    private static final int SUBJECT_LOADER = 0;

    private static final String[] SUBJECT_COLUMNS = {
            StudentGuardianContract.SubjectEntry.COLUMN_CODE,
            StudentGuardianContract.StudentEntry.COLUMN_NAME
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Activity activity = getActivity();
        activity.setTitle(R.string.description_subjects);

        View view = inflater.inflate(R.layout.fragment_subjects, container, false);
        unbinder = ButterKnife.bind(this, view);

        subjects = new ArrayList<>();

        args = getArguments();

        getLoaderManager().restartLoader(SUBJECT_LOADER, null, this);

        return view;
    }

    public void onStart(){
        super.onStart();
    }

    public void onActivityCreated(Bundle savedInstanceState){
        getLoaderManager().initLoader(SUBJECT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
            getActivity(),
                StudentGuardianContract.SubjectEntry.CONTENT_URI,
                SUBJECT_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null){
            while(data.moveToNext()){
                Subject subject = new Subject();
                subject.setCode(data.getInt(data.getColumnIndex(StudentGuardianContract.SubjectEntry.COLUMN_CODE)));
                subject.setName(data.getString(data.getColumnIndex(StudentGuardianContract.SubjectEntry.COLUMN_NAME)));

                subjects.add(subject);

                //Fill the Recycler view with the subjects
                adapter = new SubjectsAdapter(subjects);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
                recyclerSubjects.setLayoutManager(manager);
                recyclerSubjects.setItemAnimator(new DefaultItemAnimator());
                recyclerSubjects.setAdapter(adapter);
                adapter.setOnItemClickListener(this);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
