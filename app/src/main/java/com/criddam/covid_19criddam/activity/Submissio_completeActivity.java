package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Getdata_doctor;
import com.criddam.covid_19criddam.model.Post;
import com.criddam.covid_19criddam.utils.DatabaseHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Submissio_completeActivity extends AppCompatActivity {

    Button btn_addnew,btn_viwlist;
    TextView tv_identifytext;
    Toolbar mToolbaar;
    String value=null;
    private static final String TAG = "Submissio_completeActiv";
    DatabaseHelper db;
    String mobile;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    String type_dp=null;
    String usertypeglobal=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_submissio_complete);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btn_addnew=findViewById(R.id.btn_addnew);
        btn_viwlist=findViewById(R.id.btn_viewlist);


        pref = Submissio_completeActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        usertypeglobal = pref.getString("type", null);


        Post post = new Post();

        Log.d(TAG, "onCreate: ......................data retrive test in successfiled  "+ post.getFullname());

        mToolbaar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");
        tv_identifytext=findViewById(R.id.text_item);

        Intent i = getIntent();
        value = i.getStringExtra("type");
        mobile=i.getStringExtra("mobile");

        Log.d(TAG, "onCreate: ................outside ev "+value);

        if(usertypeglobal.equals("supplier")){

            Log.d(TAG, "onCreate: .....................vaue of user type text modify "+value);
            tv_identifytext.setText("Your submission is completed successfully");
            tv_identifytext.setBackgroundResource(R.drawable.tv_bg);
        }

        btn_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usertypeglobal.equals("supplier")){

                    startActivity(new Intent(getApplicationContext(),SupplierActivity.class));
                    finish();


                    Log.d(TAG, "onCreate: .....................vaue of user type doctor ui "+value);

                      }else if(usertypeglobal.equals("patient") || usertypeglobal.equals("doctor")){
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                }
            }
        });

        btn_viwlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(),DataEntryListctivity.class)
                        .putExtra("mobile",mobile).putExtra("value",value));

                /*if(value!=null){
                    startActivity(new Intent(getApplicationContext(),DataEntryListctivity.class).putExtra("valu",value));
                }else {
                    startActivity(new Intent(getApplicationContext(),DataEntryListctivity.class).putExtra("value",value));
                }*/

    }
});


    }
}
