package com.ucsdbbs.ucsdbbs;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadActivity extends AppCompatActivity {
    private ThreadAdapter mCustomBaseAdapter;
    private ServerRunnable runnable;
    // 数据
    private ArrayList<ThreadCategory> listData= new ArrayList<ThreadCategory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        getData();


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
        String fid=intent.getStringExtra("fid");
        map.put("fid",fid);
        map.put("page","1");
        map.put("step","15");
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
                    Log.e("log_tag", "BACK VALUE " + data.getString("data"));
                    JSONArray jArray = new JSONArray(data.getString("data"));
                    int count=0;
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        pool.addItem(json_data.getString("tid"), json_data.getString("fid"), json_data.getString("author"), json_data.getString("authorid"), json_data.getString("subject"), json_data.getString("dateline"), json_data.getString("lastpost"), json_data.getString("views"), json_data.getString("replies"));
                    }
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
            }
        }
    };
}
