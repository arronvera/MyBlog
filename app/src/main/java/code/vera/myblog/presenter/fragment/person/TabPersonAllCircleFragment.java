package code.vera.myblog.presenter.fragment.person;

import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.listener.OnItemMenuListener;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.personality.TabPersonAllCircleView;

/**
 * 个人界面的圈子
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonAllCircleFragment extends PresenterFragment<TabPersonAllCircleView, UserModel>
implements OnItemMenuListener{
    static TabPersonAllCircleFragment instance;
    private HomeRequestBean homeRequestBean;
    private HomeAdapter adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_person_all;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        homeRequestBean=new HomeRequestBean();
        setAdapter();
        addListener();
        getCircles();
    }

    private void addListener() {
        adapter.setOnItemMenuListener(this);
    }

    private void setAdapter() {
        adapter=new HomeAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void getCircles() {
        model.getUserTimeLine(getContext(),homeRequestBean,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                adapter.addList(statusesBeen);
            }
        });
    }

    public static TabPersonAllCircleFragment getInstance(){
        if (instance==null){
            instance=new TabPersonAllCircleFragment();
        }
        return instance;

    }

    @Override
    public void onItemMenuListener(View v, int pos) {

    }
}
