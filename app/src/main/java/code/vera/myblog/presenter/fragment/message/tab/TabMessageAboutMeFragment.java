package code.vera.myblog.presenter.fragment.message.tab;

import code.vera.myblog.R;
import code.vera.myblog.model.message.TabMessageModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.message.tab.TabMessageAboutMeView;

/**
 * 关于我
 * Created by vera on 2017/1/20 0020.
 */

public class TabMessageAboutMeFragment extends PresenterFragment<TabMessageAboutMeView,TabMessageModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_message_about_me;
    }

    @Override
    protected void onAttach() {
        super.onAttach();

    }
}
