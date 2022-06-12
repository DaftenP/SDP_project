package com.jpl.sdp_project;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.jpl.sdp_project.RecyclerView.RecyclerAdapter;
import com.jpl.sdp_project.retrofit.Body;
import com.jpl.sdp_project.retrofit.Result;
import com.jpl.sdp_project.retrofit.RetrofitClient;
import com.jpl.sdp_project.retrofit.RetrofitInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //필드 정의 부분
    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView recyclerView;

    Bitmap image; //사용되는 이미지
    private TessBaseAPI mTess; //Tess API reference
    String datapath = "" ; //언어데이터가 있는 경로

    ImageButton btn_picture; //사진 찍는 버튼
    Button btn_ocr; //텍스트 추출 버튼
    ImageView img01;
    Bitmap bitmap;

    //액티비티 생성자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //언어파일 경로
        datapath = getFilesDir()+ "/tesseract/";

        //트레이닝데이터가 카피되어 있는지 체크
        checkFile(new File(datapath + "tessdata/"), "kor");
        checkFile(new File(datapath + "testate/"), "eng");

        String lang = "kor+eng";

        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);

        //지역변수
        Context context = this; //리사이클러뷰에 전달할 Context 객체
        EditText text1 = (EditText) findViewById(R.id.Edt1);
        ImageButton btn1 = (ImageButton) findViewById(R.id.Btn1);
        LinearLayout layout01 = (LinearLayout) findViewById(R.id.layout01);
        btn_picture = (ImageButton) findViewById(R.id.Btn2);
        btn_ocr = (Button) findViewById(R.id.Btn3);
        img01 = findViewById(R.id.img01);

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
                layout01.setVisibility(View.GONE);
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


        // 사진 찍는 버튼 클릭시 카메라 킴
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultPicture.launch(intent);
                layout01.setVisibility(View.VISIBLE);
            }
        });
        btn_ocr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // 가져와진 사진을 bitmap으로 추출
                BitmapDrawable d = (BitmapDrawable)((ImageView) findViewById(R.id.img01)).getDrawable();
                image = d.getBitmap();

                String OCRresult = null;
                mTess.setImage(image);

                //텍스트 추출
                OCRresult = mTess.getUTF8Text();
                text1.setText(OCRresult);
                layout01.setVisibility(View.GONE);
            }
        });


    }
    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    //결과 OK , 데이터 null 아니면
                    if( result.getResultCode() == RESULT_OK && result.getData() != null){

                        Bundle extras = result.getData().getExtras();

                        bitmap = (Bitmap) extras.get("data");
                        img01.setImageBitmap(bitmap);
                    }

                }
            });

    private void copyFiles(String lang) {
        try{
            //파일이 있을 위치
            String filepath = datapath + "/tessdata/"+lang+".traineddata";

            //AssetManager에 액세스
            AssetManager assetManager = getAssets();

            //읽기/쓰기를 위한 열린 바이트 스트림
            InputStream instream = assetManager.open("tessdata/"+lang+".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //filepath에 의해 지정된 위치에 파일 복사
            byte[] buffer = new byte[1024];
            int read;

            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void checkFile(File dir, String lang) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang);
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            String datafilepath = datapath + "/tessdata/" + lang + ".traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(lang);
            }
        }

    }
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}