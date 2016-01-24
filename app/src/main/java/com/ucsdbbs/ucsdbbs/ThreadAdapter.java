package com.ucsdbbs.ucsdbbs;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreadAdapter extends BaseAdapter {

    private static final int TYPE_CATEGORY_ITEM = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<ThreadCategory> mListData;
    private LayoutInflater mInflater;


    public ThreadAdapter(Context context, ArrayList<ThreadCategory> pData) {
        mListData = pData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;

        if (null != mListData) {
            //  ���з�����item���ܺ���ListVIew  Item���ܸ���
            for (ThreadCategory category : mListData) {
                count += category.getItemCount();
            }
        }

        return count;
    }

    @Override
    public Object getItem(int position) {

        if (null == mListData || position <  0|| position > getCount()) {
            return null;
        }

        int categroyFirstIndex = 0;

        for (ThreadCategory category : mListData) {
            int size = category.getItemCount();

            int categoryIndex = position - categroyFirstIndex;

            if (categoryIndex < size) {
                return  category.getItem(categoryIndex);
            }
            categroyFirstIndex += size;
        }

        return null;
    }

    public Object getSubject(int position) {

        if (null == mListData || position <  0|| position > getCount()) {
            return null;
        }

        int categroyFirstIndex = 0;

        for (ThreadCategory category : mListData) {
            int size = category.getItemCount();

            int categoryIndex = position - categroyFirstIndex;

            if (categoryIndex < size) {
                return  category.getsubject(categoryIndex);
            }
            categroyFirstIndex += size;
        }

        return null;
    }

    public Object getAuthor(int position) {

        if (null == mListData || position <  0|| position > getCount()) {
            return null;
        }

        int categroyFirstIndex = 0;

        for (ThreadCategory category : mListData) {
            int size = category.getItemCount();

            int categoryIndex = position - categroyFirstIndex;

            if (categoryIndex < size) {
                return  category.getauthor(categoryIndex);
            }
            categroyFirstIndex += size;
        }

        return null;
    }

    public Object getTime(int position) {

        if (null == mListData || position <  0|| position > getCount()) {
            return null;
        }

        int categroyFirstIndex = 0;

        for (ThreadCategory category : mListData) {
            int size = category.getItemCount();

            int categoryIndex = position - categroyFirstIndex;

            if (categoryIndex < size) {
                return  category.getdate(categoryIndex);
            }
            categroyFirstIndex += size;
        }

        return null;
    }


    public Object getViewandReply(int position) {

        if (null == mListData || position <  0|| position > getCount()) {
            return null;
        }

        int categroyFirstIndex = 0;

        for (ThreadCategory category : mListData) {
            int size = category.getItemCount();

            int categoryIndex = position - categroyFirstIndex;

            if (categoryIndex < size) {
                return  category.getviewandreply(categoryIndex);
            }
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

        for (ThreadCategory category : mListData) {
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
                    convertView = mInflater.inflate(R.layout.thread_item_header, null);
                }

                //TextView textView = (TextView) convertView.findViewById(R.id.header);
                String  itemValue = (String) getItem(position);
                //textView.setText( itemValue );
                break;

            case TYPE_ITEM:
                ViewHolder viewHolder = null;
                if (null == convertView) {

                    convertView = mInflater.inflate(R.layout.thread_item, null);

                    viewHolder = new ViewHolder();
                    viewHolder.subject = (TextView) convertView.findViewById(R.id.subject);
                    viewHolder.author = (TextView) convertView.findViewById(R.id.author);
                    viewHolder.time=(TextView) convertView.findViewById(R.id.threadtime);
                    viewHolder.viewandreply=(TextView)convertView.findViewById(R.id.viewNreply);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                // ������
                viewHolder.subject.setText( (String)getSubject(position) );
                viewHolder.author.setText((String) getAuthor(position));
                viewHolder.time.setText((String) getTime(position));
                viewHolder.viewandreply.setText((String) getViewandReply(position));
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
        TextView subject;
        TextView author;
        TextView time;
        TextView viewandreply;
    }

}
