package code.vera.myblog.presenter.activity;

import android.content.Intent;

import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.PicBean;
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
        int index=intent.getIntExtra("index",-1);//选中的下标
        StatusesBean statusesBean= (StatusesBean) intent.getSerializableExtra("bean");
        if (statusesBean!=null&&index!=-1){
            List<PicBean>beanList=statusesBean.getPic_list();
            if (statusesBean.getRetweetedStatusBean()!=null&&index!=-1){//原
                beanList=statusesBean.getRetweetedStatusBean().getPic_list();
            }
            for (int i=0;i<beanList.size();i++){
                //将thumbnail代替成bmiddle
                beanList.get(i).setThumbnail_pic(beanList.get(i).getThumbnail_pic().replace("thumbnail","bmiddle"));
            }
            view.setAdapter( getSupportFragmentManager(),beanList,index);

        }

    }
    @OnClick(R.id.tv_big_pic)
    public void DowLoadBigPic(){
        //todo 下载大图

    }


}
