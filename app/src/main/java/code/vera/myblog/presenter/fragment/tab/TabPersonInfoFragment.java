package code.vera.myblog.presenter.fragment.tab;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.tab.TabRepostView;

/**
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonInfoFragment extends PresenterFragment<TabRepostView, VoidModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_person_info;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
}
