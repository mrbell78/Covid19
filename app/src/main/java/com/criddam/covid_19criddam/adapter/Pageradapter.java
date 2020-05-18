package com.criddam.covid_19criddam.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.criddam.covid_19criddam.tab.Doctorend;
import com.criddam.covid_19criddam.tab.Doctororderend;

import java.util.ArrayList;
import java.util.List;

public class Pageradapter extends FragmentStateAdapter {


    public Pageradapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 0:
                return new Doctorend();
            case 1:
                return new Doctororderend();
            default:return null;
        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
