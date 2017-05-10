package code.vera.myblog.view.other;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.UserInfoBean;
import code.vera.myblog.utils.SaveUtils;
import code.vera.myblog.view.base.BaseView;


/**
 * Created by vera on 2017/3/21 0021.
 */

public class SetView extends BaseView {
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.cb_net_toast)
    CheckBox cbIsNetToast;

    private Context context;


    @Override
    public void onAttachView(@NonNull final View view) {
        super.onAttachView(view);
        context=view.getContext();
        cbIsNetToast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SaveUtils.saveNetState(b,context);
            }
        });
        initView();
    }

    private void initView() {
        if (SaveUtils.getNetState(context)){
            cbIsNetToast.setChecked(true);
        }else {
            cbIsNetToast.setChecked(false);
        }
    }

    public void setCache(Double size) {
        tvCache.setText(size+"MB");
    }

    public void showUser(UserInfoBean userInfoBean) {
        ImageLoader.getInstance().displayImage(userInfoBean.getProfile_image_url(), ivPhoto, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
    }
}
