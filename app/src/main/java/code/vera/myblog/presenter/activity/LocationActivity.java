package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.base.VoidView;

/**
 * 定位
 */
public class LocationActivity extends PresenterActivity<VoidView,VoidModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_location;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LocationActivity.class);
        context.startActivity(starter);
    }
}
