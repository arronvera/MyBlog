package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.UserInfoBean;
import code.vera.myblog.view.CircleImageView;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class SearchUserAdapter extends RvAdapter<UserInfoBean> {
    public SearchUserAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_search_user;
    }

    @Override
    protected RvViewHolder<UserInfoBean> getViewHolder(int viewType, View view) {
        return new SearchUserViewHolder(view);
    }
    class SearchUserViewHolder extends RvViewHolder<UserInfoBean> {
        @BindView(R.id.tv_item_user_name)
        TextView tvName;
        @BindView(R.id.tv_item_user_intro)
        TextView tvIntro;
        @BindView(R.id.tv_item_user_fans)
        TextView tvFans;
        @BindView(R.id.civ_item_user_photo)
        CircleImageView civPhoto;
        public SearchUserViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, UserInfoBean bean) {
            tvName.setText(bean.getScreen_name());
            tvIntro.setText(bean.getDescription());
            tvFans.setText("粉丝数:"+bean.getFollowers_count());
            ImageLoader.getInstance().displayImage(bean.getProfile_image_url(), civPhoto, BaseApplication
                    .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
        }
    }
}
