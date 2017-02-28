package code.vera.myblog.view;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.adapter.TabPersonAdapter;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/2/7 0007.
 */

public class PersonalityView extends BaseView {

    @BindView(R.id.tab_person)
    TabLayout tabLayout;
    @BindView(R.id.vp_person_view)
    ViewPager vpPerson;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_friends)
    TextView tvFriends;//关注
    @BindView(R.id.tv_followers)
    TextView tvFolloweres;//粉丝
    @BindView(R.id.crv_photo)
    CircleImageView civPhoto;
    @BindView(R.id.iv_bg)
    ImageView ivBg;

    private TabPersonAdapter tabPersonAdapter;
    private PersonalityActivity activity;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }
    public void setAdapter(){
        tabPersonAdapter = new TabPersonAdapter(activity.getSupportFragmentManager());
        //给ViewPager设置适配器
        vpPerson.setAdapter(tabPersonAdapter);
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(vpPerson);
        //给Tabs设置适配器
        tabLayout.setTabsFromPagerAdapter(tabPersonAdapter);

        }

    public void showInfo(UserInfoBean userInfoBean) {
        tvFriends.setText("关注："+userInfoBean.getFriends_count());
        tvFolloweres.setText("粉丝："+userInfoBean.getFriends_count());
        tvIntro.setText(userInfoBean.getDescription());
        tvName.setText(userInfoBean.getName());
        ImageLoader.getInstance().displayImage(userInfoBean.getProfile_image_url(), civPhoto, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
//        ImageLoader.getInstance().displayImage(userInfoBean.getCover_image_phone(), ivBg, BaseApplication
//                .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
    }

    public void setActivity(PersonalityActivity personalityActivity) {
        activity=personalityActivity;
    }
}
