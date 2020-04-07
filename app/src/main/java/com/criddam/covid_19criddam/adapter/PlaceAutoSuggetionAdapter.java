package com.criddam.covid_19criddam.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.criddam.covid_19criddam.utils.Placeapi;

import java.util.ArrayList;

public class PlaceAutoSuggetionAdapter extends ArrayAdapter implements Filterable {

    ArrayList<String> result;
    Context context;
    int resource;
    Placeapi placeapi = new Placeapi();

    public PlaceAutoSuggetionAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.context = context;
        this.resource=resource;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return result.get(position);
    }


    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint!=null){
                    result = placeapi.autoComplete(constraint.toString());

                    filterResults.values = result;
                    filterResults.count = result.size();
                }

                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if(results!=null && results.count>0){
                    notifyDataSetChanged();
                }else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
