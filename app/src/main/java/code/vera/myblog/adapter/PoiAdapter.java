package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.PoiBean;
import code.vera.myblog.listener.OnItemClickListener;
import ww.com.core.Debug;

/**
 * Created by vera on 2017/4/20 0020.
 */

public class PoiAdapter extends RvAdapter<PoiBean> {
    private OnItemClickListener onItemClickListener;
    public PoiAdapter(Context context) {
        super(context);
    }
    private int clickIndex=-1;
    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_poi;
    }

    @Override
    protected RvViewHolder<PoiBean> getViewHolder(int viewType, View view) {
        return new PoiViewHolder(view);
    }

    class PoiViewHolder extends RvViewHolder<PoiBean> {
        @BindView(R.id.tv_item_address)
        TextView tvAddress;
        @BindView(R.id.tv_check_in_num)
        TextView tvCheckInNum;
        @BindView(R.id.tv_item_title)
        TextView tvTitle;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.iv_select)
        ImageView ivSelect;

        public PoiViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData(final int position, PoiBean bean) {
            Debug.d("address="+bean.getAddress());
            tvAddress.setText(bean.getAddress());
            tvCheckInNum.setText(bean.getCheckin_user_num() + "人在这签到过");
            tvTitle.setText(bean.getTitle());
            rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClickListener(view,position);
                    }
                }
            });
            if (clickIndex==position){
                ivSelect.setVisibility(View.VISIBLE);
            }else{
                ivSelect.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setClickIndex(int clickIndex) {
        this.clickIndex = clickIndex;
    }
}
