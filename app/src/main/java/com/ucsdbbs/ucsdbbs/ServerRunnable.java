package com.ucsdbbs.ucsdbbs;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/27.
 */
public class ServerRunnable implements Runnable{
    public String script;
    public Map<String,String> map;
    public Handler handler;
    public String content;

    public ServerRunnable(String _script,Map<String,String> _map,Handler _handler) {
        this.script = _script;
        this.map=_map;
        this.handler=_handler;
    }

    public void run() {
        URL url;
        HttpURLConnection httpConn = null;
        InputStream is;
        ByteArrayOutputStream baos;
        String content = null;
        try {
            url = new URL(script);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setUseCaches(false);
            httpConn.connect();
            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
            String postContent="";
            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                postContent+=URLEncoder.encode(entry.getKey(), "UTF-8") +"="+entry.getValue()+"&";
            }
            dos.write(postContent.getBytes());
            dos.flush();
            dos.close();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        try {
            // 开始GET数据
            String encoding = httpConn.getContentEncoding();
            is = httpConn.getInputStream();
            int read = -1;
            baos = new ByteArrayOutputStream();
            while ((read = is.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            baos.close();


            if (encoding != null) {
                content = new String(data, encoding);
            } else {
                content = new String(data);
            }
            Message msg = new Message();
            Bundle data1 = new Bundle();
            data1.putString("result", "success");
            data1.putString("data", content);
            msg.setData(data1);
            handler.sendMessage(msg);
        } catch (Exception e) {
            Log.e("log_tag", "Error in GET " + e.toString());
            Message msg = new Message();
            Bundle data1 = new Bundle();
            data1.putString("result", "failed");
            msg.setData(data1);
            handler.sendMessage(msg);
        }



        /*try {
            Log.e("log_tag", content);
            if (content.equals("null")){
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", "找不到用户名或密码错误");
                msg.setData(data);
                handler.sendMessage(msg);

            }
            else {
                JSONArray jArray = new JSONArray(content);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    salt=json_data.getString("salt");
                }
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }*/
    }
}

