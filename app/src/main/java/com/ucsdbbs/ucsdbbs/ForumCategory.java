package com.ucsdbbs.ucsdbbs;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ForumCategory {

    private String mCategoryName;
    private List<String> fid= new ArrayList<String>();
    private List<String> type= new ArrayList<String>();
    private List<String> name= new ArrayList<String>();
    private List<String> status= new ArrayList<String>();
    private List<String> displayorder= new ArrayList<String>();
    private List<String> threads= new ArrayList<String>();
    private List<String> posts= new ArrayList<String>();
    private List<String> todayposts= new ArrayList<String>();
    private List<String> lastpost= new ArrayList<String>();
    private List<String> lastusertime= new ArrayList<String>();

    public ForumCategory(String mCategroyName) {
        mCategoryName = mCategroyName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void addItem(String _fid,String _type,String _name,String _status,String _displayorder,String _threads,String _posts,String _todayposts,String _lastpost) {
        fid.add(_fid);
        type.add(_type);
        name.add(_name);
        status.add(_status);
        displayorder.add(_displayorder);
        threads.add(_threads);
        posts.add(_posts);
        todayposts.add(_todayposts);
        lastpost.add(_lastpost);
    }

    /**
     *
     *
     * @param pPosition
     * @return
     */
    public String getItem(int pPosition) {
    //    String a="";
        if (pPosition == 0) {
            return mCategoryName;
        } else {
            return name.get(pPosition-1);
        }
    //    return a;
    }

    public String getStat(int pPosition){
            String s="";
            s+="今日:";
            s+=todayposts.get(pPosition-1);
            s+="/总计:";
            s+=posts.get(pPosition-1);
            return s;
    }

    public String getLastpost(int pPosition){
            String s="";
            if(Integer.parseInt(posts.get(pPosition-1))!=0){
                s+=lastpost.get(pPosition-1);
                String[] aa = s.split("\t");
                s=aa[1];
                return s;
            }
            else return s;
    }

    public String getLastusertime(int pPosition){
        String s="";
        if(Integer.parseInt(posts.get(pPosition-1))!=0){
            s+=lastpost.get(pPosition-1);
            String[] aa = s.split("\t");
            String time=aa[2];
            String user=aa[3];
            s=user;
            s+="回复于 ";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(time)*1000);
            String date = sdf.format(calendar.getTime());
            s+=date;
            return s;
        }
        else return s;
    }
    /**
     *
     * @return
     */
    public int getItemCount() {
        return fid.size() + 1;
    }

}
