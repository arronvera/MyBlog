package code.vera.myblog.presenter.base;

import code.vera.myblog.model.base.IModel;
import code.vera.myblog.utils.ClassUtils;
import code.vera.myblog.view.base.IView;

/**
 * Created by vera on 2017/1/4 0004.
 */

public class PresenterHelper {

    private IPresenter presenter;

    private PresenterHelper(IPresenter presenter) {
        this.presenter = presenter;
    }

    public static void bind(IPresenter presenter) {
        PresenterHelper presenterHelper = new PresenterHelper(presenter);
        try {
            presenterHelper.build();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void build() throws IllegalAccessException, InstantiationException {
        this.presenter.setViewModule((IView) getClassView().newInstance());
        try {
            IModel model = (IModel) getClassModel().newInstance();
            model.onAttach();
            this.presenter.setModel(model);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private Class<?> getClassView() {
        return ClassUtils.getParameterizedClass(presenter.getClass(), 0);
    }

    private Class<?> getClassModel() {
        return ClassUtils.getParameterizedClass(presenter.getClass(), 1);
    }

}
