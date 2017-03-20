package code.vera.myblog.presenter.activity;

import android.view.View;

import code.vera.myblog.R;
import code.vera.myblog.model.user.UsersFollowsModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.user.UsersFollowsView;

public class UsersFollowsActivity extends PresenterActivity<UsersFollowsView, UsersFollowsModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_users_follows;
    }

    @Override
    public void onAttach(View viRoot) {
        super.onAttach(viRoot);

    }
}
