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
import bruno.udacity.com.studentguardian.adapter.NotesAdapter;
import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.Note;
import bruno.udacity.com.studentguardian.ui.activity.listener.OnRecyclerViewItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 19/10/2016.
 */

public class FragmentNotes extends Fragment implements OnRecyclerViewItemClickListener {

    @BindView(R.id.recycler_notes)
    RecyclerView recyclerNotes;

    private Unbinder unbinder;
    private List<Note> notes;
    private NotesAdapter adapter;

    private FragmentNoteDetails fragNoteDetails;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Activity activity = getActivity();
        activity.setTitle(R.string.description_notes);

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        unbinder = ButterKnife.bind(this, view);

        notes = new ArrayList<>();

        return view;
    }

    public void onStart(){
        super.onStart();

        Cursor cursor = getActivity().getContentResolver().query(StudentGuardianContract.NoteEntry.CONTENT_URI, null, null, null, StudentGuardianContract.NoteEntry.COLUMN_DATE);
        if(cursor != null){
            while(cursor.moveToNext()){
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(StudentGuardianContract.NoteEntry.COLUMN_CODE)));
                note.setCodeSubject(cursor.getInt(cursor.getColumnIndex(StudentGuardianContract.NoteEntry.COLUMN_CODE_SUBJECT)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.NoteEntry.COLUMN_TITLE)));
                note.setDate(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.NoteEntry.COLUMN_DATE)));
                note.setColorGravity(cursor.getInt(cursor.getColumnIndex(StudentGuardianContract.NoteEntry.COLUMN_GRAVITY)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.NoteEntry.COLUMN_DESCRIPTION)));
                note.setPathEvidenceImage(cursor.getString(cursor.getColumnIndex(StudentGuardianContract.NoteEntry.COLUMN_EVIDENCE_IMAGE)));

                notes.add(note);
            }

            cursor.close();
        }

        //Fill the Recycler view with the notes
        adapter = new NotesAdapter(getActivity(), notes);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerNotes.setLayoutManager(manager);
        recyclerNotes.setItemAnimator(new DefaultItemAnimator());
        recyclerNotes.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        Note note = adapter.getItem(position);

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.bundle_note), note);

        fragNoteDetails = new FragmentNoteDetails();
        fragNoteDetails.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragNoteDetails, FragmentNoteDetails.TAG)
                .addToBackStack(null)
                .commit();
    }
}
