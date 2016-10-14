package bruno.udacity.com.studentguardian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.Subject;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BPardini on 14/10/2016.
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    private List<Subject> subjects;

    public SubjectsAdapter(List<Subject> subjects){
        this.subjects = subjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        if(subject != null){
            holder.apply(subject);
        }
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_subject_title)
        TextView tvSubjectTitle;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void apply(Subject subject) {
            tvSubjectTitle.setText(subject.getName());
        }
    }
}
