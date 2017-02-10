package code.vera.myblog.view.home;

import android.support.annotation.NonNull;
import android.view.View;

import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.view.RefreshView;

/**
 * Created by vera on 2017/1/25 0025.
 */

public class HomeView extends RefreshView {
    private RvAdapter adapter;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    @Override
    public void setAdapter(RvAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter=adapter;
    }

}
