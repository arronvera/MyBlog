package code.vera.myblog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

import ww.com.core.Debug;
import ww.com.http.OkHttpRequest;

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
        //请求打印
        OkHttpRequest.setLogging(true);
        //设置debug的标签
        Debug.setTag("<<<<");
        initImageLoader(getApplicationContext());
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
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(480, 800)
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCache(DefaultConfigurationFactory.createDiscCache(context, new
                        Md5FileNameGenerator(), 0, 100))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(150)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }
    public static DisplayImageOptions getDisplayImageOptions(int drawableRes) {
        return getDisplayImageOptions(drawableRes, drawableRes, drawableRes);
    }
    public static DisplayImageOptions getDisplayImageOptions(int onLoading,
                                                             int emptyUri, int onFail) {
        return getDisplayImageBuilder(onLoading, emptyUri, onFail).build();
    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder(
            int onLoading, int emptyUri, int onFail) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .showImageOnLoading(onLoading).showImageForEmptyUri(emptyUri)
                .showImageOnFail(onFail).build();
        return builder;
    }

}
