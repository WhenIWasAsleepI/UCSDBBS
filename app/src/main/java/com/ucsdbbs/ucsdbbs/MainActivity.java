package com.ucsdbbs.ucsdbbs;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;



public class MainActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);


        radioGroup.check(R.id.viewradio);
        replace(new ViewFragment());//默认选第一个

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                replace(FragmentFactory.getInstanceByIndex(checkedId));
            }
        });




    }

    private void replace(Fragment fragment) {//替换当前容器中的Fragment
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.content, fragment);
        beginTransaction.commit();
    }

}

