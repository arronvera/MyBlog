package code.vera.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.bean.MenuItem;

/**
 * Created by vera on 2017/1/23 0023.
 */

public class MenuItemAdapter extends BaseAdapter {
    private List<MenuItem> items;
    private Context context;

    public MenuItemAdapter(List<MenuItem> items, Context context) {
        this.items = items;
        this.context = context;
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
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        MenuItem item=items.get(position);
        holder.tv.setText(item.getText());
        if (item.getPic()!=0){
            holder.iv.setImageResource(item.getPic());
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv;
        ImageView iv;
    }
}
