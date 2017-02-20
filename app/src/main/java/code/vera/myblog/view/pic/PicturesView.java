package code.vera.myblog.view.pic;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.PicturesVpAdapter;
import code.vera.myblog.bean.home.PicBean;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/2/16 0016.
 */

public class PicturesView extends BaseView {
    @BindView(R.id.vp_pictures)
    ViewPager viewPager;
    private PicturesVpAdapter adapter;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    public void setAdapter(List<PicBean> list) {
        adapter=new PicturesVpAdapter(list);
        viewPager.setAdapter(adapter);
    }
}
