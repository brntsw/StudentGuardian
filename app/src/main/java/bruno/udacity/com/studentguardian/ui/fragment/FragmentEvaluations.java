package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.adapter.EvaluationsAdapter;
import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.Evaluation;
import bruno.udacity.com.studentguardian.model.TypeEvaluation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 20/10/2016.
 */

public class FragmentEvaluations extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "FragmentEvaluations";

    @BindView(R.id.recycler_evaluations)
    RecyclerView recyclerEvaluations;

    @BindView(R.id.tv_overall_grade)
    TextView tvOverallGrade;

    @BindView(R.id.tv_student_status)
    TextView tvStudentStatus;

    private Unbinder unbinder;
    private int codeSubject;
    private EvaluationsAdapter adapter;
    private List<Evaluation> evaluations;

    private static final int EVALUATION_LOADER = 0;

    private static final String[] EVALUATION_COLUMNS = {
            StudentGuardianContract.EvaluationEntry.COLUMN_CODE,
            StudentGuardianContract.EvaluationEntry.COLUMN_CODE_SUBJECT,
            StudentGuardianContract.EvaluationEntry.COLUMN_NAME,
            StudentGuardianContract.EvaluationEntry.COLUMN_DESCRIPTION,
            StudentGuardianContract.EvaluationEntry.COLUMN_DATE,
            StudentGuardianContract.EvaluationEntry.COLUMN_GRADE,
            StudentGuardianContract.EvaluationEntry.COLUMN_TYPE_EVALUATION
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Activity activity = getActivity();
        activity.setTitle(R.string.description_evaluations);

        View view = inflater.inflate(R.layout.fragment_evaluations, container, false);
        unbinder = ButterKnife.bind(this, view);

        evaluations = new ArrayList<>();

        Bundle bundle = getArguments();
        codeSubject = bundle.getInt(getString(R.string.bundle_code_subject));

        getLoaderManager().restartLoader(EVALUATION_LOADER, null, this);

        return view;
    }

    public void onStart(){
        super.onStart();
    }

    public void onDestroy(){
        super.onDestroy();

        unbinder.unbind();
    }

    public void setStudentOverall(){
        double overallGrade = 0d;
        int countGrades = 0;

        for(Evaluation evaluation : evaluations){
            overallGrade += evaluation.getGrade();
            countGrades++;
        }

        overallGrade = overallGrade / countGrades;
        DecimalFormat df = new DecimalFormat("#.00");

        tvOverallGrade.setText(String.valueOf(df.format(overallGrade)));

        if(overallGrade < 6){
            tvOverallGrade.setTextColor(ContextCompat.getColor(getActivity(), R.color.gravity_urgent));
            tvStudentStatus.setText(getString(R.string.student_pass));
        }
        else{
            tvOverallGrade.setTextColor(ContextCompat.getColor(getActivity(), R.color.gravity_not_serious));
            tvStudentStatus.setText(getString(R.string.student_not_pass));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                StudentGuardianContract.EvaluationEntry.CONTENT_URI,
                EVALUATION_COLUMNS,
                "code_subject = ?",
                new String[]{String.valueOf(codeSubject)},
                StudentGuardianContract.EvaluationEntry.COLUMN_NAME
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null){
            while(data.moveToNext()){
                int codeTypeEvaluation = data.getInt(data.getColumnIndex(StudentGuardianContract.EvaluationEntry.COLUMN_TYPE_EVALUATION));
                Cursor cursorTypeEvaluation = getActivity().getContentResolver().query(StudentGuardianContract.TypeEvaluationEntry.CONTENT_URI, null, "code = ?", new String[]{String.valueOf(codeTypeEvaluation)}, null);

                if(cursorTypeEvaluation != null && cursorTypeEvaluation.moveToNext()){
                    TypeEvaluation typeEvaluation = new TypeEvaluation();
                    typeEvaluation.setCode(cursorTypeEvaluation.getInt(cursorTypeEvaluation.getColumnIndex(StudentGuardianContract.TypeEvaluationEntry.COLUMN_CODE)));
                    typeEvaluation.setType(cursorTypeEvaluation.getString(cursorTypeEvaluation.getColumnIndex(StudentGuardianContract.TypeEvaluationEntry.COLUMN_TYPE)));

                    Evaluation evaluation = new Evaluation();
                    evaluation.setCodeSubject(data.getInt(data.getColumnIndex(StudentGuardianContract.EvaluationEntry.COLUMN_CODE_SUBJECT)));
                    evaluation.setTypeEvaluation(typeEvaluation);
                    evaluation.setDescription(data.getString(data.getColumnIndex(StudentGuardianContract.EvaluationEntry.COLUMN_DESCRIPTION)));
                    evaluation.setDate(data.getString(data.getColumnIndex(StudentGuardianContract.EvaluationEntry.COLUMN_DATE)));
                    evaluation.setGrade(data.getDouble(data.getColumnIndex(StudentGuardianContract.EvaluationEntry.COLUMN_GRADE)));

                    evaluations.add(evaluation);

                    cursorTypeEvaluation.close();
                }
            }
        }

        adapter = new EvaluationsAdapter(evaluations);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerEvaluations.setLayoutManager(manager);
        recyclerEvaluations.setItemAnimator(new DefaultItemAnimator());
        recyclerEvaluations.setAdapter(adapter);

        setStudentOverall();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
