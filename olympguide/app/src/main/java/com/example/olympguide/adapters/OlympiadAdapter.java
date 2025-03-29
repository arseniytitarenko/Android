package com.example.olympguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.R;
import com.example.olympguide.models.Olympiad;

import java.util.List;

public class OlympiadAdapter extends RecyclerView.Adapter<OlympiadAdapter.OlympiadViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Olympiad olympiad);
    }

    private List<Olympiad> olympiads;
    private OnItemClickListener listener;

    public OlympiadAdapter(List<Olympiad> olympiads, OnItemClickListener listener) {
        this.olympiads = olympiads;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OlympiadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_olympiad, parent, false);
        return new OlympiadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OlympiadViewHolder holder, int position) {
        Olympiad olympiad = olympiads.get(position);
        holder.nameText.setText(olympiad.getName());
        holder.levelText.setText("Уровень: " + String.valueOf(olympiad.getLevel()));
        holder.profileText.setText("Профиль: " + olympiad.getProfile());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(olympiad));
    }

    @Override
    public int getItemCount() {
        return olympiads.size();
    }

    public void updateData(List<Olympiad> newOlympiads) {
        olympiads.clear();
        olympiads.addAll(newOlympiads);
        notifyDataSetChanged();
    }

    static class OlympiadViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView levelText;
        TextView profileText;

        public OlympiadViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tvItemOlympiadName);
            levelText = itemView.findViewById(R.id.tvItemOlympiadLevel);
            profileText = itemView.findViewById(R.id.tvItemOlympiadProfile);
        }
    }
}
