package bruno.udacity.com.studentguardian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.ContentClass;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BPardini on 21/10/2016.
 */

public class ContentsSubjectAdapter extends RecyclerView.Adapter<ContentsSubjectAdapter.ViewHolder> {

    private List<ContentClass> contents;

    public ContentsSubjectAdapter(List<ContentClass> contents){
        this.contents = contents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_subject_item, parent, false);
        return new ContentsSubjectAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContentClass content = contents.get(position);
        if(content != null){
            holder.apply(content);
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }

        void apply(ContentClass content){
            tvContent.setText(content.getContent());
        }
    }

}
