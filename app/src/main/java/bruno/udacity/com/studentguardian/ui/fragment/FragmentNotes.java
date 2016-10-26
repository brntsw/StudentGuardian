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
import bruno.udacity.com.studentguardian.adapter.NotesAdapter;
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

        addMockData();

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

    private void addMockData(){
        Note note1 = new Note();
        note1.setId(1);
        note1.setCodeSubject(100);
        note1.setDate("19/10/2016");
        note1.setTitle("Student X broke the vase from the director's room in purpose");
        note1.setDescription("Aliquam augue nulla, posuere non sollicitudin a, accumsan vel odio. Vivamus tristique bibendum lorem ac suscipit. Maecenas et sagittis enim, accumsan facilisis libero. Sed finibus risus a lectus faucibus facilisis");
        note1.setPathEvidenceImage("sdcard/randomimage.png");
        note1.setColorGravity(R.color.gravity_urgent);
        notes.add(note1);

        Note note2 = new Note();
        note2.setId(2);
        note2.setCodeSubject(101);
        note2.setDate("11/10/2016");
        note2.setTitle("Student X ran away from school");
        note2.setDescription("Aliquam augue nulla, posuere non sollicitudin a, accumsan vel odio. Vivamus tristique bibendum lorem ac suscipit. Maecenas et sagittis enim, accumsan facilisis libero. Sed finibus risus a lectus faucibus facilisis");
        note2.setPathEvidenceImage("sdcard/randomimage.png");
        note2.setColorGravity(R.color.gravity_urgent);
        notes.add(note2);

        Note note3 = new Note();
        note3.setId(3);
        note3.setCodeSubject(102);
        note3.setDate("02/08/2016");
        note3.setTitle("Student X got into a fight");
        note3.setDescription("Aliquam augue nulla, posuere non sollicitudin a, accumsan vel odio. Vivamus tristique bibendum lorem ac suscipit. Maecenas et sagittis enim, accumsan facilisis libero. Sed finibus risus a lectus faucibus facilisis");
        note3.setPathEvidenceImage("sdcard/randomimage.png");
        note3.setColorGravity(R.color.gravity_urgent);
        notes.add(note3);

        Note note4 = new Note();
        note4.setId(4);
        note4.setCodeSubject(105);
        note4.setDate("07/07/2016");
        note4.setTitle("Student X is injured");
        note4.setDescription("Aliquam augue nulla, posuere non sollicitudin a, accumsan vel odio. Vivamus tristique bibendum lorem ac suscipit. Maecenas et sagittis enim, accumsan facilisis libero. Sed finibus risus a lectus faucibus facilisis");
        note4.setPathEvidenceImage("sdcard/randomimage.png");
        note4.setColorGravity(R.color.gravity_alert);
        notes.add(note4);
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        Note note = adapter.getItem(position);

        Bundle args = new Bundle();
        args.putSerializable("note", note);

        fragNoteDetails = new FragmentNoteDetails();
        fragNoteDetails.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragNoteDetails, FragmentNoteDetails.TAG)
                .addToBackStack(null)
                .commit();
    }
}
