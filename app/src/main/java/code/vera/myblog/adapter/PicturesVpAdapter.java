package code.vera.myblog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import code.vera.myblog.bean.PicBean;
import code.vera.myblog.presenter.fragment.other.PictureFragment;

/**
 * Created by vera on 2017/2/20 0020.
 */

public class PicturesVpAdapter extends FragmentStatePagerAdapter {
    // 界面列表
    private List<PicBean> url;
    public PicturesVpAdapter(FragmentManager fm,List<PicBean> url) {
        super(fm);
        this.url=url;
    }

    @Override
    public Fragment getItem(int position) {
        return PictureFragment.newInstance(url.get(position).getThumbnail_pic());
    }

    @Override
    public int getCount() {
        return url.size();
    }

}
