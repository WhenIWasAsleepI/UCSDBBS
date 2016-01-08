package com.ucsdbbs.ucsdbbs;

import java.util.ArrayList;
import java.util.List;

public class ForumCategory {

    private String mCategoryName;
    private List<String> mCategoryItem = new ArrayList<String>();

    public ForumCategory(String mCategroyName) {
        mCategoryName = mCategroyName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void addItem(String pItemName) {
        mCategoryItem.add(pItemName);
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
            return mCategoryItem.get(pPosition - 1);
        }
    }

    /**
     *
     * @return
     */
    public int getItemCount() {
        return mCategoryItem.size() + 1;
    }

}
