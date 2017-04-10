package code.vera.myblog.presenter.fragment.person;

import com.trello.rxlifecycle.FragmentEvent;

import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.personality.TabPersonInfoView;
import ww.com.core.Debug;

/**
 *
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonInfoFragment extends PresenterFragment<TabPersonInfoView, UserModel> {
    private static TabPersonInfoFragment instance;
    String uid="";
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_person_info;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        getUserInfo();
    }

    private void getUserInfo() {
        Debug.d("uid="+uid);
        model.getUserInfo(mContext,uid,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,true){
            @Override
            public void onNext(UserInfoBean userInfoBean) {
                super.onNext(userInfoBean);
                view.showInfo(userInfoBean);
            }
        });
    }

    public static TabPersonInfoFragment getInstance() {
        if (instance == null) {
            instance = new TabPersonInfoFragment();
        }
        return  instance;
    }

    public void setUid(long id) {
        uid=id+"";
        Debug.d("uid="+uid);
    }
}
