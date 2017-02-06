package code.vera.myblog.presenter;

import rx.Subscription;

/**
 * Created by vera on 2016/12/28 0028.
 */

public interface presenter {
    void onStart();
    void onDestroy();
    void unSubscribe(Subscription subscription);
}
