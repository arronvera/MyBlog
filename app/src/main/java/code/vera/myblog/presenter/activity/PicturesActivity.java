package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.view.View;

import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.PicBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.view.pic.PicturesView;

/**
 * 查看多张图片
 * Created by vera on 2017/2/16 0016.
 */

public class PicturesActivity extends PresenterActivity<PicturesView,VoidModel> {
    private List<PicBean>beanList;
    private int index;
    public static final String ACTION_SAVE_PIC = "action_save_pic";
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
           beanList=statusesBean.getPic_list();
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
    @OnClick({R.id.tv_back,R.id.iv_save_pic,R.id.tv_big_pic})
    public void doClick(View v){
       switch (v.getId()){
           case R.id.tv_back:
              finish();
               break;
           case R.id.iv_save_pic://保存图片
//               Debug.d("发送---");
               sendBroadcast(new Intent(ACTION_SAVE_PIC));
               break;
           case R.id.tv_big_pic://查看大图
                //todo
               break;
       }

    }
}
