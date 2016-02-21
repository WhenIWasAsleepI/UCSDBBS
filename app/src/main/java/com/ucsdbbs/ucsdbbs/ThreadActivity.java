package com.ucsdbbs.ucsdbbs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadActivity extends AppCompatActivity{
    private ThreadAdapter mCustomBaseAdapter;
    private ServerRunnable runnable;
    private String fid;
    private String name;
    //next previous buttons
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;
    private FloatView Next = null;
    private FloatView Prev = null;
    private int page;
    // 数据
    private ArrayList<ThreadCategory> listData= new ArrayList<ThreadCategory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        TextView TOPIC=(TextView)findViewById(R.id.TOPIC);
        Intent intent =getIntent();
        name=intent.getStringExtra("name");
        TOPIC.setText(name);

        page=1;
        getData();
        createNextButton();

    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            //  Toast.makeText(getBaseContext(), (String) mCustomBaseAdapter.getFid(position),
            //          Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() {
    /*    ArrayList<Category> listData = new ArrayList<Category>();
        Category categoryOne = new Category("路人甲");
        categoryOne.addItem("浏览讨论区");
        Category categoryTwo = new Category("事件乙");
        categoryTwo.addItem("论坛热点");
        Category categoryThree = new Category("书籍丙");
        categoryThree.addItem("帖子查询");

        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);*/

        Map<String, String> map = new HashMap<String, String>();
        Intent intent =getIntent();
        fid=intent.getStringExtra("fid");
        map.put("fid",fid);
        map.put("page",Integer.toString(page));
        map.put("step", "15");
        runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/getthread.php", map, fill_handler);
        new Thread(runnable).start();
    }

    public Handler fill_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ThreadCategory pool =new ThreadCategory("");
            Bundle data = msg.getData();
            String val = data.getString("result");
            if (val.equals("success") && !data.getString("data").equals("null")) {
                try {
                    JSONArray jArray = new JSONArray(data.getString("data"));
                    int count=0;
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        pool.addItem(json_data.getString("tid"), json_data.getString("fid"), json_data.getString("author"), json_data.getString("authorid"), json_data.getString("subject"), json_data.getString("dateline"), json_data.getString("lastpost"), json_data.getString("views"), json_data.getString("replies"));
                    }
                    listData.clear();
                    listData.add(pool);
                    ListView listView = (ListView) findViewById(R.id.threadlist);
                    listView.setFooterDividersEnabled(false);
                    mCustomBaseAdapter = new ThreadAdapter(getBaseContext(), listData);

                    // 适配器与ListView绑定
                    listView.setAdapter(mCustomBaseAdapter);

                    listView.setOnItemClickListener(new ItemClickListener());
                }catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }
                if(Prev==null&&page>1)createPrevButton();
               // if(Next==null&&page>1)createNextButton();
                if(Next==null&&page==1)createNextButton();
            }
            else {
                page-=1;
                //windowManager.removeView(Next);
                //Next=null;
                Toast.makeText(getBaseContext(), "已经到最后一页啦！", Toast.LENGTH_SHORT).show();
                //if(Prev!=null)windowManager.removeView(Prev);
            }
        }
    };


    private void createNextButton() {
        Next = new FloatView(getApplicationContext());
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page+=1;
                Map<String, String> map = new HashMap<String, String>();
                map.put("fid",fid);
                map.put("page",Integer.toString(page));
                map.put("step", "15");
                runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/getthread.php", map, fill_handler);
                new Thread(runnable).start();

            }
        });
        Next.setImageResource(R.drawable.pm);
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManagerParams = ((Global) getApplication()).getWindowParams();
        windowManagerParams.type =  WindowManager.LayoutParams.TYPE_TOAST;
        windowManagerParams.format = PixelFormat.RGBA_8888;
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        Resources r = getResources();
        windowManagerParams.x =Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, r.getDisplayMetrics()));
        windowManagerParams.y = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, r.getDisplayMetrics()));
        windowManagerParams.width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics()));
        windowManagerParams.height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics()));
        windowManager.addView(Next, windowManagerParams);
    }


    private void createPrevButton() {
        Prev = new FloatView(getApplicationContext());
        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>1) {
                    page -= 1;
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("fid", fid);
                    map.put("page", Integer.toString(page));
                    map.put("step", "15");
                    runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/getthread.php", map, fill_handler);
                    new Thread(runnable).start();
                }
                else {
                    windowManager.removeView(Prev);
                    Prev=null;
                    Toast.makeText(getBaseContext(), "已经到第一页啦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Prev.setImageResource(R.drawable.pm);
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManagerParams = ((Global) getApplication()).getWindowParams();
        windowManagerParams.type =  WindowManager.LayoutParams.TYPE_TOAST;
        windowManagerParams.format = PixelFormat.RGBA_8888;
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        Resources r = getResources();
        windowManagerParams.x =Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, r.getDisplayMetrics()));
        windowManagerParams.y = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 360, r.getDisplayMetrics()));
        windowManagerParams.width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics()));
        windowManagerParams.height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics()));
        windowManager.addView(Prev, windowManagerParams);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(Next != null)
        {
            //移除悬浮窗口
            windowManager.removeView(Next);
        }
        if(Prev != null)
        {
            //移除悬浮窗口
            windowManager.removeView(Prev);
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(0, 0);
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
