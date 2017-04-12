package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.UserAdapter;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.user.UsersFollowsView;

public class UsersFollowsActivity extends PresenterActivity<UsersFollowsView, UserModel> {
    private String id;
    private int type;
    private UserAdapter adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_users_follows;
    }

    @Override
    public void onAttach(View viRoot) {
        super.onAttach(viRoot);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        type=intent.getIntExtra("type",0);
        initView();
        setAdapter();
        if (type== Constants.TYPE_CONCERN){
            getFriends();

        }else if (type==Constants.TYPE_FOLLOWERES){
            getFollowers();
        }
    }

    private void getFriends() {
        model.getUserConcernes(mContext,id,"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<List<UserInfoBean>>(mContext,true){
            @Override
            public void onNext(List<UserInfoBean> userInfoBeen) {
                super.onNext(userInfoBeen);
                adapter.addList(userInfoBeen);
            }
        });
    }

    private void setAdapter() {
        adapter=new UserAdapter(mContext);
        view.setAdapter(adapter);
    }

    private void getFollowers() {
        model.getUserFollowers(mContext,id,"",bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<List<UserInfoBean>>(mContext,true){
            @Override
            public void onNext(List<UserInfoBean> userInfoBeen) {
                super.onNext(userInfoBeen);
                adapter.addList(userInfoBeen);
            }
        });
    }

    private void initView() {

    }
    @OnClick(R.id.iv_back)
    public void doClick(View v){
        finish();
    }
    public static void start(Context context, Bundle bundle){
        Intent intent=new Intent(context,UsersFollowsActivity.class);
        if (bundle!=null){
            intent.putExtra("id",bundle.getLong("id")+"");
            intent.putExtra("type",bundle.getInt("type"));
        }
        context.startActivity(intent);
    }
}
