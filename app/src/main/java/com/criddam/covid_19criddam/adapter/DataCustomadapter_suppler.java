package com.criddam.covid_19criddam.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Data;
import com.criddam.covid_19criddam.model.DataResponseback;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataCustomadapter_suppler extends RecyclerView.Adapter<DataCustomadapter_suppler.Customclass> {

    Context context;
    List<Data> dataList;

    private static final String TAG = "DataCustomadapter_suppl";
    public DataCustomadapter_suppler(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataCustomadapter_suppler.Customclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itemxmlsupply,parent,false);

        return new DataCustomadapter_suppler.Customclass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataCustomadapter_suppler.Customclass holder, int position) {


        holder.tv_need.setText(dataList.get(position).getWhat_u_supply());
        holder.tv_needOther.setText(dataList.get(position).getWhat_u_supply_other());
        holder.deadline.setText(dataList.get(position).getHow_soon_can_u_supply());


        AlertDialog.Builder albuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_likesupply,null);

        albuilder.setView(view);

     AlertDialog dialog = albuilder.create();

        CheckBox mask,ppe,medicne,equipment,Hand ,saitizer;
        TextView tv_otherclik;
        Button btn_update;
        EditText edt_soon,edt_others;
        LinearLayout llayoutother;

        llayoutother=view.findViewById(R.id.supply_other);
        mask=view.findViewById(R.id.chmask);
        ppe=view.findViewById(R.id.chPPE);
        equipment=view.findViewById(R.id.mequipment);
        saitizer= view.findViewById(R.id.senitaizer);
        medicne=view.findViewById(R.id.medine);
        btn_update=view.findViewById(R.id.btn_update);
        tv_otherclik=view.findViewById(R.id.tv_otherclik);
        edt_soon=view.findViewById(R.id.edt_sptime);
        edt_others=view.findViewById(R.id.edt_supply_other);

      //  Log.d(TAG, "onClick: ...............chekboxvalue in edit mode "+ checkboxvalue);

        String [] matchesall = new String []{
                "Mask","PPE","Medicines","Medicine Equipment","Hand Sanitizer"
        };


        String newdata[]= new String[20];


        tv_otherclik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,200


                );

                params.setMargins(30,8,30,0);


                edt_others.setLayoutParams(params);

                edt_others.setVisibility(View.VISIBLE);
                TranslateAnimation animation = new TranslateAnimation(0,0,llayoutother.getHeight(),0);
                animation.setDuration(100);
                edt_others.startAnimation(animation);

            }
        });


        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(holder.getAdapterPosition());

                dialog.show();

                edt_soon.setText(dataList.get(position).getHow_soon_can_u_supply());
                edt_others.setText(dataList.get(position).getWhat_u_supply_other());

              int i =0 ;

              for(String s: matchesall){
                if(dataList.get(position).getWhat_u_supply()!=null){
                    if(dataList.get(position).getWhat_u_supply().contains(s)){

                        newdata[i]=s;

                        Log.d(TAG, "onClick: ..................newdata "+newdata[i]);
                        i++;


                    }
                }
              }

              if(newdata!=null){
                  for(int j =0;j<newdata.length;j++){

                      if(newdata[j]=="PPE")
                          ppe.setChecked(true);
                      if(newdata[j]=="Mask")
                          mask.setChecked(true);
                      if(newdata[j]=="Medicines")
                          medicne.setChecked(true);

                      if(newdata[j]=="Medicine Equipment")
                          equipment.setChecked(true);

                      if(newdata[j]=="Hand Sanitizer")
                          saitizer.setChecked(true);
                  }
              }


            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkboxvalue  = getchvalue_chekbox(mask,ppe,equipment,saitizer,medicne);


                update(position,dataList.get(position).getId(),checkboxvalue,edt_others.getText().toString(),edt_soon.getText().toString(),holder,dialog,
                       holder.tv_need,holder.tv_needOther,holder.deadline,edt_soon);

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
                                delteItem(position,dataList.get(position).getId(),holder);
                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_warning)
                        .show();

            }
        });





    }


    private String getchvalue_chekbox(CheckBox mask,CheckBox ppe,CheckBox equipment,CheckBox saitizer,CheckBox medicne) {

        String suplyablelist="";
        if(mask.isChecked()){

            suplyablelist=suplyablelist+(mask.getText().toString())+"  ";
        }
        if(ppe.isChecked()){

            suplyablelist=suplyablelist+(ppe.getText().toString())+"  ";
        }
        if(equipment.isChecked()){

            suplyablelist=suplyablelist+(equipment.getText().toString())+"  ";
        }
        if(saitizer.isChecked()){

            suplyablelist=suplyablelist+(saitizer.getText().toString())+"  ";

        }
        if(medicne.isChecked()){

            suplyablelist=suplyablelist+(medicne.getText().toString())+"  ";
        }

        return suplyablelist;


    }

    private void delteItem(int position, int id,DataCustomadapter_suppler.Customclass holder) {

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


    private void update(int classposition, int position, String need,String othersupply, String soon,
                        DataCustomadapter_suppler.Customclass holder, AlertDialog dialog,
                        TextView edneed, TextView edtother,TextView edsoon,EditText urgency) {

        DataResponseback dataResponseback = new DataResponseback(position,need,soon);
        Api_covid apinew;
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/update_what_u_need/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apinew= retrofit.create(Api_covid.class);

        Call<ResponseBody>call = apinew.update_supply(position,need,othersupply,soon);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){

                    Log.d(TAG, "onResponse: ..............error adapter "+ response.code());

                }

                dataList.get(classposition).setWhat_u_supply(need);
                dataList.get(classposition).setWhat_u_supply_other(othersupply);
                dataList.get(classposition).setHow_soon_can_u_supply(urgency.getText().toString());
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
        return dataList!=null ? dataList.size() : 0;
    }

    public class Customclass extends RecyclerView.ViewHolder {

        Button btn_delete,btn_edit,btn_update;
        TextView tv_need,tv_needOther,deadline;
        EditText edt_update;
        LinearLayout layout,layout_update;


        public Customclass(@NonNull View itemView) {
            super(itemView);

            btn_delete=itemView.findViewById(R.id.btn_delete);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            tv_need= itemView.findViewById(R.id.tv_need_supply);
            tv_needOther=itemView.findViewById(R.id.tv_need_supply_other);
            deadline = itemView.findViewById(R.id.tv_Location);
            btn_update=itemView.findViewById(R.id.btn_update);




        }
    }
}