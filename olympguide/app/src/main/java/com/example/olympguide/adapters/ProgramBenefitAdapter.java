package com.example.olympguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.R;
import com.example.olympguide.models.ProgramBenefit;

import java.util.List;

public class ProgramBenefitAdapter extends RecyclerView.Adapter<ProgramBenefitAdapter.BenefitViewHolder> {

    private List<ProgramBenefit> benefits;

    public ProgramBenefitAdapter(List<ProgramBenefit> benefits) {
        this.benefits = benefits;
    }

    @NonNull
    @Override
    public BenefitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program_benefit, parent, false);
        return new BenefitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BenefitViewHolder holder, int position) {
        ProgramBenefit benefit = benefits.get(position);
        holder.tvOlympiadName.setText(benefit.getOlympiad().getName());
        // Можно вывести дополнительную информацию о льготах
    }

    @Override
    public int getItemCount() {
        return benefits.size();
    }

    static class BenefitViewHolder extends RecyclerView.ViewHolder {
        TextView tvOlympiadName;
        public BenefitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOlympiadName = itemView.findViewById(R.id.tvOlympiadName);
        }
    }
}
