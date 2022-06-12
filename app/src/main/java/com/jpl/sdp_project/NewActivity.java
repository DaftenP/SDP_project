package com.jpl.sdp_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jpl.sdp_project.RecyclerView.RecyclerAdapter;
import com.jpl.sdp_project.retrofit.Body;
import com.jpl.sdp_project.retrofit.Result;
import com.jpl.sdp_project.retrofit.RetrofitClient;
import com.jpl.sdp_project.retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewActivity extends MainActivity{

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView recyclerView;

    protected  void onCreate(@Nullable Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        setContentView(R.layout.new_activity);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();


        retrofitInterface.getData(1, 100, null, null, "json").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                Body data = result.getBody();
                Log.d("retrofit", "Data fetch success");
                recyclerAdapter = new RecyclerAdapter(data.getItems());
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });


    }
}
