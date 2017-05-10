package code.vera.myblog.utils;

import android.content.Context;
import android.content.SharedPreferences;

import code.vera.myblog.bean.UserInfoBean;
/**
 * 存储工具
 * Created by vera on 2017/2/7 0007.
 */

public class SaveUtils {
    public static final String SHARED_PREFENCE_NAME="user";
    public static final String SHARED_PREFENCE_APP="app";

    public static final String PARAM_USER_NAME="user_name";
    public static final String PARAM_USER_ID="user_id";
    public static final String PARAM_USER_PHOTO="user_photo";
    public static final String PARAM_USER_Bg="user_bg";
    public static final String PARAM_ISNET="is_net";

    public static void saveUser(UserInfoBean userInfoBean,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putLong(PARAM_USER_ID,userInfoBean.getId());
        editor.putString(PARAM_USER_NAME,userInfoBean.getName());
        editor.putString(PARAM_USER_PHOTO,userInfoBean.getProfile_image_url());
        editor.putString(PARAM_USER_Bg,userInfoBean.getCover_image_phone());
        editor.commit();
    }
    public static UserInfoBean getUser(Context context){
        SharedPreferences preferences=context.getSharedPreferences(SHARED_PREFENCE_NAME, Context.MODE_PRIVATE);
        UserInfoBean userInfoBean=new UserInfoBean();
        userInfoBean.setId(preferences.getLong(PARAM_USER_ID,0));
        userInfoBean.setName(preferences.getString(PARAM_USER_NAME,"null"));
        userInfoBean.setProfile_image_url(preferences.getString(PARAM_USER_PHOTO,"null"));
        userInfoBean.setCover_image_phone(preferences.getString(PARAM_USER_Bg,"null"));
        return userInfoBean;
    }

    public static void saveNetState(boolean isToastNet,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFENCE_APP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(PARAM_ISNET,isToastNet);
        editor.commit();
    }
    public static Boolean getNetState(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFENCE_APP,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PARAM_ISNET,false);
    }
}
