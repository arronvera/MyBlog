package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.trello.rxlifecycle.ActivityEvent;

import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.me.MeModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.PersonalityView;
import ww.com.core.Debug;


/**
 * Created by vera on 2017/2/7 0007.
 */

public class PersonalityActivity  extends PresenterActivity<PersonalityView, MeModel> {
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
        user_name=intent.getStringExtra("user_name");
        if (!TextUtils.isEmpty(user_name)){
            //
            Debug.d("user_name="+user_name);
            model.getUserInfoByName(getApplicationContext(),user_name,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,true){
                @Override
                public void onNext(UserInfoBean userInfoBean) {
                    super.onNext(userInfoBean);
                    view.showInfo(userInfoBean);
                }
            });
        }
    }
}
