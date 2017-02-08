package code.vera.myblog.presenter.fragment.home;

import android.content.Intent;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.home.HomeModel;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.HomeView;


/**
 * 首页
 * Created by vera on 2017/1/20 0020.
 */

public class HomeFragment  extends PresenterFragment<HomeView, HomeModel> {
    private HomeRequestBean requestBean;
    private HomeAdapter adapter;//适配器
    private UserInfoBean userInfoBean;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        requestBean=new HomeRequestBean();
        setAdater();
        getUser();
        getData();
    }

    private void setAdater() {
        adapter=new HomeAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void getUser() {
        model.getUserInfo(getContext(), bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<UserInfoBean>(getContext(),true){
            @Override
            public void onNext(UserInfoBean bean) {
                super.onNext(bean);
                HomeFragment.this.userInfoBean=bean;
                view.showUserInfo(bean);
            }
        });
    }

    private void getData() {
        model.getHomeTimeLine(requestBean, getContext(), bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen!=null){
                    adapter.addList(statusesBeen);
                }
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
