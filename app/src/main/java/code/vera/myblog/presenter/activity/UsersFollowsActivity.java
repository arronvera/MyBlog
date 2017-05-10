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
import code.vera.myblog.bean.UserInfoBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemClickListener;
import code.vera.myblog.listener.OnItemHeadPhotoListener;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.view.user.UsersFollowsView;

import static code.vera.myblog.presenter.activity.BigPhotoActivity.PARAM_PHOTO;

public class UsersFollowsActivity extends PresenterActivity<UsersFollowsView, UserModel>
        implements OnItemClickListener ,OnItemHeadPhotoListener{
    private String id;
    private int type;
    private UserAdapter adapter;
    public static final String PARAM_USER_FOLLOW_ID = "id";
    public static final String PARAM_USER_FOLLOW_TYPE = "type";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_users_follows;
    }

    @Override
    public void onAttach(View viRoot) {
        super.onAttach(viRoot);
        Intent intent = getIntent();
        id = intent.getStringExtra(PARAM_USER_FOLLOW_ID);
        type = intent.getIntExtra(PARAM_USER_FOLLOW_TYPE, 0);
        setAdapter();
        if (type == Constants.TYPE_CONCERN) {
            getFriends();
            view.showTitle(type);
        } else if (type == Constants.TYPE_FOLLOWERES) {
            getFollowers();
            view.showTitle(type);
        }
        addListener();
    }

    private void addListener() {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemHeadPhotoListener(this);
    }

    private void getFriends() {
        model.getUserConcernes(mContext, id, "", bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<List<UserInfoBean>>(mContext, true) {
            @Override
            public void onNext(List<UserInfoBean> userInfoBeen) {
                super.onNext(userInfoBeen);
                adapter.addList(userInfoBeen);
                view.refreshFinished();
            }
        });
    }

    private void setAdapter() {
        adapter = new UserAdapter(mContext);
        view.setAdapter(adapter);
    }

    private void getFollowers() {
        model.getUserFollowers(mContext, id, "", bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<List<UserInfoBean>>(mContext, true) {
            @Override
            public void onNext(List<UserInfoBean> userInfoBeen) {
                super.onNext(userInfoBeen);
                adapter.addList(userInfoBeen);
                view.refreshFinished();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void doClick(View v) {
        finish();
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, UsersFollowsActivity.class);
        if (bundle != null) {
            intent.putExtra(PARAM_USER_FOLLOW_ID, bundle.getLong(PARAM_USER_FOLLOW_ID) + "");
            intent.putExtra(PARAM_USER_FOLLOW_TYPE, bundle.getInt(PARAM_USER_FOLLOW_TYPE));
        }
        context.startActivity(intent);
    }

    @Override
    public void onItemClickListener(View v, int pos) {
        DialogUtils.showDialog(mContext, "",
                "由于当前Api无法显示非当前登录用户，所以无法显示点击的该用户具体信息", "确定", null);
//        Bundle bundle = new Bundle();
//        bundle.putLong(BUNDLER_PARAM_USER_ID, adapter.getItem(pos).getId());
//        PersonalityActivity.start(mContext, bundle);
    }

    @Override
    public void onItemHeadPhotoListener(View v, int pos) {
        Bundle bundle=new Bundle();
        bundle.putString(PARAM_PHOTO,adapter.getItem(pos).getCover_image_phone());
        BigPhotoActivity.start(mContext,bundle);
    }
}
