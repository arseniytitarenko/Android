package com.example.olympguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympguide.adapters.ProgramGroupAdapter;
import com.example.olympguide.models.ProgramGroup;
import com.example.olympguide.network.ApiClient;
import com.example.olympguide.network.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramsFragment extends Fragment implements ProgramGroupAdapter.OnProgramClickListener {

    private SearchView searchView;
    private Spinner degreeSpinner;
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;
    private List<String> selectedDegrees = new ArrayList<>();

    private RecyclerView recyclerView;
    private ProgramGroupAdapter adapter;
    private ApiService apiService;

    public ProgramsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programs, container, false);

        searchView = view.findViewById(R.id.searchView);
        degreeSpinner = view.findViewById(R.id.degreeSpinner);
        setupFilters();

        recyclerView = view.findViewById(R.id.recycler_programs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ProgramGroupAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        loadPrograms();
        return view;
    }

    private void loadPrograms() {
        String searchQuery = searchView.getQuery().toString().trim();
        List<String> degrees = selectedDegrees.isEmpty() ? null : selectedDegrees;

        apiService.getProgramGroups(searchQuery, degrees).enqueue(new Callback<List<ProgramGroup>>() {
            @Override
            public void onResponse(Call<List<ProgramGroup>> call, Response<List<ProgramGroup>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ProgramGroup> groups = response.body();
                    adapter.updateData(groups);
                    Log.d("ProgramsFragment", "Обновлено групп: " + groups.size());
                } else {
                    adapter.updateData(Collections.emptyList());
                    Log.d("ProgramsFragment", "Ошибка: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ProgramGroup>> call, Throwable t) {
                adapter.updateData(Collections.emptyList());
                Log.e("ProgramsFragment", "Ошибка сети: ", t);
            }
        });
    }

    private void setupFilters() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> loadPrograms();
                searchHandler.postDelayed(searchRunnable, 500);
                return true;
            }
        });

        degreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDegrees.clear();
                switch (position) {
                    case 1:
                        selectedDegrees.add("Бакалавриат");
                        break;
                    case 2:
                        selectedDegrees.add("Специалитет");
                        break;
                }
                loadPrograms();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDegrees.clear();
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
