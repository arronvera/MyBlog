package code.vera.myblog.presenter.activity;

import com.trello.rxlifecycle.ActivityEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.SearchUserBean;
import code.vera.myblog.model.SearchModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.SearchView;
import ww.com.core.Debug;

public class SearchActivity extends PresenterActivity<SearchView, SearchModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }
    @Override
    protected void onAttach() {
        super.onAttach();
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
            public void onNext(List<SearchUserBean> userBeen) {
                super.onNext(userBeen);
                Debug.d("users.size="+userBeen.size());
            }
        });
    }
    @OnClick(R.id.iv_search)
    public void doClick(){//搜索
        getData();
    }

}
