package com.ucsdbbs.ucsdbbs;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThreadCategory {

    private String mCategoryName;
    private List<String> tid= new ArrayList<String>();
    private List<String> fid= new ArrayList<String>();
    private List<String> author= new ArrayList<String>();
    private List<String> authorid= new ArrayList<String>();
    private List<String> subject= new ArrayList<String>();
    private List<String> dateline= new ArrayList<String>();
    private List<String> lastpost= new ArrayList<String>();
    private List<String> views= new ArrayList<String>();
    private List<String> replies= new ArrayList<String>();

    public ThreadCategory(String mCategroyName) {
        mCategoryName = mCategroyName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void addItem(String _tid,String _fid,String _author,String _authorid,String _subject,String _dateline,String _lastpost,String _views,String _replies) {
        tid.add(_tid);
        fid.add(_fid);
        author.add(_author);
        authorid.add(_authorid);
        subject.add(_subject);
        dateline.add(_dateline);
        lastpost.add(_lastpost);
        views.add(_views);
        replies.add(_replies);
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
            return subject.get(pPosition-1);
        }
        //    return a;
    }

    public String getsubject(int pPosition){
        String s="";
        s+=subject.get(pPosition-1);
        return s;
    }

    public String getauthor(int pPosition){
        String s="作者: ";
        s+=author.get(pPosition-1);
        return s;
    }

    public String getdate(int pPosition){
        String s="";
        String time=dateline.get(pPosition-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time)*1000);
        String date = sdf.format(calendar.getTime());
        s+=date;
        return s;
    }

    public String getviewandreply(int pPosition) {
        String s="";
        s+="查看: ";
        s+=views.get(pPosition-1);
        s+="/回复: ";
        s+=replies.get(pPosition-1);
        return s;
    }

    /**
     *
     * @return
     */
    public int getItemCount() {
        return tid.size() + 1;
    }

}
