package code.vera.myblog.view.location;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.view.RefreshView;

/**
 * Created by vera on 2017/4/20 0020.
 */

public class LocationView extends RefreshView {
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    public void onAttachView(@NonNull View view) {

    }

    public void setAddress(String address) {
        tvAddress.setText("地址：" + address);
    }

    @Override
    public void setAdapter(RvAdapter adapter) {
        super.setAdapter(adapter);

    }
}
