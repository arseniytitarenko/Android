package com.example.olympguide.adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.example.olympguide.models.OlympiadBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        OlympiadBenefit olympiadBenefit = benefits.get(position);
        holder.tvProgramName.setText(olympiadBenefit.getProgram().getName());
        holder.tvProgramField.setText(olympiadBenefit.getProgram().getField());

        holder.benefitsContainer.removeAllViews();

        for (Benefit benefit : olympiadBenefit.getBenefits()) {
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
        tvMinDiplomaLevel.setText("Уровень: " + benefit.getMin_diploma_level());

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
        TextView tvProgramName;
        TextView tvProgramField;
        TextView tvMinClass;
        TextView tvMinDiplomaLevel;
        LinearLayout benefitsContainer;

        public BenefitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProgramName = itemView.findViewById(R.id.tvProgramName);
            tvProgramField = itemView.findViewById(R.id.tvProgramField);
            tvMinClass = itemView.findViewById(R.id.tvMinClass);
            tvMinDiplomaLevel = itemView.findViewById(R.id.tvMinDiplomaLevel);
            benefitsContainer = itemView.findViewById(R.id.benefitsContainer);
        }
    }

    private String shortenSubjectName(String subject) {
        Map<String, String> subjectMap = Map.ofEntries(
                Map.entry("Русский язык", "РЯ"),
                Map.entry("Математика", "Мат"),
                Map.entry("Физика", "Физ"),
                Map.entry("Химия", "Хим"),
                Map.entry("История", "Ист"),
                Map.entry("Обществознание", "Общ"),
                Map.entry("Информатика", "Инф"),
                Map.entry("Биология", "Биол"),
                Map.entry("География", "Гео"),
                Map.entry("Иностранный язык", "ИЯ"),
                Map.entry("Литература", "Лит")
        );
        return subjectMap.getOrDefault(subject, "N/A");
    }

    private Button createButton(Context context, String text) {
        Button btn = new Button(context);
        btn.setText(text);
        btn.setTextColor(Color.parseColor("#90CAF9"));
        btn.setBackgroundResource(R.drawable.button_border);
        btn.setPadding(16, 8, 16, 8);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 16, 0);
        btn.setLayoutParams(params);

        return btn;
    }
}


