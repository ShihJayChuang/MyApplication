package com.example.jay.myapplication.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jay.myapplication.DatabaseGetSet;
import com.example.jay.myapplication.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DatabaseGetSet> databaseGetSets;
    private RecyclerView recyclerView;
    private OnItemClickListener listener;

    public MyAdapter(Context context, List<DatabaseGetSet> databaseGetSets) {
        this.context = context;
        this.databaseGetSets = databaseGetSets;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_images, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DatabaseGetSet databaseGetSet = databaseGetSets.get(position);
        holder.textViewName.setText(databaseGetSet.getTypeName());
        Glide.with(context).load(databaseGetSet.getThumbPathUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && v != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(v);
                    listener.onItemClick(recyclerView, v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return databaseGetSets.size();

    }


    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
