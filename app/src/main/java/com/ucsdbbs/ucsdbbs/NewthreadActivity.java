package com.ucsdbbs.ucsdbbs;

import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class NewthreadActivity extends AppCompatActivity {
    private Global global;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newthread);
        TextView topic=(TextView)findViewById(R.id.TOPIC);
        topic.setText("发布新帖");

        final EditText title=(EditText)findViewById(R.id.title);
        final EditText content=(EditText)findViewById(R.id.content);

        Application application = (Application)Global.getContext();
        global = (Global)application;
        Button post=(Button)findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=global.getusername();
                String password=global.getpassword();
                String title_s=title.getText().toString();
                if(title_s.isEmpty()){
                    Toast.makeText(getBaseContext(), "请输入帖子标题", Toast.LENGTH_LONG).show();
                }
                else {
                    String content_s=content.getText().toString();
                    Map<String,String> map= new HashMap<String, String>();;
                    map.put("username",username);
                    map.put("password",password);
                    map.put("topic",title_s);
                    map.put("content",content_s);
                    map.put("typeid","0");
                }
            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //if(imm.isActive())return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        //else return true;
        //return true;
        //final View activityRootView = getWindow().getDecorView().getRootView();
        final View haha=(View)findViewById(R.id.newthread_root);
        final View view = this.getCurrentFocus();
        haha.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = haha.getRootView().getHeight() - haha.getHeight();
                Log.e("hehhee", "height is" + String.valueOf(heightDiff));
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        return super.onTouchEvent(event);
    }*/

}
