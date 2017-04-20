package code.vera.myblog.view.location;

import android.support.annotation.NonNull;
import android.view.View;

import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.view.RefreshView;

/**
 * Created by vera on 2017/4/20 0020.
 */

public class LocationView extends RefreshView {
//    @BindView(R.id.tv_address)
//    TextView tvAddress;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    public void setAddress(String address) {
//        Debug.d("设置地址："+address);
//        tvAddress.setText("地址：" + address);
    }

    @Override
    public void setAdapter(RvAdapter adapter) {
        super.setAdapter(adapter);
    }

}
