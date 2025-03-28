package com.example.olympguide.adapters;

import static com.example.olympguide.adapters.OlympiadBenefitAdapter.createButton;
import static com.example.olympguide.adapters.OlympiadBenefitAdapter.shortenSubjectName;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.R;
import com.example.olympguide.models.Benefit;
import com.example.olympguide.models.ConfirmationSubject;
import com.example.olympguide.models.Olympiad;
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
        ProgramBenefit programBenefit = benefits.get(position);
        Olympiad olympiad = programBenefit.getOlympiad();

        holder.tvOlympiadName.setText(olympiad.getName());
        holder.tvOlympiadLevel.setText("Уровень олимпиады: " + olympiad.getLevel());
        holder.tvOlympiadProfile.setText("Профиль: " + olympiad.getProfile());

        holder.benefitsContainer.removeAllViews();

        for (Benefit benefit : programBenefit.getBenefits()) {
            View benefitView = LayoutInflater.from(holder.itemView.getContext())
                    .inflate(R.layout.item_benefit, holder.benefitsContainer, false);

            setupBenefitView(benefitView, benefit);
            holder.benefitsContainer.addView(benefitView);
        }
    }

    private void setupBenefitView(View benefitView, Benefit benefit) {
        TextView tvBenefitType = benefitView.findViewById(R.id.tvBenefitType);
        TextView tvMinClass = benefitView.findViewById(R.id.tvMinClass);
        TextView tvMinDiplomaLevel = benefitView.findViewById(R.id.tvMinDiplomaLevel);
        LinearLayout confirmationLayout = benefitView.findViewById(R.id.confirmationLayout);
        LinearLayout fullScoreLayout = benefitView.findViewById(R.id.fullScoreLayout);
        TextView tvConfirmationTitle = benefitView.findViewById(R.id.tvConfirmationTitle);
        TextView tvFullScoreTitle = benefitView.findViewById(R.id.tvFullScoreTitle);

        tvBenefitType.setText(benefit.isIs_bvi() ? "БВИ" : "100 баллов");
        tvMinClass.setText("Класс: " + benefit.getMin_class());
        tvMinDiplomaLevel.setText("Уровень диплома: " + benefit.getMin_diploma_level());

        confirmationLayout.removeAllViews();
        if (!benefit.getConfirmation_subjects().isEmpty()) {
            tvConfirmationTitle.setVisibility(View.VISIBLE);
            for (ConfirmationSubject subject : benefit.getConfirmation_subjects()) {
                Button btn = createButton(
                        benefitView.getContext(),
                        shortenSubjectName(subject.getSubject()) + " | " + subject.getScore()
                );
                confirmationLayout.addView(btn);
            }
        } else {
            tvConfirmationTitle.setVisibility(View.GONE);
        }

        fullScoreLayout.removeAllViews();
        if (!benefit.isIs_bvi() && !benefit.getFull_score_subjects().isEmpty()) {
            tvFullScoreTitle.setVisibility(View.VISIBLE);
            for (String subject : benefit.getFull_score_subjects()) {
                Button btn = createButton(
                        benefitView.getContext(),
                        shortenSubjectName(subject)
                );
                fullScoreLayout.addView(btn);
            }
        } else {
            tvFullScoreTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return benefits.size();
    }

    static class BenefitViewHolder extends RecyclerView.ViewHolder {
        TextView tvOlympiadName;
        TextView tvOlympiadLevel;
        TextView tvOlympiadProfile;
        LinearLayout benefitsContainer;

        public BenefitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOlympiadName = itemView.findViewById(R.id.tvOlympiadName);
            tvOlympiadLevel = itemView.findViewById(R.id.tvOlympiadLevel);
            tvOlympiadProfile = itemView.findViewById(R.id.tvOlympiadProfile);
            benefitsContainer = itemView.findViewById(R.id.benefitsContainer);
        }
    }
}
