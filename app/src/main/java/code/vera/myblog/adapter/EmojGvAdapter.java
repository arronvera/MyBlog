package code.vera.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.bean.Emoji;
import code.vera.myblog.utils.EmojiUtil;

/**
 * Created by vera on 2017/2/16 0016.
 */

public class EmojGvAdapter extends BaseAdapter {
    private List<Emoji> list;
    private Context mContext;

    public EmojGvAdapter(List<Emoji> list, Context mContext) {
        super();
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_face, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.face_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position) != null) {
            holder.iv.setImageBitmap(EmojiUtil.decodeSampledBitmapFromResource(mContext.getResources(), list.get(position).getDrawable(),
                    EmojiUtil.dip2px(mContext, 32), EmojiUtil.dip2px(mContext, 32)));
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
    }
}
