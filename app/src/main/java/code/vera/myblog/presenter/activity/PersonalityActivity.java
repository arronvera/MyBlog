package code.vera.myblog.presenter.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;

import butterknife.OnClick;
import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.PersonalityModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.presenter.fragment.person.TabPersonAllCircleFragment;
import code.vera.myblog.presenter.fragment.person.TabPersonInfoFragment;
import code.vera.myblog.presenter.fragment.person.TabPersonPhotosFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.PersonalityView;
import ww.com.core.Debug;

import static code.vera.myblog.presenter.activity.BigPhotoActivity.PARAM_PHOTO;
import static code.vera.myblog.presenter.activity.UsersFollowsActivity.PARAM_USER_FOLLOW_ID;
import static code.vera.myblog.presenter.activity.UsersFollowsActivity.PARAM_USER_FOLLOW_TYPE;


/**
 * 个人页面
 * Created by vera on 2017/2/7 0007.
 */

public class PersonalityActivity extends PresenterActivity<PersonalityView, PersonalityModel> {
    private UserInfoBean userInfoBean;
    private String user_name;
    private long user_id;
    public static final String HEAD_PHOTO_SHARE = "photopic";
    public static final String BUNDLER_PARAM_USER = "user";
    public static final String BUNDLER_PARAM_USER_NAME = "user_name";
    public static final String BUNDLER_PARAM_USER_ID = "user_id";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personality;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        view.setActivity(this);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        userInfoBean = (UserInfoBean) intent.getSerializableExtra(BUNDLER_PARAM_USER);
        if (userInfoBean != null) {
            view.showInfo(userInfoBean);
            TabPersonInfoFragment.getInstance().setUid(userInfoBean.getId());
            TabPersonAllCircleFragment.getInstance().setUid(userInfoBean.getId());
            TabPersonPhotosFragment.getInstance().setUid(userInfoBean.getId());
            if (AccessTokenKeeper.readAccessToken(getApplicationContext()).getUid().equals(userInfoBean.getId() + "")) {
                view.setConcernVisible(false);
            }
            bundle.putString("id", userInfoBean.getId() + "");
            bundle.putString("name", userInfoBean.getName());
        }
        user_name = intent.getStringExtra(BUNDLER_PARAM_USER_NAME);
        if (!TextUtils.isEmpty(user_name)) {
            model.getUserInfoByName(mContext, user_name, bindUntilEvent(ActivityEvent.DESTROY),
                    new CustomSubscriber<UserInfoBean>(mContext, true) {
                        @Override
                        public void onNext(UserInfoBean user) {
                            super.onNext(user);
                            Debug.d("user=" + user.toString());
                            userInfoBean = user;
                            view.showInfo(user);
                            TabPersonInfoFragment.getInstance().setUid(user.getId());
                            TabPersonAllCircleFragment.getInstance().setUid(user.getId());
                            TabPersonPhotosFragment.getInstance().setUid(user.getId());
                        }
                    });
            bundle.putString("name", user_name);
        }
        //传入的是用户id
        user_id = intent.getLongExtra(BUNDLER_PARAM_USER_ID, 0);
        if (user_id != 0) {
            model.getUserInfoById(mContext, user_id + "", bindUntilEvent(ActivityEvent.DESTROY),
                    new CustomSubscriber<UserInfoBean>(mContext, true) {
                        @Override
                        public void onNext(UserInfoBean userInfoBean) {
                            super.onNext(userInfoBean);
                            TabPersonInfoFragment.getInstance().setUid(userInfoBean.getId());
                            TabPersonAllCircleFragment.getInstance().setUid(userInfoBean.getId());
                            TabPersonPhotosFragment.getInstance().setUid(userInfoBean.getId());
                        }
                    });
        }
        view.setAdapter();

    }

    @OnClick({R.id.btn_concern, R.id.tv_friends, R.id.tv_followers, R.id.iv_bg, R.id.crv_photo, R.id.iv_back})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.btn_concern:
                if (userInfoBean.isFollowing()) {//取消关注
                    destroyFriendShip();
                } else {//关注
                    createFriendShip();
                }
                break;
            case R.id.tv_friends://关注
                Bundle bundle = new Bundle();
                bundle.putLong(PARAM_USER_FOLLOW_ID, userInfoBean.getId());
                bundle.putInt(PARAM_USER_FOLLOW_TYPE, Constants.TYPE_CONCERN);
                UsersFollowsActivity.start(this, bundle);
                break;
            case R.id.tv_followers://粉丝
                bundle = new Bundle();
                bundle.putLong(PARAM_USER_FOLLOW_ID, userInfoBean.getId());
                bundle.putInt(PARAM_USER_FOLLOW_TYPE, Constants.TYPE_FOLLOWERES);
                UsersFollowsActivity.start(this, bundle);
                break;
            case R.id.iv_bg://封面
                Intent intent = new Intent(this, BigPhotoActivity.class);
                intent.putExtra(PARAM_PHOTO, userInfoBean.getCover_image_phone());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view.getCoverImgView(), HEAD_PHOTO_SHARE).toBundle());
                break;
            case R.id.crv_photo://头像
                Intent intent2 = new Intent(this, BigPhotoActivity.class);
                intent2.putExtra(PARAM_PHOTO, userInfoBean.getAvatar_hd());
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(this, view.getPhotoView(), HEAD_PHOTO_SHARE).toBundle());
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void createFriendShip() {
        model.createFriendShip(getApplicationContext(), userInfoBean.getId() + "", bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.concern_success));
                    view.setConcerText(true);
                }
            }
        });
    }

    private void destroyFriendShip() {
        model.destroyFriendShip(getApplicationContext(), userInfoBean.getId() + "", bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.cancel_concern_success));
                    view.setConcerText(false);
                }
            }
        });
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PersonalityActivity.class);
        intent.putExtra(BUNDLER_PARAM_USER, bundle.getSerializable(BUNDLER_PARAM_USER));
        intent.putExtra(BUNDLER_PARAM_USER_NAME, bundle.getString(BUNDLER_PARAM_USER_NAME));
        intent.putExtra(BUNDLER_PARAM_USER_ID, bundle.getLong(BUNDLER_PARAM_USER_ID));

        context.startActivity(intent);
    }

}
