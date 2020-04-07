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

import java.util.ArrayList;

public class SupplierEmergencyActivity extends AppCompatActivity {

    Button btn_next,btn_previous;
    EditText edt_time;
    Toolbar mToolbaar;
    String  value,other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_emergency);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        btn_previous=findViewById(R.id.btn_sprevious);

        mToolbaar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");
        btn_next=findViewById(R.id.btn_snext);
        edt_time=findViewById(R.id.edt_sptime);

        Intent intent = getIntent();
        value = intent.getStringExtra("suppy_nedd");
        other = intent.getStringExtra("othersuply");

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!TextUtils.isEmpty(edt_time.getText().toString())){
                   Toast.makeText(SupplierEmergencyActivity.this, value, Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(getApplicationContext(),ResistrationSupplierActivity.class).putExtra("sp_need",value)
                           .putExtra("time",edt_time.getText().toString()).putExtra("othersply",other));
                   finish();
               }else{
                   edt_time.setError("Please mention your urgency ");
                   Toast.makeText(SupplierEmergencyActivity.this, "Please mention your urgency here", Toast.LENGTH_SHORT).show();
               }
        }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SupplierActivity.class));
                finish();
            }
        });
    }
}
