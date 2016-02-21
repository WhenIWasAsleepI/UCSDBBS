package com.ucsdbbs.ucsdbbs;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumActivity extends AppCompatActivity {
    private ForumAdapter mCustomBaseAdapter;
    private ServerRunnable runnable;
    // 数据
    private ArrayList<ForumCategory> listData= new ArrayList<ForumCategory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        TextView TOPIC=(TextView)findViewById(R.id.TOPIC);
        TOPIC.setText("浏览讨论区");
        getData();


    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent();
            intent.setClass(ForumActivity.this, ThreadActivity.class);
            intent.putExtra("fid", (String) mCustomBaseAdapter.getFid(position));
            intent.putExtra("name",(String)mCustomBaseAdapter.getItem(position));
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
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
        runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/getforumstruct.php", map, fill_handler);
        new Thread(runnable).start();
    }

    public Handler fill_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<ForumCategory> pool =new ArrayList<ForumCategory>();
            List<Integer>pool_ix=new ArrayList<Integer>();
            Bundle data = msg.getData();
            String val = data.getString("result");
            if (val.equals("success") && !data.getString("data").equals("null")) {
                try {
                    Log.e("log_tag", "BACK VALUE " + data.getString("data"));
                    JSONArray jArray = new JSONArray(data.getString("data"));
                    int count=0;
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        if(json_data.getString("type").equals("group")){
                            pool.add( new ForumCategory(json_data.getString("name")));
                            pool_ix.add(Integer.parseInt(json_data.getString("fid")));
                            count++;
                        }
                        else if(json_data.getString("type").equals("sub")==false){
                            int k=0;
                            for(k=0;k<pool_ix.size();k++){if(Integer.parseInt(json_data.getString("fup"))==pool_ix.get(k))break;}
                            pool.get(k).addItem(json_data.getString("fid"), json_data.getString("type"), json_data.getString("name"), json_data.getString("status"), json_data.getString("displayorder"), json_data.getString("threads"), json_data.getString("posts"), json_data.getString("todayposts"), json_data.getString("lastpost"));
                        }

                    }
                    for(int i=0;i<count;i++){
                        listData.add(pool.get(i));
                    }
                    ListView listView = (ListView) findViewById(R.id.forumlist);
                    listView.setFooterDividersEnabled(false);
                    mCustomBaseAdapter = new ForumAdapter(getBaseContext(), listData);

                    // 适配器与ListView绑定
                    listView.setAdapter(mCustomBaseAdapter);

                    listView.setOnItemClickListener(new ItemClickListener());
                }catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }
            }
        }
    };


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
