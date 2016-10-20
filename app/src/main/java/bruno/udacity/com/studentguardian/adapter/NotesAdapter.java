package bruno.udacity.com.studentguardian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.model.Note;
import bruno.udacity.com.studentguardian.model.Subject;
import bruno.udacity.com.studentguardian.ui.activity.listener.OnRecyclerViewItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BPardini on 19/10/2016.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private List<Note> notes;
    private int selectedPosition = -1;
    private OnRecyclerViewItemClickListener listener;

    public NotesAdapter(Context context, List<Note> notes){
        this.context = context;
        this.notes = notes;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Note note = notes.get(position);
        if(note != null){
            holder.apply(note);
        }

        if(selectedPosition == position){
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecyclerViewItemClicked(holder.getAdapterPosition(), 1);
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
            }
        });
    }

    public Note getItem(int position){
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_note_title)
        TextView tvNoteTitle;

        @BindView(R.id.tv_note_date)
        TextView tvNoteDate;

        @BindView(R.id.tv_note_description)
        TextView tvNoteDescription;

        @BindView(R.id.view_gravity)
        View viewGravity;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void apply(Note note) {
            tvNoteTitle.setText(note.getTitle());
            tvNoteDate.setText(note.getDate());
            tvNoteDescription.setText(note.getDescription());
            viewGravity.setBackgroundColor(ContextCompat.getColor(context, note.getColorGravity()));
        }
    }

}
