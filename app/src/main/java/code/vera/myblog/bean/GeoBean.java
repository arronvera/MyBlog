package code.vera.myblog.bean;

import java.util.List;

/**
 * 地理位置
 * Created by vera on 2017/2/24 0024.
 */

public class GeoBean {
    private String type;
    private List<Double>coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
