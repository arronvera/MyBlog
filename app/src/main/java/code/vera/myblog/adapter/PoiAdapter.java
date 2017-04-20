package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.PoiBean;

/**
 * Created by vera on 2017/4/20 0020.
 */

public class PoiAdapter extends RvAdapter<PoiBean> {

    public PoiAdapter(Context context) {
        super(context);
    }

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


        public PoiViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData(int position, PoiBean bean) {
            tvAddress.setText(bean.getAddress());
            tvCheckInNum.setText(bean.getCheckin_user_num() + "人在这签到过");
        }
    }
}
