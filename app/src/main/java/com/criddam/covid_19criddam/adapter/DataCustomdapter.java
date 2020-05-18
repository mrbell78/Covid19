package com.criddam.covid_19criddam.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.activity.MainActivity;
import com.criddam.covid_19criddam.activity.RegistrationActivity;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Data;
import com.criddam.covid_19criddam.model.DataResponseback;
import com.criddam.covid_19criddam.utils.MydiffUtill;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataCustomdapter extends RecyclerView.Adapter<DataCustomdapter.Customclass> {

   Context context;
   List<Data> dataList;
   boolean status = false ;


    private static final String TAG = "DataCustomdapter";

    public DataCustomdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    public void updateData(List<Data> newlist){


        MydiffUtill mydiffUtill = new MydiffUtill(dataList,newlist);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mydiffUtill);

        dataList.clear();
        dataList.addAll(newlist);
        diffResult.dispatchUpdatesTo(this);


    }

    @NonNull
    @Override
    public Customclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.itemview,parent,false);

        return new Customclass(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Customclass holder, int position) {

        holder.tv_need.setText(dataList.get(position).getWhat_u_need());
        holder.location.setText(dataList.get(position).getHow_soon_do_u_need_it());

        Log.d(TAG, "onClick: ...........position of list "+dataList.size());


        AlertDialog.Builder albuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.itemlike_doctor,null);

        albuilder.setView(view);

        Log.d(TAG, "onBindViewHolder: ....................status "+ status);

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyItemChanged(holder.getAdapterPosition());
                final AlertDialog dialog = albuilder.create();
                dialog.show();

                Button btn_update,btn_cancel;
                EditText edt_need,edt_soon;

                edt_need=view.findViewById(R.id.edtdilog_need);
                edt_soon=view.findViewById(R.id.edtdilog_soon);

                edt_need.setText(dataList.get(position).getWhat_u_need());
                edt_soon.setText(dataList.get(position).getHow_soon_do_u_need_it());

                btn_update=view.findViewById(R.id.btn_dupdate);
                //btn_cancel=view.findViewById(R.id.btn_dcancel);


              /*  btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });*/


                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        update(position,dataList.get(position).getId(),edt_need.getText().toString(),edt_soon.getText().toString(),holder,dialog,edt_need,edt_soon);


                    }
                });

                //update(position,dataList.get(position).getId(),holder.edt_update,holder.btn_update,holder);



            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to Delete this item")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
/*
                                if(dataList.size()==1){
                                    Toast.makeText(context, "last position last list ", Toast.LENGTH_SHORT).show();
                                }else {

                                }*/
                                delteItem(position,dataList.get(position).getId(),holder);

                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_warning)
                        .show();
            }
        });

    }






    private void delteItem(int position, int id,DataCustomdapter.Customclass holder) {

        Api_covid apinew;
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/delete/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apinew= retrofit.create(Api_covid.class);

        Call<Void> call = apinew.delete(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                dataList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),dataList.size());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void update(int classposition,int position, String need,String soon, Customclass holder,AlertDialog dialog,EditText edneed,EditText edsoon) {

        DataResponseback dataResponseback = new DataResponseback(position,need,soon);
        Api_covid apinew;
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/update_what_u_need/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apinew= retrofit.create(Api_covid.class);
        Call<ResponseBody>call = apinew.update(position,need,soon);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: ..............error adapter "+ response.code());
                }

                dataList.get(classposition).setWhat_u_need(edneed.getText().toString());
                dataList.get(classposition).setHow_soon_do_u_need_it(edsoon.getText().toString());
                notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return  dataList!=null ? dataList.size() : 0;
    }

    public class Customclass extends RecyclerView.ViewHolder {

        Button btn_delete,btn_edit,btn_update;
        TextView tv_need,tv_needOther,location;
        EditText edt_update;
        LinearLayout layout,layout_update;


        public Customclass(@NonNull View itemView) {
            super(itemView);

            btn_delete=itemView.findViewById(R.id.btn_delete);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            tv_need= itemView.findViewById(R.id.tv_need);
           // tv_needOther=itemView.findViewById(R.id.tv_other_needd);
            location= itemView.findViewById(R.id.tv_Location);
            edt_update=itemView.findViewById(R.id.edt_update);
            btn_update=itemView.findViewById(R.id.btn_update);
            layout=itemView.findViewById(R.id.llayoutsolid);
            layout_update=itemView.findViewById(R.id.udateLayout);


        }
    }
}
