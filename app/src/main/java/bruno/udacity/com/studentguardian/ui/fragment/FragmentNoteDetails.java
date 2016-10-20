package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.Note;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 20/10/2016.
 */

public class FragmentNoteDetails extends Fragment {

    public static final String TAG = "FragmentNoteDetails";

    @BindView(R.id.tv_note_title)
    TextView tvNoteTitle;

    @BindView(R.id.img_proof)
    ImageView imgProof;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    private Unbinder unbinder;
    private Bundle args;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Activity activity = getActivity();
        activity.setTitle(R.string.description_note_details);

        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        args = getArguments();

        return view;
    }

    public void onStart() {
        super.onStart();

        Note note = (Note) args.getSerializable("note");

        if(note != null){
            tvNoteTitle.setText(note.getTitle());
            tvDescription.setText(note.getDescription());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
