package com.jpl.sdp_project;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    //필드 정의 부분
    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView recyclerView;


    //액티비티 생성자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //지역변수
        Context context = this; //리사이클러뷰에 전달할 Context 객체
        EditText text1 = (EditText) findViewById(R.id.Edt1);
        ImageButton btn1 = (ImageButton) findViewById(R.id.Btn1);

        //리사이클러뷰 객체
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //retrofit 객체
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        //검색버튼 OnClickListener
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit1 = text1.getText().toString();  //검색어 추출
                retrofitInterface.getData(1, 100, edit1, null, "json").enqueue(new Callback<Result>() { //json request
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {  //request success
                        Result result = response.body();
                        Body data = result.getBody();
                        Log.d("retrofit", "Data fetch success");
                        recyclerAdapter = new RecyclerAdapter(context, data.getItems());
                        recyclerView.setAdapter(recyclerAdapter);

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) { //request failure
                        Log.d("retrofit", t.getMessage());
                    }
                });
            }
        });
    }
}