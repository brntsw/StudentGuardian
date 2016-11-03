package bruno.udacity.com.studentguardian.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

public class FragmentAbsences extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.recycler_absences)
    RecyclerView recyclerAbsences;

    private AbsencesAdapter adapter;
    private List<Absence> absences;

    private Unbinder unbinder;
    private static final int ABSENCE_LOADER = 0;

    private static final String[] ABSENCE_COLUMNS = {
            StudentGuardianContract.AbsenceEntry.COLUMN_ABSENCES,
            StudentGuardianContract.AbsenceEntry.COLUMN_CODE_SUBJECT
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_absences, container, false);
        unbinder = ButterKnife.bind(this, view);

        absences = new ArrayList<>();

        getLoaderManager().restartLoader(ABSENCE_LOADER, null, this);

        return view;
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                StudentGuardianContract.AbsenceEntry.CONTENT_URI,
                ABSENCE_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null){
            while(data.moveToNext()){
                Absence absence = new Absence();
                absence.setCodeSubject(data.getInt(data.getColumnIndex(StudentGuardianContract.AbsenceEntry.COLUMN_CODE_SUBJECT)));
                absence.setCountAbsences(data.getInt(data.getColumnIndex(StudentGuardianContract.AbsenceEntry.COLUMN_ABSENCES)));

                absences.add(absence);
            }
        }

        adapter = new AbsencesAdapter(getActivity(), absences);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerAbsences.setLayoutManager(manager);
        recyclerAbsences.setItemAnimator(new DefaultItemAnimator());
        recyclerAbsences.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
