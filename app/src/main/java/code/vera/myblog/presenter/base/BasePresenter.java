package code.vera.myblog.presenter.base;

import code.vera.myblog.model.base.IModel;
import code.vera.myblog.view.base.BaseView;
import rx.Subscription;

/**
 * Created by vera on 2016/12/28 0028.
 */

public class BasePresenter<M extends IModel, V extends BaseView> implements presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void unSubscribe(Subscription subscription) {

    }
}
