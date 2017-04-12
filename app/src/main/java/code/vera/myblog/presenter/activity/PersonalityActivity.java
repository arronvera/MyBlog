package code.vera.myblog.presenter.activity;

import android.app.ActivityOptions;
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


/**
 * 个人页面
 * Created by vera on 2017/2/7 0007.
 */

public class PersonalityActivity  extends PresenterActivity<PersonalityView, PersonalityModel> {
    private UserInfoBean userInfoBean;
    private String user_name;
    public static final String HEAD_PHOTO_SHARE="photopic";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personality;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        view.setActivity(this);
        Intent intent=getIntent();
        Bundle bundle=new Bundle();

        userInfoBean= (UserInfoBean) intent.getSerializableExtra("user");
        if (userInfoBean!=null){
            view.showInfo(userInfoBean);
            TabPersonInfoFragment.getInstance().setUid(userInfoBean.getId());
            TabPersonAllCircleFragment.getInstance().setUid(userInfoBean.getId());
            TabPersonPhotosFragment.getInstance().setUid(userInfoBean.getId());
            if (AccessTokenKeeper.readAccessToken(getApplicationContext()).getUid().equals(userInfoBean.getId()+"")){
                view.setConcernVisible(false);
            }
            bundle.putString("id",userInfoBean.getId()+"");
            bundle.putString("name",userInfoBean.getName());
        }
        user_name=intent.getStringExtra("user_name");
        if (!TextUtils.isEmpty(user_name)){
            model.getUserInfoByName(getApplicationContext(),user_name,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,true){
                @Override
                public void onNext(UserInfoBean user) {
                    super.onNext(user);
                    Debug.d("user="+user.toString());
                   userInfoBean=user;
                    view.showInfo(user);

                }
            });
            bundle.putString("name",user_name);
        }
        view.setAdapter();

    }
    @OnClick({R.id.tv_concern,R.id.tv_friends,R.id.tv_followers,R.id.iv_bg,R.id.crv_photo})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.tv_concern:
                if (userInfoBean.isFollowing()){//取消关注
                    model.destroyFriendShip(getApplicationContext(),userInfoBean.getId()+"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                        @Override
                        public void onNext(String s) {
                            super.onNext(s);
                            if (!TextUtils.isEmpty(s)){
                                ToastUtil.showToast(getApplicationContext(),getString(R.string.concern_success));
                                //todo 更新view
                            }
                        }
                    });
                }else{//关注
                    model.createFriendShip(getApplicationContext(),userInfoBean.getId()+"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                        @Override
                        public void onNext(String s) {
                            super.onNext(s);
                            if (!TextUtils.isEmpty(s)){
                                ToastUtil.showToast(getApplicationContext(),getString(R.string.concern_success));
                                //todo 更新view
                            }
                        }
                    });
                }

                break;
            case R.id.tv_friends://关注
                Bundle bundle=new Bundle();
                bundle.putLong("id",userInfoBean.getId());
                bundle.putInt("type", Constants.TYPE_CONCERN);
                UsersFollowsActivity.start(this,bundle);
                break;
            case R.id.tv_followers://粉丝
                 bundle=new Bundle();
                bundle.putLong("id",userInfoBean.getId());
                bundle.putInt("type", Constants.TYPE_FOLLOWERES);
                UsersFollowsActivity.start(this,bundle);
                break;
            case R.id.iv_bg://封面
                Intent intent=new Intent(this,BigPhotoActivity.class);
                intent.putExtra(PARAM_PHOTO,userInfoBean.getCover_image_phone());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,view.getCoverImgView(),HEAD_PHOTO_SHARE).toBundle());
                break;
            case R.id.crv_photo://头像
                Intent intent2=new Intent(this,BigPhotoActivity.class);
                intent2.putExtra(PARAM_PHOTO,userInfoBean.getAvatar_hd());
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(this,view.getPhotoView(),HEAD_PHOTO_SHARE).toBundle());
                break;
        }
    }

}
