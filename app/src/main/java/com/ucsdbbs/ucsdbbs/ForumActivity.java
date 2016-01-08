package com.ucsdbbs.ucsdbbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {
    private CategoryAdapter mCustomBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ListView listView = (ListView) findViewById(R.id.forumlist);
        listView.setFooterDividersEnabled(false);
        // 数据
        ArrayList<Category> listData = getData();

        mCustomBaseAdapter = new CategoryAdapter(getBaseContext(), listData);

        // 适配器与ListView绑定
        listView.setAdapter(mCustomBaseAdapter);

        listView.setOnItemClickListener(new ItemClickListener());
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(getBaseContext(), (String) mCustomBaseAdapter.getItem(position),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<Category> getData() {
        ArrayList<Category> listData = new ArrayList<Category>();
        Category categoryOne = new Category("路人甲");
        categoryOne.addItem("浏览讨论区");
        Category categoryTwo = new Category("事件乙");
        categoryTwo.addItem("论坛热点");
        Category categoryThree = new Category("书籍丙");
        categoryThree.addItem("帖子查询");

        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);

        return listData;
    }
}
