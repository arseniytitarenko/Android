package com.example.olympguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.R;
import com.example.olympguide.models.ProgramGroup;
import com.example.olympguide.models.ProgramItem;

import java.util.List;

public class ProgramGroupAdapter extends RecyclerView.Adapter<ProgramGroupAdapter.GroupViewHolder> {

    public interface OnProgramClickListener {
        void onProgramClick(int programId);
    }

    private List<ProgramGroup> groups;
    private OnProgramClickListener listener;

    public ProgramGroupAdapter(List<ProgramGroup> groups, OnProgramClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program_group, parent, false);
        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        ProgramGroup group = groups.get(position);
        holder.groupTitle.setText(group.getName());
        // Для простоты выводим программы внутри группы в одном TextView через join
        StringBuilder sb = new StringBuilder();
        for (ProgramItem item : group.getPrograms()){
            sb.append(item.getName()).append("\n");
        }
        holder.programsText.setText(sb.toString());
        holder.itemView.setOnClickListener(v -> {
            // При клике можно передавать id первой программы или сделать вложенный RecyclerView
            if (!group.getPrograms().isEmpty()){
                listener.onProgramClick(group.getPrograms().get(0).getProgram_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupTitle, programsText;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupTitle = itemView.findViewById(R.id.tvGroupTitle);
            programsText = itemView.findViewById(R.id.tvPrograms);
        }
    }
}
