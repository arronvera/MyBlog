package code.vera.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.bean.SortBean;

/**
 * 排序
 * Created by vera on 2017/3/1 0001.
 */

public class SortFriendAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortBean> list = null;
    private Context context;

    public SortFriendAdapter(Context context, List<SortBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SortBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SortFriendViewHolder viewHolder=null;
        SortBean sortBean=list.get(position);
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_sort,parent,false);
            viewHolder=new SortFriendViewHolder();
            viewHolder.tvName= (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvLetter= (TextView) convertView.findViewById(R.id.tv_catalog);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (SortFriendViewHolder) convertView.getTag();
        }

        int section = getSectionForPosition(position);

        if(position == getPositionForSection(section)){//如果位置等于单元位置
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(sortBean.getSortLetters());
        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvName.setText(this.list.get(position).getName());
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return  null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }
    class SortFriendViewHolder{
        TextView tvName;
        TextView tvLetter;

    }
}
