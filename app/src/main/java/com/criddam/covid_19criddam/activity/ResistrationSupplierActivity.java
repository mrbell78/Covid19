package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Post;
import com.criddam.covid_19criddam.model.Post_supply;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResistrationSupplierActivity extends AppCompatActivity {

    Button btn_submit,btn_cancel;
    Toolbar mToolbaar;
    EditText edt_name,edt_mobile,edt_email,edt_hospital,edt_location,edt_password;
    Api_covid api;
   // ArrayList<String> value =new ArrayList<>();
    String value_cn;
    String sptime;
    String othersupply;
    ProgressDialog mdialog;

    private static final String TAG = "ResistrationSupplierAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistration_supplier);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        edt_name=findViewById(R.id.fullname);
        edt_mobile=findViewById(R.id.mobile);
        edt_email=findViewById(R.id.email);
        edt_hospital=findViewById(R.id.hospital);
        edt_location=findViewById(R.id.location);
        edt_password=findViewById(R.id.password);

        mToolbaar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbaar);

        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_submit=findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        value_cn  = intent.getStringExtra("sp_need");
        sptime = intent.getStringExtra("time");
        othersupply = intent.getStringExtra("othersply");

        mdialog= new ProgressDialog(this);
        mdialog.setTitle("Create Account");
        mdialog.setMessage("Please wait....");



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(edt_name.getText().toString())
                        && !TextUtils.isEmpty(edt_mobile.getText().toString())
                        && !TextUtils.isEmpty(edt_location.getText().toString())
                        && !TextUtils.isEmpty(edt_password.getText().toString())

                ){

                    Retrofit retrofit  = new Retrofit.Builder()
                            .baseUrl("http://sales.criddam.com/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    api = retrofit.create(Api_covid.class);
                    createPost("supplier",edt_name.getText().toString(),edt_mobile.getText().toString(),edt_email.getText().toString(),value_cn,sptime,
                            edt_password.getText().toString(),edt_location.getText().toString(),othersupply);
                    mdialog.show();

                }

                else if(TextUtils.isEmpty(edt_name.getText().toString())){
                    edt_name.setError("You must have to fill up this filed");
                    Toast.makeText(ResistrationSupplierActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                }


                else if(TextUtils.isEmpty(edt_mobile.getText().toString())){
                    edt_mobile.setError("You must have to fill up this filed");
                    Toast.makeText(ResistrationSupplierActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                }




                else if(TextUtils.isEmpty(edt_location.getText().toString())){
                    edt_location.setError("You must have to fill up this filed");
                    Toast.makeText(ResistrationSupplierActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                };
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ResistrationSupplierActivity.this)
                        .setTitle("Cancel Registration")
                        .setMessage("Are you sure you want to Cancel the registration?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                startActivity(new Intent(getApplicationContext(),SupplierActivity.class));
                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_warning)
                        .show();
            }
        });
    }

    private void createPost(String supplyer, String name, String mobile, String email, String value_cn, String sptime, String password, String location,String othersupply) {


        Post_supply response = new Post_supply(supplyer,name,mobile,email,value_cn, sptime,password,location,othersupply);
        Call<ResponseBody> call = api.createPost_supply(supplyer,name,mobile,email,value_cn, sptime,password,location,othersupply);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: ..............unSuccessful "+response.code());
                    mdialog.dismiss();
                    Toast.makeText(ResistrationSupplierActivity.this, "server error", Toast.LENGTH_SHORT).show();

                }
                String s =null;
                try {


                    s = response.body().string();
                    Log.d(TAG, "onResponse: .................resposne by serveer "+ s);
                    Log.d(TAG, "onResponse: .................resposne by serveer code "+ response.code());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        String status = jsonObject.getString("msg");
                        if (status.equals("Mobile number 01762957422 already exists in server.")) {
                            mdialog.dismiss();
                            Toast.makeText(ResistrationSupplierActivity.this, "Mobile number 01762957422 already exists in server.", Toast.LENGTH_SHORT).show();
                            edt_mobile.setError("User exist");
                        } else {
                            mdialog.dismiss();
                            Toast.makeText(ResistrationSupplierActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Submissio_completeActivity.class).putExtra("type","supplier").putExtra("mobile",mobile));
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }



   /* private void createPost(String name,String password,String mobile,String email,String need,String location) {

        Call<Post> call = api.createPost_supply(name,password,mobile,email,need, location);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: ..............unSuccessful "+response.code());
                }

                Post postrespone = response.body();
                Log.d(TAG, "onResponse: .........successful "+ response.code());
                Log.d(TAG, "onResponse: .....................data retrive "+ postrespone.getMobile());
                Toast.makeText(ResistrationSupplierActivity.this, "upload successful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Log.d(TAG, "onFailure: ...........error " + t.getMessage());

            }
        });

    }*/

}
