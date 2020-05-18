package com.criddam.covid_19criddam.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.criddam.covid_19criddam.model.Data;

import java.util.List;

public class MydiffUtill extends DiffUtil.Callback {

    List<Data> oldList;
    List<Data> newList;

    public MydiffUtill(List<Data> oldList, List<Data> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition==newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition)== newList.get(newItemPosition);
    }
}
