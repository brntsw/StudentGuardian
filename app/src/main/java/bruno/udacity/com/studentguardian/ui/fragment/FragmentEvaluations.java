package bruno.udacity.com.studentguardian.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
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
import bruno.udacity.com.studentguardian.model.Evaluation;
import bruno.udacity.com.studentguardian.model.TypeEvaluation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 20/10/2016.
 */

public class FragmentEvaluations extends Fragment {

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_evaluations, container, false);
        unbinder = ButterKnife.bind(this, view);

        evaluations = new ArrayList<>();

        Bundle bundle = getArguments();
        codeSubject = bundle.getInt("codeSubject");

        return view;
    }

    public void onStart(){
        super.onStart();

        addMockData();

        adapter = new EvaluationsAdapter(evaluations);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerEvaluations.setLayoutManager(manager);
        recyclerEvaluations.setItemAnimator(new DefaultItemAnimator());
        recyclerEvaluations.setAdapter(adapter);

        setStudentOverall();
    }

    public void addMockData(){
        //Types of evaluation
        TypeEvaluation typeEvaluation1 = new TypeEvaluation();
        typeEvaluation1.setCode(1);
        typeEvaluation1.setCodeSubject(codeSubject);
        typeEvaluation1.setNameEvaluation("Evaluation XYZ Math");
        typeEvaluation1.setType("Written test");

        TypeEvaluation typeEvaluation2 = new TypeEvaluation();
        typeEvaluation2.setCode(2);
        typeEvaluation2.setCodeSubject(codeSubject);
        typeEvaluation2.setNameEvaluation("Evaluation GGH Math");
        typeEvaluation2.setType("Homework");

        //Evaluations
        Evaluation evaluation1 = new Evaluation();
        evaluation1.setDate("12/09/2016");
        evaluation1.setCodeSubject(codeSubject);
        evaluation1.setTypeEvaluation(typeEvaluation1);
        evaluation1.setDescription("This is a math written test");
        evaluation1.setGrade(3.5);
        evaluations.add(evaluation1);

        Evaluation evaluation2 = new Evaluation();
        evaluation2.setDate("14/08/2016");
        evaluation2.setCodeSubject(codeSubject);
        evaluation2.setTypeEvaluation(typeEvaluation1);
        evaluation2.setDescription("This is a math written test");
        evaluation2.setGrade(6.9);
        evaluations.add(evaluation2);

        Evaluation evaluation3 = new Evaluation();
        evaluation3.setDate("07/07/2016");
        evaluation3.setCodeSubject(codeSubject);
        evaluation3.setTypeEvaluation(typeEvaluation2);
        evaluation3.setDescription("This is a math homework");
        evaluation3.setGrade(5.1);
        evaluations.add(evaluation3);
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
            tvStudentStatus.setText("The student X will not pass!");
        }
        else{
            tvOverallGrade.setTextColor(ContextCompat.getColor(getActivity(), R.color.gravity_not_serious));
            tvStudentStatus.setText("The student X will pass");
        }
    }

}
