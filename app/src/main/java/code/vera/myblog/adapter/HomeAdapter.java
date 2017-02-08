package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.view.CircleImageView;
import ww.com.core.Debug;

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
        @BindView(R.id.crv_item_photo)
        CircleImageView civPhoto;

        public HomeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, StatusesBean bean) {
            tvContent.setText(bean.getText());//内容
            Debug.d("bean="+bean.toString());
            if (bean.getUserBean()!=null){
                tvName.setText(bean.getUserBean().getName());//用户名
                ImageLoader.getInstance().displayImage(bean.getUserBean().getProfile_image_url(), civPhoto, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
            }else {
                civPhoto.setImageResource(R.mipmap.ic_user_default);
                tvName.setText("未知");
            }

        }
    }
}
