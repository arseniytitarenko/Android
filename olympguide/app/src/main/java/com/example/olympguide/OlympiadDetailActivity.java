package com.example.olympguide;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.adapters.OlympiadBenefitAdapter;
import com.example.olympguide.models.OlympiadBenefit;
import com.example.olympguide.models.OlympiadDetail;
import com.example.olympguide.network.ApiClient;
import com.example.olympguide.network.ApiService;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlympiadDetailActivity extends AppCompatActivity {

    private TextView tvName, tvLevel, tvProfile, tvLink;
    private RecyclerView recyclerBenefits;
    private ApiService apiService;
    private int olympiadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olympiad_detail);

        tvName = findViewById(R.id.tvOlympiadDetailName);
        tvLevel = findViewById(R.id.tvOlympiadDetailLevel);
        tvLink = findViewById(R.id.tvOlympiadDetailLink);
        tvProfile = findViewById(R.id.tvOlympiadDetailProfile);

        recyclerBenefits = findViewById(R.id.recyclerOlympiadBenefits);
        recyclerBenefits.setLayoutManager(new LinearLayoutManager(this));

        olympiadId = getIntent().getIntExtra("olympiad_id", -1);
        apiService = ApiClient.getClient().create(ApiService.class);

        loadOlympiadDetail();
        loadBenefits();
    }

    private void loadOlympiadDetail() {
        apiService.getOlympiadDetail(olympiadId).enqueue(new Callback<OlympiadDetail>() {
            @Override
            public void onResponse(Call<OlympiadDetail> call, Response<OlympiadDetail> response) {
                if(response.isSuccessful() && response.body() != null){
                    OlympiadDetail detail = response.body();
                    tvName.setText(detail.getName());
                    tvLevel.setText(String.valueOf(detail.getLevel()));
                    tvProfile.setText(detail.getProfile());
                    tvLink.setText(detail.getLink());
                }
            }

            @Override
            public void onFailure(Call<OlympiadDetail> call, Throwable t) { }
        });
    }

    private void loadBenefits() {
        apiService.getOlympiadBenefits(olympiadId).enqueue(new Callback<List<OlympiadBenefit>>() {
            @Override
            public void onResponse(Call<List<OlympiadBenefit>> call, Response<List<OlympiadBenefit>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<OlympiadBenefit> benefits = response.body();
                    OlympiadBenefitAdapter adapter = new OlympiadBenefitAdapter(benefits);
                    recyclerBenefits.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<OlympiadBenefit>> call, Throwable t) {
            }
        });
    }

}
