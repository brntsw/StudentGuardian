package bruno.udacity.com.studentguardian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.Absence;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BPardini on 20/10/2016.
 */

public class AbsencesAdapter extends RecyclerView.Adapter<AbsencesAdapter.ViewHolder> {

    private Context context;
    private List<Absence> absences;

    public AbsencesAdapter(Context context, List<Absence> absences){
        this.context = context;
        this.absences = absences;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.absence_item, parent, false);
        return new AbsencesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Absence absence = absences.get(position);
        if(absence != null){
            holder.apply(absence);
        }
    }

    @Override
    public int getItemCount() {
        return absences.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_subject_title)
        TextView tvSubjectTitle;

        @BindView(R.id.tv_absences)
        TextView tvAbsences;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }

        void apply(Absence absence) {
            switch (absence.getCodeSubject()){
                case 100: //English
                    tvSubjectTitle.setText(context.getString(R.string.english));
                    break;
                case 101: //Mathematics
                    tvSubjectTitle.setText(context.getString(R.string.mathematics));
                    break;
                case 102: //History
                    tvSubjectTitle.setText(context.getString(R.string.history));
                    break;
                case 103: //Chemistry
                    tvSubjectTitle.setText(context.getString(R.string.chemistry));
                    break;
                case 104: //Physics
                    tvSubjectTitle.setText(context.getString(R.string.physics));
                    break;
                case 105: //Geography
                    tvSubjectTitle.setText(context.getString(R.string.geography));
                    break;
            }

            tvAbsences.setText(String.valueOf(absence.getCountAbsences()));
        }
    }

}
