package code.vera.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import code.vera.myblog.R;

/**
 * Created by vera on 2017/2/10 0010.
 */

public class HomeTypeListAdpater extends BaseAdapter {
    private List<String>data;
    private Context context;

    public HomeTypeListAdpater(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_home_type,parent,false);
            holder.textView= (TextView) convertView.findViewById(R.id.type_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(data.get(position));
        return convertView;
    }
    class ViewHolder {
        TextView textView;

    }
}
