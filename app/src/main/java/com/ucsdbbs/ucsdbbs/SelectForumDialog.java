package com.ucsdbbs.ucsdbbs;

        import android.content.Intent;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v4.app.DialogFragment;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
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

public class SelectForumDialog extends DialogFragment implements
        OnItemClickListener {
    private ServerRunnable runnable;
    List<Integer>pool_index=new ArrayList<Integer>();
    List<Integer>pool_ix=new ArrayList<Integer>();
    TextView topic;
    //String[] listitems = { "item01", "item02", "item03", "item04","item01", "item02", "item03", "item04","item01", "item02", "item03", "item04","item01", "item02", "item03", "item04" };
    private ArrayList<ForumCategory> listData= new ArrayList<ForumCategory>();

    ListView mylist;
    Button cancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newthread_selectforum, null, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        int width = getResources().getDimensionPixelSize(R.dimen.forum_select_width);
        int height = getResources().getDimensionPixelSize(R.dimen.forum_select_height);
        getDialog().getWindow().setLayout(width, height);

        mylist = (ListView) view.findViewById(R.id.list);
        cancel=(Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        topic=(TextView)view.findViewById(R.id.TOPIC);
        topic.setText("请选择发帖板块");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Map<String, String> map = new HashMap<String, String>();
        runnable = new ServerRunnable("http://www.ucsdbbs.com/app_related/getforumstruct.php", map, fill_handler);
        new Thread(runnable).start();
        mylist.setOnItemClickListener(this);

    }

    public Handler fill_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<ForumCategory> pool =new ArrayList<ForumCategory>();
            List<String>pool_names=new ArrayList<String>();

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
                            pool_names.add(json_data.getString("name"));
                            pool_index.add(Integer.parseInt(json_data.getString("fid")));
                        }

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, pool_names);

                    mylist.setAdapter(adapter);

                }catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if ((view != null)) {
            //Toast.makeText(getActivity(), String.valueOf(pool_index.get(position)), Toast.LENGTH_SHORT)
           //         .show();
            Intent intent = new Intent();
            intent.putExtra("fid", pool_index.get(position));
            intent.setClass(getActivity(), NewthreadActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            dismiss();
        }

    }

}