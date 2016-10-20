package bruno.udacity.com.studentguardian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.Evaluation;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BPardini on 20/10/2016.
 */

public class EvaluationsAdapter extends RecyclerView.Adapter<EvaluationsAdapter.ViewHolder> {

    private List<Evaluation> evaluations;

    public EvaluationsAdapter(List<Evaluation> evaluations){
        this.evaluations = evaluations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_item, parent, false);
        return new EvaluationsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Evaluation evaluation = evaluations.get(position);
        if(evaluation != null){
            holder.apply(evaluation);
        }
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.tv_grade)
        TextView tvGrade;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void apply(Evaluation evaluation) {
            tvName.setText(evaluation.getTypeEvaluation().getNameEvaluation());
            tvDate.setText(evaluation.getDate());
            tvGrade.setText(String.valueOf(evaluation.getGrade()));
            tvDescription.setText(evaluation.getDescription());
        }
    }

}
