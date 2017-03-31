package code.vera.myblog.presenter.fragment.person;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.SimpleRecyclerCardAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.PicBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.personality.TabPersonPhotosView;

/**
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonPhotosFragment extends PresenterFragment<TabPersonPhotosView, UserModel> {
    private static TabPersonPhotosFragment instance;
    private SimpleRecyclerCardAdapter adapter;
    private List<PicBean>pics;
    private HomeRequestBean homeRequestBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_person_photos;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        initData();
        setAdapter();
        getPics();

    }

    private void initData() {
        pics=new ArrayList<>();
        homeRequestBean=new HomeRequestBean();
        //todo uid
    }

    private void getPics() {
        model.getUserTimeLine(mContext,homeRequestBean,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen!=null){
                    for (int i=0;i<statusesBeen.size();i++){
                        List<PicBean>pic=statusesBeen.get(i).getPic_list();
                        if (pic!=null&&pic.size()!=0){
                            pics.addAll(pic);
                        }
                    }
                    adapter.addList(pics);
                }
            }
        });
    }

    private void setAdapter() {
        adapter=new SimpleRecyclerCardAdapter(mContext);
        view.setAdapter(adapter);
    }

    public static TabPersonPhotosFragment getInstance(){
        if (instance==null){
            instance=new TabPersonPhotosFragment();
        }
        return instance;
    }
}
