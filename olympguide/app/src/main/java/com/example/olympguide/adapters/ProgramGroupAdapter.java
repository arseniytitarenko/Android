package com.example.olympguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        holder.bind(group);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void updateData(List<ProgramGroup> newGroups) {
        groups.clear();
        groups.addAll(newGroups);
        notifyDataSetChanged();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView groupTitle;
        private final RecyclerView programsRecycler;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupTitle = itemView.findViewById(R.id.tvGroupTitle);
            programsRecycler = itemView.findViewById(R.id.rvPrograms);

            programsRecycler.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            programsRecycler.setItemAnimator(new DefaultItemAnimator());
        }

        void bind(ProgramGroup group) {
            groupTitle.setText(group.getName());

            ProgramItemAdapter adapter = new ProgramItemAdapter(group.getPrograms(), listener);
            programsRecycler.setAdapter(adapter);
        }
    }

    static class ProgramItemAdapter extends RecyclerView.Adapter<ProgramItemAdapter.ProgramViewHolder> {

        private List<ProgramItem> programs;
        private OnProgramClickListener listener;

        ProgramItemAdapter(List<ProgramItem> programs, OnProgramClickListener listener) {
            this.programs = programs;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_program_in_group, parent, false);
            return new ProgramViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
            ProgramItem program = programs.get(position);
            holder.bind(program);

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProgramClick(program.getProgram_id());
                }
            });
        }

        @Override
        public int getItemCount() {
            return programs.size();
        }

        static class ProgramViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvName;
            private final TextView tvField;

            public ProgramViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvProgramName);
                tvField = itemView.findViewById(R.id.tvProgramField);
            }

            void bind(ProgramItem program) {
                tvName.setText(program.getName());
                tvField.setText(program.getField());
            }
        }
    }
}