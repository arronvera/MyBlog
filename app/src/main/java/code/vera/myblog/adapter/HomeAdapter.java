package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.StatusesBean;

/**
 * Created by vera on 2017/2/7 0007.
 */

public class HomeAdapter extends RvAdapter<StatusesBean>{
    private Context context;

    public HomeAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_home_weib;
    }

    @Override
    protected RvViewHolder<StatusesBean> getViewHolder(int viewType, View view) {
        return new HomeViewHolder(view);
    }
    class HomeViewHolder extends RvViewHolder<StatusesBean> {
        @BindView(R.id.tv_item_text)
        TextView tvContent;
        @BindView(R.id.tv_item_name)
        TextView tvName;

        public HomeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, StatusesBean bean) {
            tvContent.setText(bean.getText());
//            tvName.setText(bean.getUserBean().getName());
//            ImageLoader.getInstance().displayImage(bean.ge);
        }
    }
}
