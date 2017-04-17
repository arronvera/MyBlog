package code.vera.myblog.view;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.adapter.TabPersonAdapter;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.view.base.BaseView;
import ww.com.core.Debug;

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
    @BindView(R.id.iv_identity)
    ImageView ivIdentity;//身份
    @BindView(R.id.iv_gender)
    ImageView ivGender;//性别
    @BindView(R.id.tv_concern)
    TextView tvConcern;//关注
    @BindView(R.id.tv_vertify_reason)
    TextView tvIdentityReason;//认证理由
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_author_name)
    TextView tvAuthor;


    private TabPersonAdapter tabPersonAdapter;
    private PersonalityActivity activity;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Debug.d("verticalOffset=" + verticalOffset);
                if (verticalOffset == 0) {
                    rlTop.setVisibility(View.GONE);
                } else {
                    rlTop.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    public void setAdapter() {
        tabPersonAdapter = new TabPersonAdapter(activity.getSupportFragmentManager());
        //给ViewPager设置适配器
        vpPerson.setAdapter(tabPersonAdapter);
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(vpPerson);
        //给Tabs设置适配器
        tabLayout.setTabsFromPagerAdapter(tabPersonAdapter);
    }

    /**
     * 显示信息
     *
     * @param userInfoBean
     */
    public void showInfo(UserInfoBean userInfoBean) {
        tvFriends.setText("关注：" + userInfoBean.getFriends_count());//关注
        tvFolloweres.setText("粉丝：" + userInfoBean.getFollowers_count());//粉丝
        tvIntro.setText("简介：" + userInfoBean.getDescription());//介绍
        tvName.setText(userInfoBean.getName());//名字
        tvAuthor.setText(userInfoBean.getName());
        ImageLoader.getInstance().displayImage(userInfoBean.getProfile_image_url(), civPhoto, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
        ImageLoader.getInstance().displayImage(userInfoBean.getCover_image_phone(), ivBg);//背景
        if (userInfoBean.getGender().equals("m")) {//男性
            ivGender.setImageResource(R.mipmap.ic_male);
        } else if (userInfoBean.getGender().equals("f")) {//女性
            ivGender.setImageResource(R.mipmap.ic_female);
        } else {
            ivGender.setVisibility(View.INVISIBLE);
        }
        if (userInfoBean.isFollow_me()) {
            tvConcern.setText("取消关注");
        } else {
            tvConcern.setText("关注");
        }
        if (userInfoBean.isVerified()) {//认证
            ivIdentity.setVisibility(View.VISIBLE);
            tvIdentityReason.setVisibility(View.VISIBLE);
            tvIdentityReason.setText("认证理由：" + userInfoBean.getVerified_reason());
        } else {
            ivIdentity.setVisibility(View.INVISIBLE);
            tvIdentityReason.setVisibility(View.GONE);
        }

    }

    public void setActivity(PersonalityActivity personalityActivity) {
        activity = personalityActivity;
    }

    public void setConcernVisible(boolean b) {
        if (b) {
            tvConcern.setVisibility(View.VISIBLE);
        } else {
            tvConcern.setVisibility(View.INVISIBLE);
        }
    }

    public CircleImageView getPhotoView() {
        return civPhoto;
    }

    public ImageView getCoverImgView() {
        return ivBg;
    }

    public void setConcerText(boolean isFollow) {
        if (isFollow) {
            tvConcern.setText(R.string.cancel_concern);
        } else {
            tvConcern.setText(R.string.concern);
        }
    }
}
