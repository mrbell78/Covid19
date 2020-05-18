package com.criddam.covid_19criddam.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.criddam.covid_19criddam.tab.Doctorend;
import com.criddam.covid_19criddam.tab.Doctororderend;
import com.criddam.covid_19criddam.tab.DoctorrequestitemFragment;
import com.criddam.covid_19criddam.tab.SubmititemFragment;

public class AdapterSupplierfragment extends FragmentStateAdapter {


    public AdapterSupplierfragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 0:
                return new DoctorrequestitemFragment();
            case 1:
                return new SubmititemFragment();
            default:return null;
        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
