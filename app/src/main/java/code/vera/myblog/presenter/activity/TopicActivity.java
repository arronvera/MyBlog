package code.vera.myblog.presenter.activity;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.other.TopicView;

/**
 * 话题
 */
public class TopicActivity extends PresenterActivity<TopicView,VoidModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topic;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
}
