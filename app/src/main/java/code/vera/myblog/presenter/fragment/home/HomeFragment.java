package code.vera.myblog.presenter.fragment.home;

import com.trello.rxlifecycle.FragmentEvent;

import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.model.home.HomeModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.HomeView;
import rx.Subscriber;


/**
 * 首页
 * Created by vera on 2017/1/20 0020.
 */

public class HomeFragment  extends PresenterFragment<HomeView, HomeModel> {
    private HomeRequestBean requestBean;
    private HomeAdapter adapter;//适配器
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        requestBean=new HomeRequestBean();
        getData();
    }

    private void getData() {
        model.getHomeTimeLine(requestBean, getContext(), bindUntilEvent(FragmentEvent.DESTROY), new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                //todo

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }
        });
    }
}
