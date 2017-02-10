package code.vera.myblog.presenter.fragment.message.tab;

import code.vera.myblog.R;
import code.vera.myblog.model.message.TabMessageModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.message.tab.TabMessageByMeView;

/**
 * 关于我
 * Created by vera on 2017/1/20 0020.
 */

public class TabMessageByMeFragment extends PresenterFragment<TabMessageByMeView,TabMessageModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_message_by_me;
    }

    @Override
    protected void onAttach() {
        super.onAttach();

    }
}
