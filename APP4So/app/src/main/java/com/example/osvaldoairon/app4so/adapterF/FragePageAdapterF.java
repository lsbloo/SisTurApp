package com.example.osvaldoairon.app4so.adapterF;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragePageAdapterF extends FragmentStatePagerAdapter {
    public FragePageAdapterF(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
    /*

     public String[] titulos;

    public FragePageAdapterF(FragmentManager fm , String[] tab_titulos) {
        super(fm);
        this.titulos=tab_titulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentInfTurismo();

            case 1:


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
     */


}
