package org.hutcwp.doubleselectedrvadapter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.hutcwp.doubleselectedrvadapter.R;
import org.hutcwp.doubleselectedrvadapter.bean.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hutcwp on 2017/7/31.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public abstract class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    //正常状态
    public final int NORMAL_STATE = 0;
    //多选状态
    public final int MULTIPLE_STATE = 1;

    //被选择的item的数量
    private int selectCount = 0;
    //当前所处模式
    public int curState = NORMAL_STATE;

    //点击和长按事件接口
    private OnItemClickListener onItemClickListener;
    //Item数据集合
    private List<Item> itemList = new ArrayList<>();

    private Context context;

    protected ItemAdapter(List<Item> itemList, Context context) {
        if (itemList != null) {
            this.itemList = itemList;
        }
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        //如果被选中的item大于0，则为多选模式，否则为正常模式
        int mode = selectCount > 0 ? MULTIPLE_STATE : NORMAL_STATE;
        //每次都需要调用设置模式的方法，来监听当此Item状态改变时，是否需要退出多选模式
        setMode(mode);

        if (itemList.get(position).isSelectStatus()) {
            //选中 蓝色
            holder.tvItem.setBackgroundColor(Color.CYAN);
        } else {
            //未选中 红色
            holder.tvItem.setBackgroundColor(Color.GRAY);
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v, position);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    /**
     * 改变选择状态,只有位于多选状态下才能改变
     */
    public void changeSelectStatus(int position) {
        if (curState == MULTIPLE_STATE) {
            // 如果当前处于多选状态，则进入多选状态的逻辑
            boolean status = itemList.get(position).isSelectStatus();
            //改变选择状态
            itemList.get(position).setSelectStatus(!status);
            //等到status改变之后再读取
            selectCount = itemList.get(position).isSelectStatus() ? selectCount + 1 : selectCount - 1;
            //通知更新此Item
            notifyItemChanged(position);
        }

    }

    /**
     * 清除已经选择的状态，只有处于多选状态下才能操作
     */
    public void clearSelectedStatus() {
        if (curState == MULTIPLE_STATE) {
            //选中的Item清零
            selectCount = 0;
            //遍历改变Item属性
            for (Item item : itemList) {
                item.setSelectStatus(false);
            }
            //通知更新
            notifyDataSetChanged();
        }
    }

    /**
     * 设置当前模式
     *
     * @param status 模式
     */
    public void setMode(int status) {
        //判断是否需要更改当前模式
        if (curState != status) {
            curState = status;
            if (curState == NORMAL_STATE) {
                toast("当前为普通模式");
                setNormalMode();
            } else if (curState == MULTIPLE_STATE) {
                toast("当前为多选模式");
                setMultipleMode();
            }
        }

    }

    /**
     * 设置事件监听接口
     *
     * @param onItemClickListener 接口
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /** 抽象方法，需要在调用者中去实现
     * 设置为正常模式
     */
    public abstract void setNormalMode();

    /**
     * 设置为多选模式
     */
    public abstract void setMultipleMode();


    /**
     * 长按和点击事件的接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    /**
     * 调试用的方法
     */
    public void printStatu() {
        Log.d("test", "Mode = " + curState + "  seletcCount " + selectCount);
    }

    /**
     * 弹出Toast
     *
     * @param msg 信息
     */
    private void toast(String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    //自定义ViewHolder
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        ItemViewHolder(View view) {
            super(view);
            tvItem = (TextView) view.findViewById(R.id.tv_item);
        }
    }


}
