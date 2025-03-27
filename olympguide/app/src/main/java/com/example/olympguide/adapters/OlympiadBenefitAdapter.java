package com.example.olympguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.R;
import com.example.olympguide.models.OlympiadBenefit;

import java.util.List;

public class OlympiadBenefitAdapter extends RecyclerView.Adapter<OlympiadBenefitAdapter.BenefitViewHolder> {

    private List<OlympiadBenefit> benefits;

    public OlympiadBenefitAdapter(List<OlympiadBenefit> benefits) {
        this.benefits = benefits;
    }

    @NonNull
    @Override
    public BenefitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_olympiad_benefit, parent, false);
        return new BenefitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BenefitViewHolder holder, int position) {
        OlympiadBenefit benefit = benefits.get(position);
        holder.tvProgramName.setText(benefit.getProgram().getName());
        // Здесь можно вывести и список льгот, например, в виде текста
    }

    @Override
    public int getItemCount() {
        return benefits.size();
    }

    static class BenefitViewHolder extends RecyclerView.ViewHolder {
        TextView tvProgramName;
        public BenefitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProgramName = itemView.findViewById(R.id.tvProgramName);
        }
    }
}