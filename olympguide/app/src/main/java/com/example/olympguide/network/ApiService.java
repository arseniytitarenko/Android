package com.example.olympguide.network;

import com.example.olympguide.models.Olympiad;
import com.example.olympguide.models.OlympiadBenefit;
import com.example.olympguide.models.OlympiadDetail;
import com.example.olympguide.models.ProgramBenefit;
import com.example.olympguide.models.ProgramDetail;
import com.example.olympguide.models.ProgramGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("olympiads")
    Call<List<Olympiad>> getOlympiads();

    @GET("university/1/programs/by-faculty")
    Call<List<ProgramGroup>> getProgramGroups();

    @GET("olympiad/{id}")
    Call<OlympiadDetail> getOlympiadDetail(@Path("id") int id);

    @GET("program/{id}")
    Call<ProgramDetail> getProgramDetail(@Path("id") int id);

    @GET("olympiads/{id}/benefits")
    Call<List<OlympiadBenefit>> getOlympiadBenefits(@Path("id") int olympiadId);

    @GET("programs/{id}/benefits")
    Call<List<ProgramBenefit>> getProgramBenefits(@Path("id") int programId);
}