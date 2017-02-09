package code.vera.myblog.presenter.activity;

import com.trello.rxlifecycle.ActivityEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.SearchUserAdapter;
import code.vera.myblog.bean.SearchUserBean;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.SearchModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.SearchView;
import ww.com.core.Debug;

public class SearchActivity extends PresenterActivity<SearchView, SearchModel> {
    private SearchUserAdapter adapter;
    private List<UserInfoBean> userInfoBeanList;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }
    @Override
    protected void onAttach() {
        super.onAttach();
        userInfoBeanList=new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {
        adapter=new SearchUserAdapter(mContext);
        view.setAdapter(adapter);
    }

    public void getData(){
        String info=view.getSearchInfo();
        String key="";
        try {
            //URL编码
            key= URLEncoder.encode(info,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.searchUsers(key,this,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<List<SearchUserBean>>(mContext,true){
            @Override
            public void onNext(final List<SearchUserBean> userBeen) {
                super.onNext(userBeen);
                for (int i=0;i<userBeen.size();i++){
                    //根据uid获取用户
                    Debug.d("uid="+userBeen.get(i).getUid());
                    model.getUserInfo(mContext,userBeen.get(i).getUid()+"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<UserInfoBean>(mContext,true){
                        @Override
                        public void onNext(UserInfoBean userInfoBean) {
                            super.onNext(userInfoBean);
                            userInfoBeanList.add(userInfoBean);
                        }
                    });
                }
                Debug.d("userinfoBeanList.size="+userInfoBeanList.size());
                adapter.addList(userInfoBeanList);

            }
        });
    }
    @OnClick(R.id.iv_search)
    public void doClick(){//搜索
        getData();
    }

}