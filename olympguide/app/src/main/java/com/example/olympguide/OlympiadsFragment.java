package com.example.olympguide;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olympguide.adapters.OlympiadAdapter;
import com.example.olympguide.adapters.ProgramGroupAdapter;
import com.example.olympguide.models.Olympiad;
import com.example.olympguide.models.ProgramGroup;
import com.example.olympguide.network.ApiClient;
import com.example.olympguide.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlympiadsFragment extends Fragment implements OlympiadAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private OlympiadAdapter adapter;
    private ApiService apiService;

    public OlympiadsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_olympiads, container, false);
        recyclerView = view.findViewById(R.id.recycler_olympiads);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiService = ApiClient.getClient().create(ApiService.class);
        loadOlympiads();
        return view;
    }

    private void loadOlympiads() {
        apiService.getOlympiads().enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Olympiad> olympiads = response.body();
                    adapter = new OlympiadAdapter(olympiads, OlympiadsFragment.this);
                    recyclerView.setAdapter(adapter);
                    Log.d("OlympiadsFragment", "Получено олимпиад: " + olympiads.size());
                } else {
                    Log.d("OlympiadsFragment", "Ошибка: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Olympiad>> call, Throwable t) {
                Log.e("OlympiadsFragment", "Ошибка запроса: ", t);            }
        });
    }

    @Override
    public void onItemClick(Olympiad olympiad) {
        Intent intent = new Intent(getContext(), OlympiadDetailActivity.class);
        intent.putExtra("olympiad_id", olympiad.getOlympiad_id());
        startActivity(intent);
    }
}
