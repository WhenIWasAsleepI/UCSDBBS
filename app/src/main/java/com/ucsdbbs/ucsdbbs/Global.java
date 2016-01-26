package com.ucsdbbs.ucsdbbs;
import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/12/26.
 */
public class Global extends Application{
    private static Context mContext;
    private boolean login;
    public boolean getloginstatus(){
        return login;
    }
    public void setloginstatus(boolean s){
        this.login = s;
    }

    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        this.mContext = this;
        setloginstatus(false);
    }

    public static Context getContext(){
        return mContext;
    }
}

