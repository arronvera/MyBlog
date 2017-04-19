package code.vera.myblog.bean;

import java.util.List;

/**
 * 地理位置
 * Created by vera on 2017/2/24 0024.
 */

public class GeoBean {

    /**
     * type : Point
     * coordinates : [30.61587,104.069649]
     */

    private String type;
    private List<Double> coordinates;//0-lat 1-lon

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
