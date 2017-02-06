package code.vera.myblog;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

import ww.com.core.Debug;

/**
 * Created by vera on 2017/1/4 0004.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private ArrayList<Activity> runActivity = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //设置debug的标签
        Debug.setTag("myblog");
    }
    public static BaseApplication getInstance() {
        return instance;
    }
    public void addRunActivity(Activity _value) {
        if (this.runActivity == null) {
            this.runActivity = new ArrayList<>();
        }
        if (!this.runActivity.contains(_value)) {
            this.runActivity.add(_value);
        }
    }

    /**
     * 移除Activity
     * @param _value
     */
    public void removeRunActivity(Activity _value) {
        if (this.runActivity != null) {
            this.runActivity.remove(_value);
        }
    }
}
