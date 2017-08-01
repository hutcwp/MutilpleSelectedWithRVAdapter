package org.hutcwp.doubleselectedrvadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.hutcwp.doubleselectedrvadapter.adapter.ItemAdapter;
import org.hutcwp.doubleselectedrvadapter.bean.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView rvItem;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;

    //该Button用来实现清除所有已选状态的方法
    private Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    /**
     * 初始化View
     */
    private void initView() {

        btnClear = (Button) findViewById(R.id.btn_clear);
        rvItem = (RecyclerView) findViewById(R.id.rv_item);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        //数据结合
        itemList = new ArrayList<>();
        //模拟生成数据
        addData();

        //通过实现抽象方法，来达到Button根据当前状态（多选模式下显示，普通模式不显示）决定是否显示
        itemAdapter = new ItemAdapter(itemList, MainActivity.this) {
            @Override
            public void setNormalMode() {
                btnSetVisiablity(false);
            }

            @Override
            public void setMultipleMode() {
                btnSetVisiablity(true);
            }
        };

        //设置点击事件和长按事件的接口
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //改变选择状态
                itemAdapter.changeSelectStatus(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //如果当前状态是普通状态
                itemAdapter.setMode(itemAdapter.MULTIPLE_STATE);

            }
        });

        rvItem.setAdapter(itemAdapter);

        //一开始为普通模式，Enable设置为false,且不可见
        btnSetVisiablity(false);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAdapter.clearSelectedStatus();
            }
        });
    }


    /**
     * 模拟添加item数据
     */
    private void addData() {
        for (int i = 0; i < 5; i++) {
            itemList.add(new Item());
        }
    }

    /**
     * 设置可见性
     *
     * @param visibility 可见性
     */
    private void btnSetVisiablity(boolean visibility) {
        if (visibility) {
            btnClear.setEnabled(true);
            btnClear.setVisibility(View.VISIBLE);
        } else {
            btnClear.setEnabled(false);
            btnClear.setVisibility(View.INVISIBLE);
        }
    }

}
