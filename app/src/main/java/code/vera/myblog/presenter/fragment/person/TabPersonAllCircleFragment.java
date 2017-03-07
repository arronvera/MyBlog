package code.vera.myblog.presenter.fragment.person;

import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.tab.TabRepostView;

/**
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonAllCircleFragment extends PresenterFragment<TabRepostView, VoidModel> {
    static TabPersonAllCircleFragment instance;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_person_all;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
    public static TabPersonAllCircleFragment getInstance(){
        if (instance==null){
            instance=new TabPersonAllCircleFragment();
        }
        return instance;

    }

    public void setUser(UserInfoBean userInfoBean) {

    }
}
