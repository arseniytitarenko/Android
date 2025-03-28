package com.example.olympguide;

import static com.example.olympguide.adapters.OlympiadBenefitAdapter.shortenSubjectName;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.adapters.ProgramBenefitAdapter;
import com.example.olympguide.models.ProgramBenefit;
import com.example.olympguide.models.ProgramDetail;
import com.example.olympguide.network.ApiClient;
import com.example.olympguide.network.ApiService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramDetailActivity extends AppCompatActivity {

    private TextView tvName, tvField, tvCost, tvBudgetPlaces, tvPaidPlaces, tvLink;
    private RecyclerView recyclerBenefits;
    private LinearLayout layoutRequiredSubjects, layoutOptionalSubjects;
    private TextView tvRequiredSubjectsTitle, tvOptionalSubjectsTitle;
    private ApiService apiService;
    private int programId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);

        initViews();
        setupRecyclerView();
        loadData();
    }

    private void initViews() {
        tvName = findViewById(R.id.tvProgramDetailName);
        tvField = findViewById(R.id.tvProgramDetailField);
        tvCost = findViewById(R.id.tvProgramDetailCost);
        tvBudgetPlaces = findViewById(R.id.tvProgramBudgetPlaces);
        tvPaidPlaces = findViewById(R.id.tvProgramPaidPlaces);
        tvLink = findViewById(R.id.tvProgramLink);
        recyclerBenefits = findViewById(R.id.recyclerProgramBenefits);
        layoutRequiredSubjects = findViewById(R.id.layoutRequiredSubjects);
        layoutOptionalSubjects = findViewById(R.id.layoutOptionalSubjects);
        tvRequiredSubjectsTitle = findViewById(R.id.tvRequiredSubjectsTitle);
        tvOptionalSubjectsTitle = findViewById(R.id.tvOptionalSubjectsTitle);
    }

    private void setupRecyclerView() {
        recyclerBenefits.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        programId = getIntent().getIntExtra("program_id", -1);
        apiService = ApiClient.getClient().create(ApiService.class);

        loadProgramDetail();
        loadBenefits();
    }

    private void loadProgramDetail() {
        apiService.getProgramDetail(programId).enqueue(new Callback<ProgramDetail>() {
            @Override
            public void onResponse(Call<ProgramDetail> call, Response<ProgramDetail> response) {
                if(response.isSuccessful() && response.body() != null){
                    ProgramDetail detail = response.body();
                    updateUI(detail);
                }
            }

            @Override
            public void onFailure(Call<ProgramDetail> call, Throwable t) {
            }
        });
    }

    private void updateUI(ProgramDetail detail) {
        tvName.setText(detail.getName());
        tvField.setText(detail.getField());
        tvCost.setText(String.format("%d ₽", detail.getCost()));
        tvBudgetPlaces.setText(String.valueOf(detail.getBudget_places()));
        tvPaidPlaces.setText(String.valueOf(detail.getPaid_places()));
        tvLink.setText(detail.getLink());

        setupSubjects(layoutRequiredSubjects, tvRequiredSubjectsTitle, detail.getRequired_subjects());
        setupSubjects(layoutOptionalSubjects, tvOptionalSubjectsTitle, detail.getOptional_subjects());
    }

    private void setupSubjects(LinearLayout container, TextView title, List<String> subjects) {
        container.removeAllViews();
        if(subjects != null && !subjects.isEmpty()) {
            title.setVisibility(View.VISIBLE);
            for(String subject : subjects) {
                Button btn = createSubjectButton(subject);
                container.addView(btn);
            }
        } else {
            title.setVisibility(View.GONE);
        }
    }

    private Button createSubjectButton(String subject) {
        Button btn = new Button(this);
        btn.setText(shortenSubjectName(subject));
        btn.setTextColor(Color.parseColor("#2196F3"));
        btn.setBackgroundResource(R.drawable.button_border);
        // btn.setPadding(32, 16, 32, 16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        // params.setMargins(0, 0, 8, 0);
        btn.setLayoutParams(params);
        return btn;
    }

    private void loadBenefits() {
        apiService.getProgramBenefits(programId).enqueue(new Callback<List<ProgramBenefit>>() {
            @Override
            public void onResponse(Call<List<ProgramBenefit>> call, Response<List<ProgramBenefit>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ProgramBenefit> benefits = response.body();
                    ProgramBenefitAdapter adapter = new ProgramBenefitAdapter(benefits);
                    recyclerBenefits.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProgramBenefit>> call, Throwable t) {
            }
        });
    }
}
