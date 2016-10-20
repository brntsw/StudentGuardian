package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import bruno.udacity.com.studentguardian.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 20/10/2016.
 */

public class DialogFragmentAbout extends DialogFragment {

    public static final String TAG = "DialogFragmentAbout";

    private Unbinder unbinder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_fragment_about, null, false);
        unbinder = ButterKnife.bind(this, view);

        return inicializaAlertDialog(view);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private AlertDialog inicializaAlertDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog_Alert);
        builder.setView(view)
                .setNegativeButton(R.string.action_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

}
