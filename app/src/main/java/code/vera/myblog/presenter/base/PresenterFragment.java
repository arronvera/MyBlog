package code.vera.myblog.presenter.base;

import android.view.View;

import butterknife.ButterKnife;
import code.vera.myblog.model.IModel;
import code.vera.myblog.presenter.IPresenter;
import code.vera.myblog.view.IView;

/**
 * Created by vera on 2017/1/25 0025.
 */
public abstract class PresenterFragment<V extends IView, M extends IModel> extends BaseFragment implements IPresenter {
    protected V view;
    protected M model;

    public PresenterFragment() {
        PresenterHelper.bind(this);
    }

    @Override
    protected void onAttach() {
        if (view != null) {
            ButterKnife.bind(view, contentView);
        }
        onAttach(contentView);
    }

    @Override
    public void onAttach(View viRoot) {
        view.onAttachView(viRoot);
    }


    @Override
    public M getModel() {
        return model;
    }

    @Override
    public V getViewModule() {
        return view;
    }

    @Override
    public void setViewModule(IView iView) {
        view = (V) iView;
    }

    @Override
    public void setModel(IModel iModel) {
        model = (M) iModel;
    }


}
