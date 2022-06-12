package com.jpl.sdp_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

    //필드 정의 부분
    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView recyclerView;

    //액티비티 생성자
    protected  void onCreate(@Nullable Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        setContentView(R.layout.new_activity);

        //지역변수
        Context context = this; //리사이클러뷰에 전달할 Context 객체
        Intent intent = getIntent();
        String txt1 = intent.getStringExtra("TEXT");

        //리사이클러뷰 객체
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        //retrofit 객체
        recyclerView.setLayoutManager(layoutManager);
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();



        retrofitInterface.getData(1, 100, null, txt1, "json").enqueue(new Callback<Result>() {  //json request
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {  //request success
                Result result = response.body();
                Body data = result.getBody();
                Log.d("retrofit", "Data fetch success");
                recyclerAdapter = new RecyclerAdapter(context,data.getItems());
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) { //request failure
                Log.d("retrofit", t.getMessage());
            }
        });


    }
}
