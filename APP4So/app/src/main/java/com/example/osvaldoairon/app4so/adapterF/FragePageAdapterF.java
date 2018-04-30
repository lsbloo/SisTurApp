package com.example.osvaldoairon.app4so.adapterF;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.osvaldoairon.app4so.Fragments.FragmentInfCidade;
import com.example.osvaldoairon.app4so.Fragments.FragmentInfTurismo;

public class FragePageAdapterF extends FragmentStatePagerAdapter {

    public String[] titulos;

    public FragePageAdapterF(FragmentManager fm , String[] tab_titulos) {
        super(fm);
        this.titulos=tab_titulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Log.d("akpsakps" , "oks");
                return new FragmentInfTurismo();

            case 1:
                return new FragmentInfCidade();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titulos[position];
    }
}
