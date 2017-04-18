package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.other.TopicModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.other.TopicView;

/**
 * 话题
 */
public class TopicActivity extends PresenterActivity<TopicView,TopicModel> {
    private HomeAdapter adapter;
    public static final String BUNDLER_PARAM_TOPIC="topic";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topic;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent=getIntent();
        String topic=intent.getStringExtra("topic");
        setAdapter();
        getTopic(topic);
    }

    private void setAdapter() {
        adapter=new HomeAdapter(this);
        view.setAdapter(adapter);
    }

    private void getTopic(String topic) {
        model.getTopicStatus(topic,mContext,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen!=null&&statusesBeen.size()!=0){
                    adapter.addList(statusesBeen);
                }else{
                    ToastUtil.showToast(mContext,getString(R.string.topic_no_share));
                }
            }
        });
    }
    @OnClick(R.id.iv_back)
    public void finish(){
        finish();
    }
    public static void start(Context context, Bundle bundle){
        Intent intent=new Intent(context,TopicActivity.class);
        intent.putExtra(BUNDLER_PARAM_TOPIC, bundle.getSerializable(BUNDLER_PARAM_TOPIC));
        context.startActivity(intent);
    }
}
