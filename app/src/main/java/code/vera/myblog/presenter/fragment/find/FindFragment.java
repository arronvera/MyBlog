package code.vera.myblog.presenter.fragment.find;

import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.FindAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.listener.OnItemConcernListener;
import code.vera.myblog.model.find.FindModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.find.FindView;

/**
 * Created by vera on 2017/1/20 0020.
 */

public class FindFragment extends PresenterFragment<FindView,FindModel>implements OnItemConcernListener {
    private FindAdapter adapter;
    private HomeRequestBean requestBean;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        requestBean=new HomeRequestBean();
        setAdapter();
        getTimeLine();
    }

    private void getTimeLine() {
        model.getPublic(requestBean,mContext,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                adapter.addList(statusesBeen);
            }
        });
    }

    private void setAdapter() {
        adapter=new FindAdapter(mContext);
        view.setAdapter(adapter);
    }

    @Override
    public void onItemConcern(final View view, int pos) {
        //关注
        long id=adapter.getItem(pos).getId();
        model.createFriendShip(mContext,id+"",bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)){
                    if (!TextUtils.isEmpty(s)) {
                        ToastUtil.showToast(getContext(), getString(R.string.concern_success));
                    }
                }
            }
        });
    }
}
