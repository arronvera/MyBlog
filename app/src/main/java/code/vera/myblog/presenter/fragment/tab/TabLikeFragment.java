package code.vera.myblog.presenter.fragment.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import code.vera.myblog.R;
import code.vera.myblog.model.tab.TabCommentDetailModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.tab.TabLikeView;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class TabLikeFragment extends PresenterFragment<TabLikeView, TabCommentDetailModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_like;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_like, container, false);
        return view;
    }
}
