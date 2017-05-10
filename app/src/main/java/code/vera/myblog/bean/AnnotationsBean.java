package code.vera.myblog.bean;

/**
 * Created by vera on 2017/1/19 0019.
 */

public  class AnnotationsBean {
    /**
     * shooting : 1
     * client_mblogid : 4ecdc0d4-49ef-4ef0-8a89-3cc3fa0e99b2
     * mapi_request : true
     */

    private int shooting;
    private String client_mblogid;
    private boolean mapi_request;

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public String getClient_mblogid() {
        return client_mblogid;
    }

    public void setClient_mblogid(String client_mblogid) {
        this.client_mblogid = client_mblogid;
    }

    public boolean isMapi_request() {
        return mapi_request;
    }

    public void setMapi_request(boolean mapi_request) {
        this.mapi_request = mapi_request;
    }
}

