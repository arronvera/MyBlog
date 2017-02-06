package code.vera.myblog.bean.home;

import java.util.List;

/**
 * Created by vera on 2017/1/19 0019.
 */

public class StatusesBean {

    /**
     * created_at : Thu Jan 19 11:19:39 +0800 2017
     * id : 4065591365113482
     * mid : 4065591365113482
     * idstr : 4065591365113482
     * text : 这个宝宝简直太可爱了，#老师都被带跑调了#[笑cry][笑cry]作为四川人，你们普通话能过几级？[白眼]#成都分享#http://t.cn/RMEXsLE ​
     * textLength : 124
     * source_allowclick : 0
     * source_type : 1
     * source :
     * favorited : false
     * truncated : false
     * in_reply_to_status_id :
     * in_reply_to_user_id :
     * in_reply_to_screen_name :
     * pic_urls : []
     * geo : null
     * user : {"id":5339196961,"idstr":"5339196961","class":1,"screen_name":"四川高校新鲜事儿","name":"四川高校新鲜事儿","province":"51","city":"1","location":"四川 成都","description":"『在四川念书就关注@四川高校新鲜事儿』我们不生产微博，我们只是微博的搬运工！爆料请私信，合作请加QQ：307826203【本平台内容未经授权，不得转载】","url":"http://qq.zone.zhanwei","profile_image_url":"http://tva1.sinaimg.cn/crop.0.0.1002.1002.50/005PkJ21jw8f3t27xe59mj30ru0rv415.jpg","cover_image":"http://ww2.sinaimg.cn/crop.0.0.920.300/005PkJ21gw1fb8r8ygw52j30pk08cwhc.jpg","cover_image_phone":"http://ww4.sinaimg.cn/crop.0.0.640.640.640/005PkJ21gw1fbg2bzc6mfj30u00u0mzm.jpg","profile_url":"sichuangaoxiao","domain":"sichuangaoxiao","weihao":"","gender":"m","followers_count":455822,"friends_count":173,"pagefriends_count":6,"statuses_count":5853,"favourites_count":68,"created_at":"Mon Nov 10 17:12:28 +0800 2014","following":true,"allow_all_act_msg":false,"geo_enabled":true,"verified":true,"verified_type":0,"remark":"","insecurity":{"sexual_content":false},"ptype":8,"allow_all_comment":true,"avatar_large":"http://tva1.sinaimg.cn/crop.0.0.1002.1002.180/005PkJ21jw8f3t27xe59mj30ru0rv415.jpg","avatar_hd":"http://tva1.sinaimg.cn/crop.0.0.1002.1002.1024/005PkJ21jw8f3t27xe59mj30ru0rv415.jpg","verified_reason":"知名本地博主 微博本地资讯博主（四川）校园拓展大使 微博校园菁英计划成员","verified_trade":"3376","verified_reason_url":"","verified_source":"","verified_source_url":"","verified_state":0,"verified_level":3,"verified_type_ext":1,"verified_reason_modified":"","verified_contact_name":"","verified_contact_email":"","verified_contact_mobile":"","follow_me":false,"online_status":0,"bi_followers_count":165,"lang":"zh-cn","star":0,"mbtype":11,"mbrank":5,"block_word":0,"block_app":1,"ability_tags":"社交","credit_score":80,"user_ability":1806,"cardid":"star_046","avatargj_id":"gj_vip_027","urank":33}
     * annotations : [{"shooting":1,"client_mblogid":"4ecdc0d4-49ef-4ef0-8a89-3cc3fa0e99b2"},{"mapi_request":true}]
     * reposts_count : 0
     * comments_count : 0
     * attitudes_count : 0
     * isLongText : false
     * mlevel : 0
     * visible : {"type":0,"list_id":0}
     * biz_ids : [230442]
     * biz_feature : 0
     * page_type : 32
     * hasActionTypeCard : 0
     * darwin_tags : []
     * hot_weibo_tags : []
     * text_tag_tips : []
     * rid : 0_0_1_2666892245177348312
     * userType : 0
     * cardid : star_046
     * positive_recom_flag : 0
     * gif_ids :
     * is_show_bulletin : 2
     */

    private String created_at;//微博创建时间
    private long id;//	微博ID
    private String mid;//	微博MID
    private String idstr;//字符串型的微博ID
    private String text;//微博信息内容
    private int textLength;//	微博长度
    private int source_allowclick;
    private int source_type;
    private String source;
    private boolean favorited;
    private boolean truncated;
    private String in_reply_to_status_id;
    private String in_reply_to_user_id;
    private String in_reply_to_screen_name;
    private GeoBean geo;//地理信息字段
    private UserBean user;//	微博作者的用户信息字段
    private int reposts_count;//	转发数
    private int comments_count;//评论数
    private int attitudes_count;//	表态数
    private boolean isLongText;
    private int mlevel;
    private VisibleBean visible;
    private int biz_feature;
    private int page_type;
    private int hasActionTypeCard;
    private String rid;
    private int userType;
    private String cardid;
    private int positive_recom_flag;
    private String gif_ids;
    private int is_show_bulletin;
    private List<?> pic_urls;
    private List<AnnotationsBean> annotations;
    private List<Integer> biz_ids;
    private List<?> darwin_tags;
    private List<?> hot_weibo_tags;
    private List<?> text_tag_tips;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextLength() {
        return textLength;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public int getSource_allowclick() {
        return source_allowclick;
    }

    public void setSource_allowclick(int source_allowclick) {
        this.source_allowclick = source_allowclick;
    }

    public int getSource_type() {
        return source_type;
    }

    public void setSource_type(int source_type) {
        this.source_type = source_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public String getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }

    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public GeoBean getGeo() {
        return geo;
    }

    public void setGeo(GeoBean geo) {
        this.geo = geo;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public boolean isIsLongText() {
        return isLongText;
    }

    public void setIsLongText(boolean isLongText) {
        this.isLongText = isLongText;
    }

    public int getMlevel() {
        return mlevel;
    }

    public void setMlevel(int mlevel) {
        this.mlevel = mlevel;
    }

    public VisibleBean getVisible() {
        return visible;
    }

    public void setVisible(VisibleBean visible) {
        this.visible = visible;
    }

    public int getBiz_feature() {
        return biz_feature;
    }

    public void setBiz_feature(int biz_feature) {
        this.biz_feature = biz_feature;
    }

    public int getPage_type() {
        return page_type;
    }

    public void setPage_type(int page_type) {
        this.page_type = page_type;
    }

    public int getHasActionTypeCard() {
        return hasActionTypeCard;
    }

    public void setHasActionTypeCard(int hasActionTypeCard) {
        this.hasActionTypeCard = hasActionTypeCard;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public int getPositive_recom_flag() {
        return positive_recom_flag;
    }

    public void setPositive_recom_flag(int positive_recom_flag) {
        this.positive_recom_flag = positive_recom_flag;
    }

    public String getGif_ids() {
        return gif_ids;
    }

    public void setGif_ids(String gif_ids) {
        this.gif_ids = gif_ids;
    }

    public int getIs_show_bulletin() {
        return is_show_bulletin;
    }

    public void setIs_show_bulletin(int is_show_bulletin) {
        this.is_show_bulletin = is_show_bulletin;
    }

    public List<?> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(List<?> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public List<AnnotationsBean> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationsBean> annotations) {
        this.annotations = annotations;
    }

    public List<Integer> getBiz_ids() {
        return biz_ids;
    }

    public void setBiz_ids(List<Integer> biz_ids) {
        this.biz_ids = biz_ids;
    }

    public List<?> getDarwin_tags() {
        return darwin_tags;
    }

    public void setDarwin_tags(List<?> darwin_tags) {
        this.darwin_tags = darwin_tags;
    }

    public List<?> getHot_weibo_tags() {
        return hot_weibo_tags;
    }

    public void setHot_weibo_tags(List<?> hot_weibo_tags) {
        this.hot_weibo_tags = hot_weibo_tags;
    }

    public List<?> getText_tag_tips() {
        return text_tag_tips;
    }

    public void setText_tag_tips(List<?> text_tag_tips) {
        this.text_tag_tips = text_tag_tips;
    }




}
