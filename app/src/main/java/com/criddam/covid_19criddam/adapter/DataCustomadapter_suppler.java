package com.criddam.covid_19criddam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.model.Data;

import java.util.List;

public class DataCustomadapter_suppler extends RecyclerView.Adapter<DataCustomadapter_suppler.Customclass> {

    Context context;
    List<Data> dataList;

    public DataCustomadapter_suppler(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataCustomadapter_suppler.Customclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itemview,parent,false);

        return new DataCustomadapter_suppler.Customclass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataCustomadapter_suppler.Customclass holder, int position) {

        holder.tv_need.setText(dataList.get(position).getWhat_u_supply());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class Customclass extends RecyclerView.ViewHolder {

        Button btn_delete,btn_edit;
        TextView tv_need;

        public Customclass(@NonNull View itemView) {
            super(itemView);

            btn_delete=itemView.findViewById(R.id.btn_delete);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            tv_need= itemView.findViewById(R.id.tv_need);


        }
    }
}