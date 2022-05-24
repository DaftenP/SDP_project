package com.jpl.sdp_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jpl.sdp_project.functions.OpenAPI;

public class MainActivity extends AppCompatActivity {

    String edit1 = "타이레놀";

    //ArrayList<Medicine> arrayList = new ArrayList<Medicine>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //arrayList = ;
        TextView text1 = (TextView) findViewById(R.id.txt1);
        text1.setText(OpenAPI.getXML(edit1).get(0).ingr_name);
    }

}