package code.vera.myblog.presenter.fragment.other;

import code.vera.myblog.R;
import code.vera.myblog.model.AtSomebodyModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.other.AtSomebodyView;

/**
 * @好友
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
        getFriends();
    }

    private void getFriends() {


    }

    public static AtSomebodyFragment getInstance() {
        AtSomebodyFragment instance = new AtSomebodyFragment();
        return instance;
    }
}
