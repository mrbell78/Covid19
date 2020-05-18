package com.criddam.covid_19criddam.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        mDatabase= FirebaseDatabase.getInstance().getReference().child("storeroom");

        options = new FirebaseRecyclerOptions.Builder<Firebase_productModel>().setQuery(mDatabase,Firebase_productModel.class).build();

        fadapter= new FirebaseRecyclerAdapter<Firebase_productModel, UserViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewholder userViewholder, int i, @NonNull Firebase_productModel firebase_productModel) {

                userViewholder.tv_name.setText(firebase_productModel.getpName());

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


    public static class UserViewholder extends  RecyclerView.ViewHolder{


        ImageView imageView;
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
        }
    }
}
