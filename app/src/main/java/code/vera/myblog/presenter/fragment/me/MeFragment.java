package code.vera.myblog.presenter.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import code.vera.myblog.R;

/**
 * Created by vera on 2017/1/20 0020.
 */

public class MeFragment extends RxFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }
}
