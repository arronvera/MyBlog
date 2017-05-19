package code.vera.myblog.presenter.fragment.person;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.SimpleRecyclerCardAdapter;
import code.vera.myblog.bean.HomeRequestBean;
import code.vera.myblog.bean.PicBean;
import code.vera.myblog.bean.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemClickListener;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.activity.BigPhotoActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.personality.TabPersonPhotosView;
import ww.com.core.Debug;

/**
 * 个人相册
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonPhotosFragment extends PresenterFragment<TabPersonPhotosView, UserModel>
        implements OnItemClickListener {
    private static TabPersonPhotosFragment instance;
    private SimpleRecyclerCardAdapter adapter;
    private List<PicBean> pics;
    private HomeRequestBean homeRequestBean;
    private String uid;

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
        addListener();
    }

    private void addListener() {
        adapter.setOnItemClickListener(this);
    }

    private void initData() {
        pics = new ArrayList<>();
        homeRequestBean = new HomeRequestBean();
        homeRequestBean.setUid(uid);
        homeRequestBean.setFeature(Constants.FILTER_PHOTO);
    }

    private void getPics() {
        model.getUserTimeLine(mContext, homeRequestBean, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext, true) {
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen != null) {
//                    Debug.d("statusesBeen.size="+statusesBeen.size());
                    for (int i = 0; i < statusesBeen.size(); i++) {
                        List<PicBean> pic = statusesBeen.get(i).getPic_list();
                        if (pic != null && pic.size() != 0) {
//                            Debug.d("pic.size="+pic.size());
                            pics.addAll(pic);
                        }
                    }
//                    Debug.d("pics.size="+pics.size());
                    adapter.addList(pics);
                }
            }
        });
    }

    private void setAdapter() {
        adapter = new SimpleRecyclerCardAdapter(mContext);
        view.setAdapter(adapter);
    }

    public static TabPersonPhotosFragment getInstance() {
        if (instance == null) {
            instance = new TabPersonPhotosFragment();
        }
        return instance;
    }

    public void setUid(long id) {
        uid = id + "";
    }

    @Override
    public void onItemClickListener(View v, int pos) {
        String photo=adapter.getItem(pos).getThumbnail_pic();
        Bundle bundle=new Bundle();
        bundle.putString(BigPhotoActivity.PARAM_PHOTO,photo);
        BigPhotoActivity.start(mContext,bundle);
    }
}
