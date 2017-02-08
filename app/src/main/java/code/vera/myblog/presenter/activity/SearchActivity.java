package code.vera.myblog.presenter.activity;

import code.vera.myblog.R;
import code.vera.myblog.model.SearchModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.SearchView;

public class SearchActivity extends PresenterActivity<SearchView, SearchModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }
    @Override
    protected void onAttach() {
        super.onAttach();
    }

}
