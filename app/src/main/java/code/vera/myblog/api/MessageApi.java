package code.vera.myblog.api;

import android.content.Context;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

import static code.vera.myblog.api.BaseApi.onGet;

/**
 * Created by vera on 2017/2/10 0010.
 */

public class MessageApi {
    /**
     * 获取@我的评论
     * @param context
     * @param page
     * @return
     */
    public static Observable<String > getMentions(Context context,int page) {
        String url = NetWorkConfig.COMMENT_MENTION;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("since_id","0");
        params.addParameters("max_id","0");
        params.addParameters("count","50");
        params.addParameters("page",page+"");//返回结果的页码，默认为1。
        params.addParameters("filter_by_author","0");//作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
        params.addParameters("filter_by_source","0");//来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0

        return onGet(url, params);
    }
}
