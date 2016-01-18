package com.ucsdbbs.ucsdbbs;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/17.
 */
public abstract class BaseFragment extends Fragment {
    private Button loginbutton;
    private EditText username;
    private EditText password;
    private String salt;
    private Global global;
    private ServerRunnable runnable;
    private CategoryAdapter mCustomBaseAdapter;
    private int picked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       /* View view = inflater.inflate(R.layout.fragment, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_content);
        textView.setText(initContent());*/
        View view = inflater.inflate(initContent(), null);
        picked = initContent();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(picked==R.layout.view) {
            ListView listView = (ListView) getActivity().findViewById(R.id.viewlist);
            listView.setFooterDividersEnabled(false);
            // 数据
            ArrayList<Category> listData = getData();

            mCustomBaseAdapter = new CategoryAdapter(getActivity().getBaseContext(), listData);

            // 适配器与ListView绑定
            listView.setAdapter(mCustomBaseAdapter);

            listView.setOnItemClickListener(new ItemClickListener());
        }
        //-----------login xml is selected------------------------------
        if (picked == R.layout.notlogin) {

            loginbutton = (Button) getActivity().findViewById(R.id.loginbutton);
            username = (EditText) getActivity().findViewById(R.id.username);
            password = (EditText) getActivity().findViewById(R.id.password);

            loginbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(username.getText().toString().equals(""))Toast.makeText(getActivity(), "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                        else{
                    Map<String,String> map= new HashMap<String, String>();;
                    map.put("username",username.getText().toString());
                    runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/login.php",map,login_request_handler);
                    new Thread(runnable).start();
                    //handler.post(runnable);
              /*  */}
                }
            });
        }
    }


    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
           // Toast.makeText(getActivity().getBaseContext(),  (String)mCustomBaseAdapter.getItem(position),
         //           Toast.LENGTH_SHORT).show();
            if(position==1) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ForumActivity.class);
                startActivity(intent);
            }
            
           // finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
        }

    }

    private ArrayList<Category> getData() {
        ArrayList<Category> listData = new ArrayList<Category>();
        Category categoryOne = new Category("路人甲");
        categoryOne.addItem("浏览讨论区");
        Category categoryTwo = new Category("事件乙");
        categoryTwo.addItem("论坛热点");
        Category categoryThree = new Category("书籍丙");
        categoryThree.addItem("帖子查询");

        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);

        return listData;
    }

    public Handler login_request_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("result");
            if(val.equals("success")&&!data.getString("data").equals("null")){
                try {
                    JSONArray jArray = new JSONArray(data.getString("data"));
                    JSONObject json_data = jArray.getJSONObject(0);
                    salt=json_data.getString("salt");
                    MD5 password_md5=null;
                    String temp="";
                    try {
                        temp = password_md5.getMD5(password.getText().toString());
                        temp=temp+salt;
                        temp=password_md5.getMD5(temp);
                        Map<String,String> map= new HashMap<String, String>();;
                        map.put("username",username.getText().toString());
                        map.put("password",temp);
                        runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/login_verify.php",map,login_verify_handler);
                        new Thread(runnable).start();

                    }catch(Exception e) {
                        Log.e("log_tag", "MD5 Error: " + e.toString());
                    }


                }catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }

            }
           else Toast.makeText(getActivity(), "用户名不存在", Toast.LENGTH_SHORT).show();
        }
    };

    public Handler login_verify_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("result");
            if(val.equals("success")){
                if(data.getString("data").equals("correct")) {
                    Application application = (Application)Global.getContext();
                    global = (Global)application;
                    global.setloginstatus(true);
                    FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                    int checked=((RadioGroup)getActivity().findViewById(R.id.rg_tab)).getCheckedRadioButtonId();
                    switch (checked){
                        case R.id.pmradio:
                            beginTransaction.replace(R.id.content,new PmFragment());
                            break;
                        case R.id.personalradio:
                            beginTransaction.replace(R.id.content,new PersonalFragment());
                            break;
                        case R.id.settingradio:
                            beginTransaction.replace(R.id.content,new SettingFragment());
                            break;
                    }
                    beginTransaction.commit();
                   // Toast.makeText(getActivity(), "密码正确！", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), "密码错误", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getActivity(), "服务器连接错误", Toast.LENGTH_SHORT).show();
        }
    };

    public abstract int initContent();
}
