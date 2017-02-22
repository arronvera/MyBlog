package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.text.TextUtils;

import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.PersonalityModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.PersonalityView;
import ww.com.core.Debug;


/**
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
        Intent intent=getIntent();
        userInfoBean= (UserInfoBean) intent.getSerializableExtra("user");
        user_name=intent.getStringExtra("user_name");
        if (!TextUtils.isEmpty(user_name)){
            //
            Debug.d("user_name="+user_name);
        }
    }
}
