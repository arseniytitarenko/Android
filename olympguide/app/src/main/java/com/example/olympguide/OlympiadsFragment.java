package com.example.olympguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.olympguide.adapters.OlympiadAdapter;
import com.example.olympguide.adapters.ProgramGroupAdapter;
import com.example.olympguide.models.Olympiad;
import com.example.olympguide.models.ProgramGroup;
import com.example.olympguide.network.ApiClient;
import com.example.olympguide.network.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlympiadsFragment extends Fragment implements OlympiadAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private OlympiadAdapter adapter;
    private ApiService apiService;

    private SearchView searchView;
    private Spinner levelSpinner;
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;
    private List<String> selectedLevels = new ArrayList<>();

    public OlympiadsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_olympiads, container, false);

        searchView = view.findViewById(R.id.searchView);
        levelSpinner = view.findViewById(R.id.levelSpinner);
        recyclerView = view.findViewById(R.id.recycler_olympiads);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new OlympiadAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        setupFilters();
        loadOlympiads();
        return view;
    }

    private void loadOlympiads() {
        String searchQuery = searchView.getQuery().toString().trim();
        List<String> levels = selectedLevels.isEmpty() ? null : selectedLevels;

        apiService.getOlympiads(searchQuery, levels).enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Olympiad> olympiads = response.body();
                    adapter.updateData(olympiads);
                    Log.d("OlympiadsFragment", "Обновлено: " + olympiads.size() + " олимпиад");
                } else {
                    adapter.updateData(Collections.emptyList());
                    Log.e("OlympiadsFragment", "Ошибка: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Olympiad>> call, Throwable t) {
                adapter.updateData(Collections.emptyList());
                Log.e("OlympiadsFragment", "Ошибка сети: ", t);
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
                searchRunnable = () -> loadOlympiads();
                searchHandler.postDelayed(searchRunnable, 500);
                return true;
            }
        });

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLevels.clear();
                if (position > 0) {
                    selectedLevels.add(String.valueOf(position));
                }
                loadOlympiads();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedLevels.clear();
            }
        });
    }

    @Override
    public void onItemClick(Olympiad olympiad) {
        Intent intent = new Intent(getContext(), OlympiadDetailActivity.class);
        intent.putExtra("olympiad_id", olympiad.getOlympiad_id());
        startActivity(intent);
    }
}
