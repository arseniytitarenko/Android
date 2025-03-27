package com.example.olympguide;

import android.os.Bundle;
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

    private TextView tvName, tvField, tvCost;
    private RecyclerView recyclerBenefits;
    private ApiService apiService;
    private int programId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);

        tvName = findViewById(R.id.tvProgramDetailName);
        tvField = findViewById(R.id.tvProgramDetailField);
        tvCost = findViewById(R.id.tvProgramDetailCost);
        recyclerBenefits = findViewById(R.id.recyclerProgramBenefits);
        recyclerBenefits.setLayoutManager(new LinearLayoutManager(this));

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
                    tvName.setText(detail.getName());
                    tvField.setText(detail.getField());
                    tvCost.setText(String.valueOf(detail.getCost()));
                }
            }

            @Override
            public void onFailure(Call<ProgramDetail> call, Throwable t) { }
        });
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
            public void onFailure(Call<List<ProgramBenefit>> call, Throwable t) { }
        });
    }
}
