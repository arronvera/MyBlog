package code.vera.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.bean.MenuItem;
import code.vera.myblog.bean.UnReadBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.db.PostDao;

/**
 * Created by vera on 2017/1/23 0023.
 */

public class MenuItemAdapter extends BaseAdapter {
    private List<MenuItem> items;
    private Context context;
    private PostDao postDao;
    private UnReadBean unreadBean;
    private int favoriteSize;


    public MenuItemAdapter(List<MenuItem> items, Context context) {
        this.items = items;
        this.context = context;
        postDao=PostDao.getInstance(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_drawer,parent,false);
            holder=new ViewHolder();
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            holder.iv= (ImageView) convertView.findViewById(R.id.iv);
            holder.tvNum= (TextView) convertView.findViewById(R.id.tv_num);
            holder.rlNumber= (RelativeLayout) convertView.findViewById(R.id.rl_number);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        MenuItem item=items.get(position);
        holder.tv.setText(item.getText());
        if (item.getPic()!=0){
            holder.iv.setImageResource(item.getPic());
        }
        //显示未读信息
        if (unreadBean!=null){
            switch (position){
                case Constants.MENU_INDEX_HOME://首页
                    if (unreadBean.getStatus()!=0){
                        holder.rlNumber.setVisibility(View.VISIBLE);
                        holder.tvNum.setText(unreadBean.getStatus()+"");
                    }
                    break;
                case Constants.MENU_INDEX_MESSAGE://消息
                    holder.tvNum.setText(unreadBean.getStatus()+"");
                    break;
           }
        }
        //显示收藏数
        if (favoriteSize!=0&&position== Constants.MENU_INDEX_FAVORITE){
            holder.tvNum.setText(favoriteSize+"");
            holder.rlNumber.setVisibility(View.VISIBLE);
        }
        if (postDao.getAll()!=null&&postDao.getAll().size()!=0
                &&position== Constants.MENU_INDEX_DRAFT){//草稿
            holder.rlNumber.setVisibility(View.VISIBLE);
            holder.tvNum.setText(postDao.getAll().size()+"");
        }

        return convertView;
    }

    public void setFaviroteNum(int size) {
        this.favoriteSize=size;
        notifyDataSetChanged();

    }

    class ViewHolder{
        TextView tv;
        ImageView iv;
        TextView tvNum;
        RelativeLayout rlNumber;


    }

    /**
     * 设置未读消息数
     * @param unreadBean
     */
    public void setUnreadBean(UnReadBean unreadBean){
        this.unreadBean=unreadBean;
        notifyDataSetChanged();
    }
}
