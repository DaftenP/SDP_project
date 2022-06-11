package com.jpl.sdp_project;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

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
    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView recyclerView;

    String edit1 ="타이레놀";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      // itemInfo라는 변수로 새로운 ArrayList<>를 정의해줍니다
        recyclerView =findViewById(R.id.recyclerView);
        //layoutManager: recyclerview에 listview 객체를 하나씩 띄움
        // LayoutManager을 이용하여 RecyclerView를 세팅해줍니다.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();
        retrofitInterface.getData(1, 100, edit1, null, "json").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                Body data = result.getBody();
                Log.d("retrofit", "Data fetch success");
                //Call(요청)에 대한 Response(응답)를 Body data라는 객체에 담았잖아요?
                //그러면 그 Body data 담긴 body part, 즉 List<Data>를 뽑아오기 위해 dataInfo = dataList.body;를 해줍니다.
                //*여기서 response.body()에서의 body()함수와 body를 헷갈리면 안되요!
                //후자의 body는 제가 받아오는 데이터 내에서의 변수명입니다!! ㅠ ㅅ ㅠ
                //Adapter를 이용해서 iteminfo에 있는 내용을 가져와서 저장해둔 listView 형식에 맞게 띄움
                recyclerAdapter = new RecyclerAdapter(data.getItems());
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText text1 = (EditText) findViewById(R.id.Edt1);

        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();
        retrofitInterface.getData(1, 100, edit1, null, "json").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                Body data = result.getBody();
                Log.d("retrofit", "Data fetch success");
                text1.setText(data.getItems().get(0).getItemName());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }


        });


    }
 */
    }
}
