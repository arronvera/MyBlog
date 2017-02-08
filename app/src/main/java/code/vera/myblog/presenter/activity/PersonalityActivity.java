package code.vera.myblog.presenter.activity;

import android.content.Intent;

import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.PersonalityModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.PersonalityView;

/**
 * Created by vera on 2017/2/7 0007.
 */

public class PersonalityActivity  extends PresenterActivity<PersonalityView, PersonalityModel> {
    private UserInfoBean userInfoBean;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personality;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent=getIntent();
        userInfoBean= (UserInfoBean) intent.getSerializableExtra("user");

    }
}
