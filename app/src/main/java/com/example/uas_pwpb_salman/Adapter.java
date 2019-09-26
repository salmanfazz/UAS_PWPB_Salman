package com.example.uas_pwpb_salman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteVH>{
    Context context;
    List<Note> listNote;
    UserActionListener listener;

    public Adapter(Context context, List<Note> listNote, UserActionListener listener) {
        this.context = context;
        this.listNote = listNote;
        this.listener = listener;
    }

    public interface UserActionListener{
        void onUserClick(Note note);
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        NoteVH nvh = new NoteVH(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        final Note write = listNote.get(position);
        holder.tvDateTime.setText(write.getDatetime());
        holder.tvTitle.setText(write.getTitle());
        holder.tvDetail.setText(write.getDetail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(write);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class NoteVH extends RecyclerView.ViewHolder{
        TextView tvDateTime,tvTitle,tvDetail;

        public NoteVH(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDetail = itemView.findViewById(R.id.tvDetail);
        }
    }



}
