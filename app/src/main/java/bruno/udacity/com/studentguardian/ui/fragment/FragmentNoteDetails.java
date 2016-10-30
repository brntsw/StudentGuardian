package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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

    @BindView(R.id.view_gravity)
    View viewGravity;

    @BindView(R.id.tv_status)
    TextView tvStatus;

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

        final Note note = (Note) args.getSerializable(getString(R.string.note));

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.title_download_image));
        progressDialog.setMessage(getString(R.string.msg_download_image));

        if(note != null) {
            //Download the image to a specific path
            Picasso.with(getActivity()).load(note.getPathEvidenceImage()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    progressDialog.dismiss();
                    imgProof.setImageBitmap(bitmap);
                    //Utils.saveImageToExternalStorage(getActivity(), bitmap, note.getId());
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.e("Error drawable", errorDrawable.toString());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    progressDialog.show();
                }
            });
        }

        return view;
    }

    public void onStart() {
        super.onStart();

        Note note = (Note) args.getSerializable(getString(R.string.bundle_note));

        if(note != null){
            tvNoteTitle.setText(note.getTitle());
            viewGravity.setBackgroundColor(ContextCompat.getColor(getActivity(), note.getColorGravity()));

            switch (note.getColorGravity()){
                case R.color.gravity_alert:
                    tvStatus.setText(R.string.label_alert);
                    break;
                case R.color.gravity_urgent:
                    tvStatus.setText(R.string.label_serious);
                    break;
                case R.color.gravity_not_serious:
                    tvStatus.setText(R.string.label_not_serious);
                    break;
            }

            tvDescription.setText(note.getDescription());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
