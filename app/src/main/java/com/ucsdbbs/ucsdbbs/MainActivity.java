package com.ucsdbbs.ucsdbbs;


import android.app.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private ImageButton newpost;
    private Global global;
    private final static int DIALOG=1;
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

        newpost=(ImageButton)findViewById(R.id.NewThread);
        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application application = (Application) Global.getContext();
                global = (Global) application;
                if (!global.getloginstatus()) {
                    Toast.makeText(getBaseContext(), "您尚未登录，请登录后再发表帖子", Toast.LENGTH_SHORT).show();
                    radioGroup.check(R.id.personalradio);
                    replace(new PersonalFragment());
                } else {
                    //SelectForumDialog dialog = new SelectForumDialog();
                    //dialog.show(fragmentManager, "dialog");
                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), NewthreadActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            }
        });


    }

    private void replace(Fragment fragment) {//替换当前容器中的Fragment
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.content, fragment);
        beginTransaction.commit();
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //if(imm.isActive())return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        //else return true;
        //return true;
        final View activityRootView = getWindow().getDecorView().getRootView();
        final View haha=(View)findViewById(R.id.);
        final View view = this.getCurrentFocus();
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
           @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - haha.getHeight();
               Log.e("hehhee","height is"+String.valueOf(heightDiff));
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        return super.onTouchEvent(event);
    }*/


}

