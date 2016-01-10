package com.ucsdbbs.ucsdbbs;

import java.util.ArrayList;
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

    public ForumCategory(String mCategroyName) {
        mCategoryName = mCategroyName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void addItem(String _fid,String _type,String _name,String _status,String _displayorder,String _threads,String _posts,String _todayposts) {
        fid.add(_fid);
        type.add(_type);
        name.add(_name);
        status.add(_status);
        displayorder.add(_displayorder);
        threads.add(_threads);
        posts.add(_posts);
        todayposts.add(_todayposts);
    }

    /**
     *
     *
     * @param pPosition
     * @return
     */
    public String getItem(int pPosition) {
        if (pPosition == 0) {
            return mCategoryName;
        } else {
            return name.get(pPosition - 1);
        }
    }

    /**
     *
     * @return
     */
    public int getItemCount() {
        return fid.size() + 1;
    }

}
