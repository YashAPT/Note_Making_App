package com.yash.notetaking.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yash.notetaking.EditorActivity;
import com.yash.notetaking.R;
import com.yash.notetaking.database.NoteEntity;

import java.util.List;

import static com.yash.notetaking.utils.Constants.NOTE_ID_KEY;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private Context mContext;
    private List<NoteEntity> mDataList;

    public NotesAdapter(Context mContext, List<NoteEntity> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.note_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final NoteEntity noteEntity=mDataList.get(position);
        holder.textView.setText(noteEntity.getText());
        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                intent.putExtra(NOTE_ID_KEY,noteEntity.getId());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public NoteEntity getNoteAtPosition(int position){
        return mDataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        FloatingActionButton fab;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fab=itemView.findViewById(R.id.fab_edit_note);
            textView=itemView.findViewById(R.id.note_text);
        }
    }
}
