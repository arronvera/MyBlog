package code.vera.myblog.presenter.fragment.other;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.SortFriendAdapter;
import code.vera.myblog.bean.SortBean;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.callback.FriendFragmentCallBack;
import code.vera.myblog.model.AtSomebodyModel;
import code.vera.myblog.other.CharacterParser;
import code.vera.myblog.other.PinyinComparator;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.other.AtSomebodyView;

/**
 * @好友
 * Created by vera on 2017/2/23 0023.
 */

public class AtSomebodyFragment extends PresenterFragment<AtSomebodyView,AtSomebodyModel> {
    private SortFriendAdapter adapter;
    private CharacterParser characterParser;//转换
    private PinyinComparator pinyinComparator;//拼音
    @BindView(R.id.lv_friends)
    ListView lvFreiends;
    private FriendFragmentCallBack fragmentCallBack;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_at_somebody;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        initData();
        getFriends();
        addLister();
    }

    private void addLister() {
        lvFreiends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ////  给activty传值
                Bundle bundle=new Bundle();
                bundle.putSerializable("sort_bean" ,adapter.getItem(position));
                fragmentCallBack.callbackFriend(bundle);
            }
        });
    }


    private void initData() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
    }
    private void getFriends() {
    model.getConcerns(getContext(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<UserInfoBean>>(mContext,true){
        @Override
        public void onNext(List<UserInfoBean> userInfoBeen) {
            super.onNext(userInfoBeen);
            //排序
            List<SortBean> SourceDateList=filledData(userInfoBeen);
            Collections.sort(SourceDateList, pinyinComparator);
            adapter=new SortFriendAdapter(getContext(),SourceDateList);
            view.setAdapter(adapter);
        }
    });
    }
    /**
     * 填充数据，获取拼音首字母
     * @param userInfoBeen
     * @return
     */
    private List<SortBean> filledData(List<UserInfoBean> userInfoBeen){
        List<SortBean> mSortList = new ArrayList<>();

        for(int i=0; i<userInfoBeen.size(); i++){
            SortBean sortModel = new SortBean();
            sortModel.setName(userInfoBeen.get(i).getName());
            String pinyin = characterParser.getSelling(userInfoBeen.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
    public void setFragmentCallBack(FriendFragmentCallBack callBack){
        this.fragmentCallBack=callBack;
    }
}
