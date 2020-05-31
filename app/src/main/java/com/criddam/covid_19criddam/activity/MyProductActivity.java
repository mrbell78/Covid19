package com.criddam.covid_19criddam.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.firebasemodel.Firebase_productModel;
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

public class MyProductActivity extends AppCompatActivity {

    private FirebaseRecyclerOptions<Firebase_productModel> options;
    private FirebaseRecyclerAdapter<Firebase_productModel, MyProductActivity.UserViewholder> fadapter;

    RecyclerView recyclerView;
    DatabaseReference mDatabase;
    FirebaseUser mUser;
    String userId;

    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);

        mToolbar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Published Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recylerview_publishproduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();



        mUser= FirebaseAuth.getInstance().getCurrentUser();
        userId=mUser.getUid();
        Log.d("userid", "onStart: -----userid "+ userId);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("storeroom").child(userId);
        mDatabase.keepSynced(true);

        Query mqury = mDatabase.getRef();
       // Log.d("TAG", "onStart: ..........................userkey "+ mDatabase.);
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

                userViewholder.tv_name.setText(firebase_productModel.getpName());
                userViewholder.tv_details.setText(firebase_productModel.getpDetails());
                Picasso.with(MyProductActivity.this).load(firebase_productModel.getThum1()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage)
                        .into(userViewholder.img1);


                Picasso.with(MyProductActivity.this).load(firebase_productModel.getThum2()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage)
                        .into(userViewholder.img2);


                Picasso.with(MyProductActivity.this).load(firebase_productModel.getThum3()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage)
                        .into(userViewholder.img3);


                Picasso.with(MyProductActivity.this).load(firebase_productModel.getThum4()).placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage)
                        .into(userViewholder.img4);

                Log.d("TAG", "onBindViewHolder: ................user product name  "+ firebase_productModel.getpName());
                String key  = getRef(i).getKey();
                Log.d("TAG", "onBindViewHolder: ................refer eky  "+ key );

                userViewholder.tv_model.setText(firebase_productModel.getpModel());
                userViewholder.tv_price.setText(firebase_productModel.getpPrice());
            }

            @NonNull
            @Override
            public UserViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.firebase_productitem,parent,false);
                return new UserViewholder(view);
            }
        };
        fadapter.startListening();
        recyclerView.setAdapter(fadapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),SupplierActivity.class));
    }

    public  static class UserViewholder extends  RecyclerView.ViewHolder{


        ImageView img1,img2,img3,img4;
        TextView tv_name,tv_details,tv_model,tv_area,tv_price;
        Button details;
        View mview;
        public UserViewholder(@NonNull View itemView) {
            super(itemView);
            tv_name= itemView.findViewById(R.id.tv_pname);
            tv_details= itemView.findViewById(R.id.tv_pDetails);
            tv_model=itemView.findViewById(R.id.tv_pModel);
            tv_area=itemView.findViewById(R.id.tv_pArea);
            tv_price=itemView.findViewById(R.id.tv_pPrice);

            img1=itemView.findViewById(R.id.img1);
            img2=itemView.findViewById(R.id.img2);
            img3=itemView.findViewById(R.id.img3);
            img4=itemView.findViewById(R.id.img4);
        }
    }
}
