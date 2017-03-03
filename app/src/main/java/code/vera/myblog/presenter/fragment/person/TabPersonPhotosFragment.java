package code.vera.myblog.presenter.fragment.person;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.tab.TabRepostView;

/**
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonPhotosFragment extends PresenterFragment<TabRepostView, VoidModel> {
    private static TabPersonPhotosFragment instance;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_person_photos;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
    public static TabPersonPhotosFragment getInstance(){
        if (instance==null){
            instance=new TabPersonPhotosFragment();
        }
        return instance;

    }
}
