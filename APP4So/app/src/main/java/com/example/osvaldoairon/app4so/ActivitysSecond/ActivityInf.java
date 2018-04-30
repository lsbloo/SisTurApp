package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.adapterF.FragePageAdapterF;

public class ActivityInf extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf);
        getSupportActionBar().setHomeButtonEnabled(true);

        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);

        viewPager.setAdapter(new FragePageAdapterF(getSupportFragmentManager(), getResources().getStringArray(R.array.secoes)));

        tabLayout.setupWithViewPager(viewPager);
    }
}
