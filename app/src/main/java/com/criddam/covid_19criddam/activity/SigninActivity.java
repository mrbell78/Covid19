package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Data;
import com.criddam.covid_19criddam.model.Post;
import com.criddam.covid_19criddam.model.Responseclass;
import com.criddam.covid_19criddam.model.Signin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class SigninActivity extends AppCompatActivity {

    TextView tv_clickreg;
    String type,docneed,emergencyfordoc;
    String supplyitem,othersupply,emergencyforsupply;

    Button btn_sumbit;
    Api_covid api_covid;

    private static final String TAG = "SigninActivity";

    EditText edt_mobile,edt_password;

    ProgressDialog mdilaog;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    CheckBox cv;
    String user=null;
    String mb=null;
    String pass=null;
    String type_dp=null;

    LinearLayout linearLayout;


    List<Data> usrdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        tv_clickreg=findViewById(R.id.clikforreg);
        btn_sumbit=findViewById(R.id.signin);

        cv=findViewById(R.id.remember);

        linearLayout=findViewById(R.id.lparentlayout);
        usrdata=new ArrayList<>();



        Intent intent = getIntent();
        supplyitem  = intent.getStringExtra("sp_need");
        emergencyforsupply = intent.getStringExtra("time");
        othersupply = intent.getStringExtra("othersply");

        type= intent.getStringExtra("type");
        docneed=intent.getStringExtra("docneed");
        emergencyfordoc=intent.getStringExtra("emergency");

        edt_mobile=findViewById(R.id.edt_mobile);
        edt_password=findViewById(R.id.edt_password);
        mdilaog= new ProgressDialog(this);
        mdilaog.setTitle("Sing in");
        mdilaog.setMessage("Please wait");


        Log.d(TAG, "onCreate: ..............usertype chekck "+ type);

      cv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              if(cv.isChecked() ){

                  if( !TextUtils.isEmpty(edt_mobile.getText().toString() )
                          && !TextUtils.isEmpty(edt_password.getText().toString())){

                      pref = SigninActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                      editor = pref.edit();
                      editor.putString("mobile",edt_mobile.getText().toString()); // Storing string
                      editor.putString("password",edt_password.getText().toString());
                      editor.commit();

                      Toast.makeText(SigninActivity.this, "Remembered", Toast.LENGTH_SHORT).show();
                  }




              }


          }
      });

        pref = SigninActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
         mb = pref.getString("mobile", null);
         pass = pref.getString("password", null);
        type_dp=pref.getString("type",null);
        Log.d(TAG, "onCreate: ........................... value "+ mb);

        if(mb!=null && pass!=null && type_dp!=null){

            Retrofit retrofit  = new Retrofit.Builder()
                    .baseUrl("http://sales.criddam.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api_covid = retrofit.create(Api_covid.class);


            linearLayout.setVisibility(View.INVISIBLE);

            signIn(mb,pass);
            mdilaog.show();

        }





        tv_clickreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class).putExtra("type",type)
                        .putExtra("docneed",docneed).putExtra("emergency",emergencyfordoc)

                );
                }
        });

        btn_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(edt_mobile.getText().toString()) && !TextUtils.isEmpty(edt_password.getText()
                .toString()
                )){

                    Retrofit retrofit  = new Retrofit.Builder()
                            .baseUrl("http://sales.criddam.com/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    api_covid = retrofit.create(Api_covid.class);


                    signIn(edt_mobile.getText().toString(),edt_password.getText().toString());



                    mdilaog.show();
                }else {


                }
            }
        });
    }

    private void signIn(String mobile,String password) {

        Signin signin = new Signin(mobile,password);

        Call<ResponseBody> call = api_covid.createPost_Signin(mobile,password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: ..............unSuccessful "+response.code());

                }

                String s = null;

                try {


                    if(response.code()==401){
                        s=response.errorBody().string();

                        Log.d(TAG, "onResponse: .................resposne by serveer in singin while error page "+ s);
                        Log.d(TAG, "onResponse: .................resposne by serveer code "+ response.code());
                    }else {
                        s = response.body().string();
                        Log.d(TAG, "onResponse: .................resposne by serveer in singin while successpage "+ s);
                        Log.d(TAG, "onResponse: .................resposne by serveer code "+ response.code());



                    }
                } catch (IOException e) {
                    e.printStackTrace();


                }

                if(s!=null){

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String success= jsonObject.getString("msg");

                        if(success.equals("Login Successfull!")){


                            Log.d(TAG, "onResponse: ...............user must check "+ type);
                            Toast.makeText(SigninActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            pref = SigninActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                            editor = pref.edit();
                            editor.putString("userexistancy", "userexist"+type); // Storing string
                            editor.commit();

                            getdata(mobile);

                            //mdilaog.dismiss();

                           // retrivedata(mobile);
                            //pusdata(docneed,emergencyfordoc);

                        }else {
                            mdilaog.dismiss();
                            Toast.makeText(SigninActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            edt_mobile.setError("Error");
                            edt_password.setError("Error");
                        }

                        //
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure:  ............."+t.getMessage());

            }
        });
    }

    private void getdata(String mobile) {

        Api_covid api;

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/show_my_data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api_covid.class);

        Call<Responseclass> call = api.getData(mobile);



        call.enqueue(new Callback<Responseclass>() {
            @Override
            public void onResponse(Call<Responseclass> call, Response<Responseclass> response) {

                String usertype=null,name=null,email=null,mobile=null,password=null,hospital=null,location=null;
                if(!response.isSuccessful()){

                    Log.d(TAG, "onResponse: ................error "+response.code());
                }



                List<Data> data =response.body().getData();
                for(Data d : data){
                    usertype=d.getUsertype();
                    name=d.getFullname();
                    mobile=d.getMobile();
                    email=d.getEmail();
                    password=d.getPassword();
                    hospital=d.getHospital();
                    location=d.getLocation();


                }


                if( mobile!=null){

                    insertdata(usertype,name,mobile,email,docneed,emergencyfordoc,password,hospital,

                            location
                    );


                    Log.d(TAG, "onResponse: .................inserdata() method is called "+ mobile);
                }else {
                    Toast.makeText(SigninActivity.this, "inner class null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Responseclass> call, Throwable t) {

                Log.d(TAG, "onFailure: .........fail "+t.getMessage());

            }
        });




    }

    private void insertdata(String usertype, String fullname, String mobile, String email, String what_u_need,String how_soon_do_u_need_it, String password, String hospital, String location) {

        Api_covid apinew;
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

                apinew= retrofit.create(Api_covid.class);


        Call<ResponseBody> call = apinew.createPost(usertype, fullname, mobile, email, what_u_need, how_soon_do_u_need_it, password, hospital, location);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                String s=null;

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: ..............unSuccessful " + response.code());
                    mdilaog.dismiss();
                    Toast.makeText(SigninActivity.this, "server error", Toast.LENGTH_SHORT).show();
                }


                Toast.makeText(SigninActivity.this, "data update successfully", Toast.LENGTH_SHORT).show();

                try {
                    s = response.body().string();

                    mdilaog.dismiss();
                    startActivity(new Intent(getApplicationContext(),Submissio_completeActivity.class).putExtra("type","Doctor").putExtra("mobile",mobile));

                    Log.d(TAG, "onResponse: .................resposne by serveer code in singn in method  " + response.code());
                    Log.d(TAG, "onResponse: .................resposne by serveer code in singn in method  response body " + s);


                } catch (IOException e) {
                    e.printStackTrace();
                }






            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }




}
