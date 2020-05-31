package com.criddam.covid_19criddam.tab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.activity.DetailssupplierActivity;
import com.criddam.covid_19criddam.activity.DoctorRequestActivity;
import com.criddam.covid_19criddam.activity.MyProductActivity;
import com.criddam.covid_19criddam.adapter.Adapter_alluser;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.firebasemodel.Firebase_productModel;
import com.criddam.covid_19criddam.model.Alluserdata;
import com.criddam.covid_19criddam.model.Alluserdataresponse;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

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

    DatabaseReference mDatabase;
    FirebaseUser mUser;
    String userId;

    private FirebaseRecyclerOptions<Firebase_productModel> options;
    private FirebaseRecyclerAdapter<Firebase_productModel,  UserViewholder> fadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.doctorend,container,false);

        recyclerView=view.findViewById(R.id.recylerviuew);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));






        return  view;
                //inflater.inflate(R.layout.doctorend,container,false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseRecyclerAdapter<Firebase_productModel,  UserViewholder> fadapter;

        alluser= new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());

        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait.....");
        //progressDialog.show();



    }


    public  void showlist(){





    }

    @Override
    public void onStart() {
        super.onStart();


        /*mUser= FirebaseAuth.getInstance().getCurrentUser();
        userId=mUser.getUid();*/


        Log.d("userid", "onStart: -----userid "+ userId);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("publicproduct");
        Query mqury = mDatabase;
        Log.d("TAG", "onStart: ..........................userkey "+ mDatabase.getKey());

        options = new FirebaseRecyclerOptions.Builder<Firebase_productModel>().setQuery(mDatabase, new SnapshotParser<Firebase_productModel>() {
            @NonNull
            @Override
            public Firebase_productModel parseSnapshot(@NonNull DataSnapshot snapshot) {

                return new Firebase_productModel(

                        snapshot.child("pName").getValue().toString(),
                        snapshot.child("pDetails").getValue().toString(),
                        snapshot.child("pModel").getValue().toString(),
                        snapshot.child("pArea").getValue().toString(),
                        snapshot.child("pPrice").getValue().toString(),
                        snapshot.child("img1").getValue().toString(),
                        snapshot.child("img1").getValue().toString(),
                        snapshot.child("img1").getValue().toString(),
                        snapshot.child("img1").getValue().toString(),
                        snapshot.child("thum1").getValue().toString(),
                        snapshot.child("thum2").getValue().toString(),
                        snapshot.child("thum3").getValue().toString(),
                        snapshot.child("thum4").getValue().toString(),
                        snapshot.child("mobile").getValue().toString(),
                        snapshot.child("userName").getValue().toString()

                );





            }
        }).build();

        fadapter= new FirebaseRecyclerAdapter<Firebase_productModel, UserViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewholder userViewholder, int i, @NonNull Firebase_productModel firebase_productModel) {




                Picasso.with(getContext()).load(firebase_productModel.getThum1()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage).into(userViewholder.imageView);
                /*Picasso.with(getContext()).load(firebase_productModel.getThum2()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage).into(userViewholder.imageView);
                Picasso.with(getContext()).load(firebase_productModel.getThum3()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage).into(userViewholder.imageView);
                Picasso.with(getContext()).load(firebase_productModel.getThum4()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage).into(userViewholder.imageView);
*/
                userViewholder.tv_name.setText(firebase_productModel.getpName());
                userViewholder.tv_price.setText(firebase_productModel.getpPrice()+"TK");

                userViewholder.details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), DetailssupplierActivity.class).putExtra("key",getRef(i).getKey().toString()));
                    }
                });
                Log.d("TAG", "onBindViewHolder: ..............recleradapter name "+ firebase_productModel.getpName());
                Log.d("TAG", "onBindViewHolder: ...................rerferkey "+ getRef(i).getKey().toString());
                progressDialog.dismiss();
            }

            @NonNull
            @Override
            public UserViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = getLayoutInflater().inflate(R.layout.firebasepublicproduct,parent,false);
                return new UserViewholder(view);
            }
        };
        fadapter.startListening();
        recyclerView.setAdapter(fadapter);
    }
    public  static class UserViewholder extends  RecyclerView.ViewHolder{


        ImageView imageView;
        TextView tv_name,tv_details,tv_model,tv_area,tv_price;

        Button details;
        View mview;
        public UserViewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_mainpic);
            tv_name=itemView.findViewById(R.id.main_name);
            tv_price=itemView.findViewById(R.id.main_price);
            details=itemView.findViewById(R.id.main_btnprice);
        }
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
