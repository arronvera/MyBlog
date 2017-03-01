package code.vera.myblog.presenter.fragment.other;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.SortFriendAdapter;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.AtSomebodyModel;
import code.vera.myblog.other.CharacterParser;
import code.vera.myblog.other.PinyinComparator;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.other.AtSomebodyView;
import ww.com.core.Debug;

/**
 * @好友
 * Created by vera on 2017/2/23 0023.
 */

public class AtSomebodyFragment extends PresenterFragment<AtSomebodyView,AtSomebodyModel> {
    private SortFriendAdapter adapter;
    private CharacterParser characterParser;//转换
    private PinyinComparator pinyinComparator;//拼音
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_at_somebody;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        initData();
        setAdapter();
        getFriends();
    }

    private void initData() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
    }

    private void setAdapter() {
        adapter=new SortFriendAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void getFriends() {
    model.getFriendShip(getContext(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<UserInfoBean>>(mContext,true){
        @Override
        public void onNext(List<UserInfoBean> userInfoBeen) {
            Debug.d("size="+userInfoBeen.size());
            super.onNext(userInfoBeen);
//            Collections.sort(UserInfoBean, pinyinComparator);
            adapter.addList(userInfoBeen);
        }
    });

    }
    public static AtSomebodyFragment getInstance() {
        AtSomebodyFragment instance = new AtSomebodyFragment();
        return instance;
    }
}
