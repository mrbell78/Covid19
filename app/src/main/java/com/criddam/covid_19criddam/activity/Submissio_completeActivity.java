package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.model.Post;

public class Submissio_completeActivity extends AppCompatActivity {

    Button btn_addnew,btn_viwlist;
    TextView tv_identifytext;
    Toolbar mToolbaar;
    private static final String TAG = "Submissio_completeActiv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_submissio_complete);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btn_addnew=findViewById(R.id.btn_addnew);
        btn_viwlist=findViewById(R.id.btn_viewlist);

        Post post = new Post();

        Log.d(TAG, "onCreate: ......................data retrive test in successfiled  "+ post.getFullname());

        mToolbaar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");
        tv_identifytext=findViewById(R.id.text_item);

        Intent i = getIntent();
        String value = i.getStringExtra("identify");

        if(value.equals("sp")){
            tv_identifytext.setText("Your submission is completed successfully");
            tv_identifytext.setBackgroundResource(R.drawable.tv_bg);
        }

        btn_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SplashActivity.class));
                finish();
            }
        });

        btn_viwlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DataEntryListctivity.class));
            }
        });


    }
}
