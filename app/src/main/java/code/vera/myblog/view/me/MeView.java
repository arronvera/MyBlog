package code.vera.myblog.view.me;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.view.CircleImageView;
import code.vera.myblog.view.base.IView;

/**
 * Created by vera on 2017/2/8 0008.
 */

public class MeView implements IView {
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_intro)
    TextView tvUserIntro;
    @BindView(R.id.civ_head_photo)
    CircleImageView civHeadPhoto;

    @Override
    public void onAttachView(@NonNull View view) {

    }

    /**
     * 显示用户信息
     * @param bean
     */
    public void showUserInfo(UserInfoBean bean) {
        tvUserName.setText(bean.getName());
        tvUserIntro.setText(bean.getDescription());
        //加载头像
        ImageLoader.getInstance().displayImage(bean.getProfile_image_url(), civHeadPhoto, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));
    }
}
