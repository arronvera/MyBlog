package code.vera.myblog.presenter.fragment.me;

import android.content.Intent;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.me.MeModel;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.me.MeView;

/**
 * Created by vera on 2017/1/20 0020.
 */

public class MeFragment extends PresenterFragment<MeView, MeModel> {
    private UserInfoBean userInfoBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        getUser();
    }
    private void getUser() {
        model.getUserInfo(getContext(), bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<UserInfoBean>(getContext(),true){
            @Override
            public void onNext(UserInfoBean bean) {
                super.onNext(bean);
                MeFragment.this.userInfoBean=bean;
                view.showUserInfo(bean);
            }
        });
    }
    @OnClick({R.id.civ_head_photo})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.civ_head_photo:
                Intent intent=new Intent(getContext(), PersonalityActivity.class);
                intent.putExtra("user",userInfoBean);
                startActivity(intent);
                break;
        }
    }
}
