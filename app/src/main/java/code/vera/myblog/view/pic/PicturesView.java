package code.vera.myblog.view.pic;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.PicturesVpAdapter;
import code.vera.myblog.bean.home.PicBean;
import code.vera.myblog.view.base.BaseView;
import code.vera.myblog.view.widget.PhotoViewPager;

/**
 * Created by vera on 2017/2/16 0016.
 */

public class PicturesView extends BaseView {
    @BindView(R.id.vp_pictures)
    PhotoViewPager viewPager;
    @BindView(R.id.tv_pic_num)
    TextView tvPicNum;
    private int currentIndex;//当前下标

    private PicturesVpAdapter adapter;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    public void setAdapter(FragmentManager fm, final List<PicBean> list,int index) {
        currentIndex=index;
        adapter=new PicturesVpAdapter(fm,list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ////////      1/4
                tvPicNum.setText(String.valueOf(position+1)+"/"+list.size());
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(index);//跳转到指定页
    }

    /**
     * 获取当前下标
     * @return
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
    public Fragment getCurrentFragment(){
        return adapter.getItem(currentIndex);
    }
}
