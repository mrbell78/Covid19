package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;

public class MainActivity extends AppCompatActivity {


    Button btn_next,btn_previous;
    TextView edtitemtext,forwho;
    Toolbar mToolbaar;
    EditText edt_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);





        btn_next=findViewById(R.id.btn_add);
        btn_previous=findViewById(R.id.btn_previous);

        edt_item=findViewById(R.id.edt_item);

        mToolbaar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");





        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_item.getText().toString())){
                    startActivity(new Intent(getApplicationContext(),DoctorEmergencyActivity.class).putExtra("docneed",edt_item.getText().toString()).putExtra("type","doc/patient"));



                }else {
                    Toast.makeText(MainActivity.this, "Please mention what you need", Toast.LENGTH_SHORT).show();
                    edt_item.setError("Please mention what you need ");
                }
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SplashActivity.class));
                finish();
            }
        });


    }
}
