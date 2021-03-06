package code.vera.myblog.presenter.base;

import android.view.View;

import code.vera.myblog.model.base.IModel;
import code.vera.myblog.view.base.IView;

/**
 * Created by vera on 2017/1/4 0004.
 */

public interface IPresenter<V extends IView, M extends IModel> {

    /**
     * 绑定 IView
     * @param viRoot
     */
    void onAttach(View viRoot);

    V getViewModule();

    M getModel();

    void setViewModule(V v);

    void setModel(M m);
}
