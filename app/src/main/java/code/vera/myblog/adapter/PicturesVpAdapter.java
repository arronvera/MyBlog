package code.vera.myblog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import code.vera.myblog.bean.home.PicBean;
import code.vera.myblog.presenter.fragment.other.PictureFragment;

/**
 * Created by vera on 2017/2/20 0020.
 */

public class PicturesVpAdapter extends FragmentPagerAdapter {
    // 界面列表
    private List<PicBean> url;
    private FragmentManager fm;
    public PicturesVpAdapter(FragmentManager fm,List<PicBean> url) {
        super(fm);
        this.fm=fm;
        this.url=url;
    }

    @Override
    public int getCount() {
        return url.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fm.findFragmentByTag(makeFragmentName(position));
        if (fragment == null) {
            fragment = newFragment(position);
        }

        return fragment;
    }
    protected Fragment newFragment(int position) {
        return PictureFragment.newInstance(url.get(position).getThumbnail_pic());
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Fragment fragment = fm.findFragmentByTag(makeFragmentName(position));
        if (fragment != null){

        }


    }

    protected String makeFragmentName(int position) {
        return url.get(position).getThumbnail_pic();
    }
}
