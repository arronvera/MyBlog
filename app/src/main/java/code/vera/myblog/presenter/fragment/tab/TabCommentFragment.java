package code.vera.myblog.presenter.fragment.tab;

import code.vera.myblog.R;
import code.vera.myblog.model.tab.TabCommentDetailModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.tab.TabCommentView;

/**
 *评论
 * Created by vera on 2017/2/9 0009.
 */

public class TabCommentFragment  extends PresenterFragment<TabCommentView, TabCommentDetailModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_comment;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
}
