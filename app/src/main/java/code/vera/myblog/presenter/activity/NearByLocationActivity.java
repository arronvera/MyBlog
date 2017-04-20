package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.view.other.NearByLocationView;
import ww.com.core.Debug;

/**
 * 定位及其附近
 */
public class NearByLocationActivity extends PresenterActivity<NearByLocationView, VoidModel> implements LocationSource {
    public static String PARAM_LOCATION_LATITUDE = "latitude";
    public static String PARAM_LOCATION_LONGTITUDE = "longtitude";
    private AMap aMap;
    private MyLocationStyle myLocationStyle;

    @BindView(R.id.mapview)
    MapView mapView;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_near_by_location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);//创建地图
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initLocation();
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra(PARAM_LOCATION_LATITUDE, 0);
        double lon = intent.getDoubleExtra(PARAM_LOCATION_LONGTITUDE, 0);
        Debug.d("lat=" + lat + ",lon=" + lon);
        if (lat != 0 && lon != 0) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 19.5f));
        }

    }

    private void initLocation() {
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void onAttach(View viRoot) {
        super.onAttach(viRoot);
        initLocationIcon();
    }

    private void initLocationIcon() {
        myLocationStyle = new MyLocationStyle().myLocationIcon((BitmapDescriptorFactory.fromResource(R.mipmap.location_marker)));//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, NearByLocationActivity.class);
        intent.putExtra(PARAM_LOCATION_LATITUDE, bundle.getDouble(PARAM_LOCATION_LATITUDE));
        intent.putExtra(PARAM_LOCATION_LONGTITUDE, bundle.getDouble(PARAM_LOCATION_LONGTITUDE));
        context.startActivity(intent);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
