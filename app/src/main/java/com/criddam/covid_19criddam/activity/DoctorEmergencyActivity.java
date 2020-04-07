package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;

public class DoctorEmergencyActivity extends AppCompatActivity {
    Button btn_next,btn_previous;
    EditText edt_time;

    Toolbar mToolbaar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_emergency);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mToolbaar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");
        btn_next=findViewById(R.id.btn_snext);
        btn_previous=findViewById(R.id.btn_sprevious);
        edt_time=findViewById(R.id.edt_stime);

        Intent intent = getIntent();
        final String value = intent.getStringExtra("docneed");
        String type = intent.getStringExtra("type");



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_time.getText().toString())){
                    startActivity(new Intent(getApplicationContext(),SigninActivity.class).putExtra("supply",value).
                            putExtra("emergency",edt_time.getText().toString()
                    ));
                    finish();
                }else{
                    edt_time.setError("Please mention your urgency here");
                    Toast.makeText(DoctorEmergencyActivity.this, "Please mention your urgency here", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }
}
