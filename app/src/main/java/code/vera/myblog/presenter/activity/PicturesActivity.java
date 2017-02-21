package code.vera.myblog.presenter.activity;

import android.content.Intent;

import code.vera.myblog.R;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.pic.PicturesView;

/**
 * 查看多张图片
 * Created by vera on 2017/2/16 0016.
 */

public class PicturesActivity extends PresenterActivity<PicturesView,VoidModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pictures;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent=getIntent();
        int index=intent.getIntExtra("index",-1);
        StatusesBean statusesBean= (StatusesBean) intent.getSerializableExtra("bean");
        if (statusesBean!=null&&index!=-1){
            view.setAdapter( getSupportFragmentManager(),statusesBean.getPic_list(),index);
        }

    }


}
