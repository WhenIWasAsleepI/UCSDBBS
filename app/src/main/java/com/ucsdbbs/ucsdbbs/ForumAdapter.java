package com.ucsdbbs.ucsdbbs;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ForumAdapter extends BaseAdapter {

    private static final int TYPE_CATEGORY_ITEM = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<ForumCategory> mListData;
    private LayoutInflater mInflater;


    public ForumAdapter(Context context, ArrayList<ForumCategory> pData) {
        mListData = pData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;

        if (null != mListData) {
            //  ���з�����item���ܺ���ListVIew  Item���ܸ���
            for (ForumCategory category : mListData) {
                count += category.getItemCount();
            }
        }

        return count;
    }

    @Override
    public Object getItem(int position) {

        // �쳣�������
        if (null == mListData || position <  0|| position > getCount()) {
            return null;
        }

        // ͬһ�����ڣ���һ��Ԫ�ص�����ֵ
        int categroyFirstIndex = 0;

        for (ForumCategory category : mListData) {
            int size = category.getItemCount();
            // �ڵ�ǰ�����е�����ֵ
            int categoryIndex = position - categroyFirstIndex;
            // item�ڵ�ǰ������
            if (categoryIndex < size) {
                return  category.getItem( categoryIndex );
            }

            // �����ƶ�����ǰ�����β������һ�������һ��Ԫ������
            categroyFirstIndex += size;
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        // �쳣�������
        if (null == mListData || position <  0|| position > getCount()) {
            return TYPE_ITEM;
        }


        int categroyFirstIndex = 0;

        for (ForumCategory category : mListData) {
            int size = category.getItemCount();
            // �ڵ�ǰ�����е�����ֵ
            int categoryIndex = position - categroyFirstIndex;
            if (categoryIndex == 0) {
                return TYPE_CATEGORY_ITEM;
            }

            categroyFirstIndex += size;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_CATEGORY_ITEM:
                if (null == convertView) {
                    convertView = mInflater.inflate(R.layout.forum_item_header, null);
                }

                //TextView textView = (TextView) convertView.findViewById(R.id.header);
                String  itemValue = (String) getItem(position);
                //textView.setText( itemValue );
                break;

            case TYPE_ITEM:
                ViewHolder viewHolder = null;
                if (null == convertView) {

                    convertView = mInflater.inflate(R.layout.forum_item, null);

                    viewHolder = new ViewHolder();
                    viewHolder.content = (TextView) convertView.findViewById(R.id.content);
                    viewHolder.contentIcon = (ImageView) convertView.findViewById(R.id.content_icon);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                // ������
                viewHolder.content.setText( (String)getItem(position) );
                viewHolder.contentIcon.setImageResource(R.drawable.view);
                break;
        }

        return convertView;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != TYPE_CATEGORY_ITEM;
    }


    private class ViewHolder {
        TextView content;
        ImageView contentIcon;
    }

}
