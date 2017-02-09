package code.vera.myblog.presenter.fragment.tab;

import code.vera.myblog.R;
import code.vera.myblog.model.tab.TabCommentDetailModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.tab.TabRepostView;

/**
 * 转发
 * Created by vera on 2017/2/9 0009.
 */

public class TabRepostFragment extends PresenterFragment<TabRepostView, TabCommentDetailModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_repost;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        getReposts();
    }

    private void getReposts() {

    }
}
