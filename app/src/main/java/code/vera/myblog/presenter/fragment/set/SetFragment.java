package code.vera.myblog.presenter.fragment.set;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.base.VoidView;

/**
 * 设置
 * Created by vera on 2017/2/13 0013.
 */

public class SetFragment extends PresenterFragment<VoidView,VoidModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_set;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
}
