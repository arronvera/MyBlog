package code.vera.myblog.presenter.fragment.other;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.model.AtSomebodyModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.other.AtSomebodyView;

/**
 * Created by vera on 2017/2/23 0023.
 */

public class AtSomebodyFragment extends PresenterFragment<AtSomebodyView,AtSomebodyModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_at_somebody;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }

    public static AtSomebodyFragment getInstance() {
        AtSomebodyFragment instance = new AtSomebodyFragment();
        return instance;
    }
    @OnClick(R.id.tv_cancle)
    public void doClick(){

    }
}
