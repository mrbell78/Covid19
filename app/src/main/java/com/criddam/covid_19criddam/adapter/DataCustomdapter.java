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

import java.util.List;

public class DataCustomdapter extends RecyclerView.Adapter<DataCustomdapter.Customclass> {

   Context context;
   List<String> datalist;

    public DataCustomdapter(Context context, List<String> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public Customclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itemview,parent,false);

        return new Customclass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Customclass holder, int position) {

        holder.tv_need.setText(datalist.get(position));

    }

    @Override
    public int getItemCount() {
        return datalist.size();
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
