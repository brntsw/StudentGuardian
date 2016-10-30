package bruno.udacity.com.studentguardian.ui.fragment;

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
import bruno.udacity.com.studentguardian.adapter.AbsencesAdapter;
import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.Absence;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 20/10/2016.
 */

public class FragmentAbsences extends Fragment {

    @BindView(R.id.recycler_absences)
    RecyclerView recyclerAbsences;

    private AbsencesAdapter adapter;
    private List<Absence> absences;

    private Unbinder unbinder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_absences, container, false);
        unbinder = ButterKnife.bind(this, view);

        absences = new ArrayList<>();

        return view;
    }

    public void onStart() {
        super.onStart();

        Cursor cursorAbsence = getActivity().getContentResolver().query(StudentGuardianContract.AbsenceEntry.CONTENT_URI, null, null, null, null);
        if(cursorAbsence != null){
            while(cursorAbsence.moveToNext()){
                Absence absence = new Absence();
                absence.setCodeSubject(cursorAbsence.getInt(cursorAbsence.getColumnIndex("code_subject")));
                absence.setCountAbsences(cursorAbsence.getInt(cursorAbsence.getColumnIndex("count_absences")));

                absences.add(absence);
            }

            cursorAbsence.close();
        }

        adapter = new AbsencesAdapter(absences);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerAbsences.setLayoutManager(manager);
        recyclerAbsences.setItemAnimator(new DefaultItemAnimator());
        recyclerAbsences.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
