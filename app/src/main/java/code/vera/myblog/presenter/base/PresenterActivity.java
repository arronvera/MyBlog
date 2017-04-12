package code.vera.myblog.presenter.base;

import android.view.View;

import butterknife.ButterKnife;
import code.vera.myblog.model.base.IModel;
import code.vera.myblog.view.base.IView;

/**
 * Created by vera on 2017/1/4 0004.
 */

public abstract class PresenterActivity<V extends IView, M extends IModel> extends
        BaseActivity implements IPresenter {
    protected V view;
    protected M model;
    public PresenterActivity(){
        PresenterHelper.bind(this);
    }

    @Override
    public void onAttach(View viRoot) {
        view.onAttachView(viRoot);
    }
    @Override
    protected void onAttach() {
        View viRoot = findViewById(android.R.id.content);
        if (view != null) {
            ButterKnife.bind(view, viRoot);
        }
        onAttach(viRoot);
    }
    @Override
    public IView getViewModule() {
        return view;
    }

    @Override
    public IModel getModel() {
        return model;
    }

    @Override
    public void setViewModule(IView iView) {
        this.view = (V) iView;
    }

    @Override
    public void setModel(IModel iModel) {
        this.model = (M) iModel;
    }
}
