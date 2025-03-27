package com.example.olympguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.adapters.ProgramGroupAdapter;
import com.example.olympguide.models.ProgramGroup;
import com.example.olympguide.network.ApiClient;
import com.example.olympguide.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramsFragment extends Fragment implements ProgramGroupAdapter.OnProgramClickListener {

    private RecyclerView recyclerView;
    private ProgramGroupAdapter adapter;
    private ApiService apiService;

    public ProgramsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programs, container, false);
        recyclerView = view.findViewById(R.id.recycler_programs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiService = ApiClient.getClient().create(ApiService.class);
        loadPrograms();
        return view;
    }

    private void loadPrograms() {
        apiService.getProgramGroups().enqueue(new Callback<List<ProgramGroup>>() {
            @Override
            public void onResponse(Call<List<ProgramGroup>> call, Response<List<ProgramGroup>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ProgramGroup> groups = response.body();
                    adapter = new ProgramGroupAdapter(groups, ProgramsFragment.this);
                    recyclerView.setAdapter(adapter);
                    Log.d("ProgramsFragment", "Получено групп: " + groups.size());
                } else {
                    Log.d("ProgramsFragment", "Ошибка: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ProgramGroup>> call, Throwable t) {
                Log.e("ProgramsFragment", "Ошибка запроса: ", t);
            }
        });
    }

    @Override
    public void onProgramClick(int programId) {
        Intent intent = new Intent(getContext(), ProgramDetailActivity.class);
        intent.putExtra("program_id", programId);
        startActivity(intent);
    }
}
