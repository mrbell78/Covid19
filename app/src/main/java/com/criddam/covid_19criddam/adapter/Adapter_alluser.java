package com.criddam.covid_19criddam.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.activity.DetailssupplierActivity;
import com.criddam.covid_19criddam.model.Alluserdata;

import java.io.Serializable;
import java.util.List;

public class Adapter_alluser extends RecyclerView.Adapter<Adapter_alluser.CustomclassAlluser> {

    Context context;

    List<Alluserdata> data;

    public Adapter_alluser(Context context, List<Alluserdata> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CustomclassAlluser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.doctorreques_itemview,parent,false);
        return new CustomclassAlluser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomclassAlluser holder, int position) {


        holder.name.setText(data.get(position).getName());
        holder.item.setText(data.get(position).getWhat_u_supply());
        holder.location.setText(data.get(position).getLocation());
        holder.suppliother.setText(data.get(position).getWhat_u_supply_other());

        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                context.startActivity(new Intent(context, DetailssupplierActivity.class)
                                .putExtra("mobile", data.get(position).getMobile())
                        .putExtra("item",data.get(position).getWhat_u_supply())
                        .putExtra("otheritem",data.get(position).getWhat_u_supply_other())
                        .putExtra("location",data.get(position).getLocation())
                );

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomclassAlluser extends RecyclerView.ViewHolder {

        TextView name,item,location,suppliother;
        Button btn_details;

        public CustomclassAlluser(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.username);
            item=itemView.findViewById(R.id.item_slsit);
            location=itemView.findViewById(R.id.item_slocation);
            suppliother = itemView.findViewById(R.id.suppliother);
            btn_details=itemView.findViewById(R.id.details_sitem);
        }
    }
}
