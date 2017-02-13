package code.vera.myblog.presenter.fragment.draft;

import code.vera.myblog.R;
import code.vera.myblog.model.draft.DraftModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.draft.DraftView;

/**
 * 草稿箱
 * Created by vera on 2017/2/13 0013.
 */

public class DraftFragment  extends PresenterFragment<DraftView, DraftModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_draft;
    }

    @Override
    protected void onAttach() {
        super.onAttach();

    }
}
