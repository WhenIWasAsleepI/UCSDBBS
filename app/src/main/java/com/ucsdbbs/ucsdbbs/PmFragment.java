package com.ucsdbbs.ucsdbbs;

import android.app.Application;

/**
 * Created by Administrator on 2015/12/26.
 */
public class PmFragment extends BaseFragment {
    private Global global;
    @Override
    public int initContent() {
        Application application = (Application)Global.getContext();
        global = (Global)application;
        if(global.getloginstatus())return R.layout.pm;
        else return R.layout.notlogin;
    }
}
