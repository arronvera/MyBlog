package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.view.CircleImageView;

/**
 * Created by vera on 2017/3/21 0021.
 */

public class UserAdapter extends RvAdapter<UserInfoBean> {

    public UserAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
       return R.layout.item_user;
    }

    @Override
    protected RvViewHolder<UserInfoBean> getViewHolder(int viewType, View view) {
        return new UserViewHolder(view);
    }

     class UserViewHolder extends RvViewHolder<UserInfoBean> {
         @BindView(R.id.civ_item_user_photo)
         CircleImageView civPhoto;
        @BindView(R.id.tv_item_name)
         TextView tvName;
         @BindView(R.id.tv_item_latest)
         TextView tvLatest;

        public UserViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData(int position, UserInfoBean bean) {
            ImageLoader.getInstance().displayImage(bean.getProfile_image_url(),civPhoto);
            tvName.setText(bean.getName());
            //todo 最近一条
        }
    }
}
