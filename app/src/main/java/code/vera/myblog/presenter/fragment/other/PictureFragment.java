package code.vera.myblog.presenter.fragment.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.pic.PictureView;

/**
 * Created by vera on 2017/2/20 0020.
 */

public class PictureFragment  extends PresenterFragment<PictureView,VoidModel>  {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_picture;
    }

    public static Fragment newInstance(String url) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putSerializable("url", url);
        fragment.setArguments(args);
        return fragment;
    }
}
