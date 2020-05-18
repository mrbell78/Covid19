package com.criddam.covid_19criddam.tab;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.activity.DoctorRequestActivity;
import com.criddam.covid_19criddam.adapter.Adapter_alluser;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Alluserdata;
import com.criddam.covid_19criddam.model.Alluserdataresponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Doctorend extends Fragment {

    RecyclerView recyclerView;
    Api_covid api_covid;
    List<Alluserdata> alluser;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.doctorend,container,false);

        recyclerView=view.findViewById(R.id.recylerviuew);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return  view;
                //inflater.inflate(R.layout.doctorend,container,false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        alluser= new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());

        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();

        getdata();

    }

      private void getdata() {

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api_covid = retrofit.create(Api_covid.class);

        Call<Alluserdataresponse> call = api_covid.getalluser();

        call.enqueue(new Callback<Alluserdataresponse>() {
            @Override
            public void onResponse(Call<Alluserdataresponse> call, Response<Alluserdataresponse> response) {

               if(!response.isSuccessful()){

                   Log.d("demo", "onResponse: ..................error alluserdata "+ response.code());
               }

                List<Alluserdata> alluserdata = response.body().getAlluserdataList();


                for(Alluserdata d : alluserdata){

                    String value = d.getType();

                      if(d.getType()!=null && d.getType().equals("supplier")){
                          Alluserdata modifedata = new Alluserdata(d.getSl(),d.getId(),d.getWhat_u_need(),d.getHow_soon_do_u_need_it(),d.getWhat_u_supply(),d.getWhat_u_supply_other(),
                                  d.getHow_soon_can_u_supply(),d.getHospital(),d.getLocation(),d.getName(),d.getMobile(),d.getEmail(),d.getType());
                          alluser.add(modifedata);
                          Log.d("demotest", "onResponse: ................alll user data  "+ value);

                      }




                }

                Adapter_alluser adapter = new Adapter_alluser(getContext(),alluser);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Alluserdataresponse> call, Throwable t) {

                Log.d("failed", "onFailure: ..................failed all user "+ t.getMessage());

            }
        });

    }
}
