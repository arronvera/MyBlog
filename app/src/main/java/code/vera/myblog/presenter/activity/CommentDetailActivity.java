package code.vera.myblog.presenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.RetweetedStatusBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.CommentDetailModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.CommentDetailView;

import static code.vera.myblog.presenter.activity.PersonalityActivity.BUNDLER_PARAM_USER;
import static code.vera.myblog.presenter.activity.TopicActivity.BUNDLER_PARAM_TOPIC;

/**
 * 评论详情
 */
public class CommentDetailActivity extends PresenterActivity<CommentDetailView, CommentDetailModel>
        implements OnItemAtListener, OnItemTopicListener, OnItemLinkListener {

    public static long id;
    private StatusesBean statusesBean;
    public static final  String BUNDLE_PARAM_STATUS="status";
    public static final  String ACTION_UPDATE_COMMENT_NUM="com.action.update.comment";
    private UpdateBroadCast broadCast;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        broadCast=new UpdateBroadCast();
        broadCast.register();
        initData();
        view.setActivity(this);
        view.setAdapter();
        addListener();

    }

    private void initData() {
        Intent intent = getIntent();
        StatusesBean statusesBean = (StatusesBean) intent.getSerializableExtra("status");
        if (statusesBean != null && statusesBean.getId() != 0) {
            id = statusesBean.getId();
            view.showInfo(statusesBean);
            this.statusesBean = statusesBean;
        }
        RetweetedStatusBean retweetedStatusBean = (RetweetedStatusBean) intent.getSerializableExtra("retweeted_status");
        if (retweetedStatusBean != null && retweetedStatusBean.getId() != 0) {
            id = retweetedStatusBean.getId();
            view.showInfo2(retweetedStatusBean);
        }
    }

    private void addListener() {
        view.setOnItemAtListener(this);
        view.setOnItemTopicListener(this);
        view.setOnItemLinkListener(this);
    }

    @OnClick({R.id.rl_item_comment, R.id.rl_item_repost,
            R.id.rl_item_like,R.id.iv_back,R.id.crv_photo,R.id.iv_shoucang})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item_comment://评论
                Bundle bundle = new Bundle();
                bundle.putInt(PostActivity.PARAM_POST_TYPE, Constants.POST_TYPE_COMMENT);
                bundle.putSerializable(PostActivity.PARAM_STATUS_BEAN, statusesBean);
                PostActivity.start(this, bundle);
                break;
            case R.id.rl_item_repost://转发
                bundle = new Bundle();
                bundle.putInt(PostActivity.PARAM_POST_TYPE, Constants.POST_TYPE_REPOST);
                bundle.putSerializable(PostActivity.PARAM_STATUS_BEAN, statusesBean);
                PostActivity.start(this, bundle);
                break;
            case R.id.rl_item_like://喜欢
                view.showLikeView();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.crv_photo://头像
                Intent intent = new Intent(this, PersonalityActivity.class);
                intent.putExtra(BUNDLER_PARAM_USER, statusesBean.getUserBean());
                startActivity(intent);
                break;
            case R.id.iv_shoucang://收藏
                createFavorites();
                break;
        }
    }

    private void createFavorites() {
        model.createFavorites(id+"",mContext,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<String>(mContext,false){
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)){
                    view.showCollectionView();
                }else {
                    ToastUtil.showToast(mContext,getString(R.string.wront_net));
                }
            }
        });
    }

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLER_PARAM_USER,str.substring(str.indexOf("@") + 1, str.length()));
        PersonalityActivity.start(mContext,bundle);
//        Intent intent = new Intent(this, PersonalityActivity.class);
//        intent.putExtra("user_name", str.substring(str.indexOf("@") + 1, str.length()));
//        startActivity(intent);
    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLER_PARAM_TOPIC,str);
        TopicActivity.start(mContext,bundle);
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, CommentDetailActivity.class);
        intent.putExtra(BUNDLE_PARAM_STATUS, bundle.getSerializable(BUNDLE_PARAM_STATUS));
        context.startActivity(intent);
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str, int type) {
        //链接
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("link", str);
        startActivity(intent);
    }
    public class UpdateBroadCast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            view.updateView();
        }
        public void register(){
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_UPDATE_COMMENT_NUM);//更新
            mContext.registerReceiver(this, filter);
        }
        public void unRegister(){
            mContext.unregisterReceiver(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadCast.unRegister();
    }
}
