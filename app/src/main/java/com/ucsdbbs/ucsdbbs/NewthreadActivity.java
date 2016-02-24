package com.ucsdbbs.ucsdbbs;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private Integer fid;
    private ServerRunnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newthread);
        TextView topic=(TextView)findViewById(R.id.TOPIC);
        topic.setText("发布新帖");

        Intent intent =getIntent();
        fid=intent.getIntExtra("fid",0);

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
                    map.put("fid",String.valueOf(fid));
                    map.put("uid", global.getuid());
                    //Log.e("post param:",username+password+title_s+content_s+String.valueOf(fid));
                    runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/newthread.php", map, post_handler);
                    new Thread(runnable).start();
                }
            }
        });

        Button discard=(Button)findViewById(R.id.discard);
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    public Handler post_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("result");
            if (val.equals("success")){
                if(data.getString("data").equals("ok")){
                    Toast.makeText(getBaseContext(), "发帖成功！", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                else{
                    Toast.makeText(getBaseContext(), "对不起，您没有发帖权限，或已被禁言，请联系管理员：dnyjy1989@gmail.com", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            else{

            }
        }
    };

}
