package com.example.olympguide.adapters;

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
        OlympiadBenefit benefit = benefits.get(position);
        holder.tvProgramName.setText(benefit.getProgram().getName());
        holder.tvProgramField.setText(benefit.getProgram().getField());

        List<Benefit> benefitList = benefit.getBenefits();
        for (Benefit b : benefitList) {
            List<ConfirmationSubject> confirmationSubjects = b.getConfirmation_subjects();
            for (ConfirmationSubject subject : confirmationSubjects) {
                String shortenedName = shortenSubjectName(subject.getSubject());
                String buttonText = shortenedName + " | " + subject.getScore();
                Button button = new Button(holder.itemView.getContext());
                button.setText(buttonText);
                button.setTextColor(Color.BLUE);
                button.setBackgroundResource(R.drawable.button_border);
                holder.buttonsLayout.addView(button);
            }

            if (!b.isIs_bvi()) {
                String fullScoreSubjects = getFullScoreSubjects(b.getFull_score_subjects());
                holder.tvFullScoreSubjects.setText(fullScoreSubjects);
            }

            // Отображаем минимальный класс и уровень диплома
            holder.tvMinClass.setText("Минимальный класс: " + b.getMin_class());
            holder.tvMinDiplomaLevel.setText("Минимум уровень диплома: " + b.getMin_diploma_level());
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
        TextView tvFullScoreSubjects;
        LinearLayout buttonsLayout;

        public BenefitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProgramName = itemView.findViewById(R.id.tvProgramName);
            tvProgramField = itemView.findViewById(R.id.tvProgramField);
            tvMinClass = itemView.findViewById(R.id.tvMinClass);
            tvMinDiplomaLevel = itemView.findViewById(R.id.tvMinDiplomaLevel);
            tvFullScoreSubjects = itemView.findViewById(R.id.tvFullScoreSubjects);
            buttonsLayout = itemView.findViewById(R.id.buttonsLayout); // контейнер для кнопок
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

    private String getFullScoreSubjects(List<String> fullScoreSubjects) {
        if (fullScoreSubjects != null && !fullScoreSubjects.isEmpty()) {
            return String.join(", ", fullScoreSubjects);
        } else {
            return "Нет данных";
        }
    }
}


