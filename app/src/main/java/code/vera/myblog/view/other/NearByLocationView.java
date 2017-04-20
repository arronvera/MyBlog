package code.vera.myblog.view.other;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/4/19 0019.
 */

public class NearByLocationView extends BaseView {
    @BindView(R.id.tv_current_address)
    TextView tvAddress;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }
}
