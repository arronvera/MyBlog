package code.vera.myblog.bean;

/**
 * 上传请求实体类
 * Created by vera on 2017/2/9 0009.
 */

public class UploadRequestBean {
    private String status;
    private int visible;//微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
    private String list_id;
    private String pic;
    private float lat;
    private float lon;
    private String annotations;
    private String rip;//真实ip
}
