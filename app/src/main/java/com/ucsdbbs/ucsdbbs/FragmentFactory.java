package com.ucsdbbs.ucsdbbs;
import android.support.v4.app.Fragment;
/**
 * Created by Administrator on 2015/12/26.
 */
public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.viewradio:
                fragment = new ViewFragment();
                break;
            case R.id.pmradio:
                fragment = new PmFragment();
                break;
            case R.id.personalradio:
                fragment = new PersonalFragment();
                break;
            case R.id.settingradio:
                fragment = new SettingFragment();
                break;
        }
        return fragment;
    }
}