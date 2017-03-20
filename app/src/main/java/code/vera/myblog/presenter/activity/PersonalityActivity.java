package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.PersonalityModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.fragment.person.TabPersonInfoFragment;
import code.vera.myblog.presenter.fragment.person.TabPersonPhotosFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.PersonalityView;
import ww.com.core.Debug;


/**
 * 个人页面
 * Created by vera on 2017/2/7 0007.
 */

public class PersonalityActivity  extends PresenterActivity<PersonalityView, PersonalityModel> {
    private UserInfoBean userInfoBean;
    private String user_name;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personality;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        view.setActivity(this);
        view.setAdapter();
        Intent intent=getIntent();
        userInfoBean= (UserInfoBean) intent.getSerializableExtra("user");
        if (userInfoBean!=null){
            view.showInfo(userInfoBean);
        }
        user_name=intent.getStringExtra("user_name");
        if (!TextUtils.isEmpty(user_name)){
            //
            Debug.d("user_name="+user_name);
            model.getUserInfoByName(getApplicationContext(),user_name,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,true){
                @Override
                public void onNext(UserInfoBean userInfoBean) {
                    super.onNext(userInfoBean);
                    Debug.d("userInfoBean="+userInfoBean.toString());
                    view.showInfo(userInfoBean);
                    TabPersonInfoFragment.getInstance().setUser(userInfoBean);
//                    TabPersonAllCircleFragment.getInstance().setUser(userInfoBean);
                    TabPersonPhotosFragment.getInstance().setUser(userInfoBean);

                }
            });
        }
    }
    @OnClick({R.id.tv_concern,R.id.tv_friends,R.id.tv_followers})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.tv_concern://关注
                model.createFriendShip(getApplicationContext(),userInfoBean.getId()+"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,false){
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        super.onNext(userInfoBean);
                        // TODO: 2017/3/10 0010
                    }
                });
                break;
            case R.id.tv_friends://关注

                break;
            case R.id.tv_followers://粉丝
                Intent intent=new Intent(this,UsersFollowsActivity.class);
                startActivity(intent);

                break;
        }
    }

}
