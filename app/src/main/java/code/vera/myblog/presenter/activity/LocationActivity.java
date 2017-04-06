package code.vera.myblog.presenter.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import code.vera.myblog.R;

import static android.R.attr.action;

/**
 * 定位
 */
public class LocationActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, CloudSearch.OnCloudSearchListener,
        AMap.OnCameraChangeListener, AMap.OnMapLoadedListener, AMap.OnMapTouchListener
        , AMap.OnMarkerClickListener{
    private MapView mapView=null;
    private AMap aMap;
    private boolean isFollow=false;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    public static final String PARAM_ADDRESS="address";
    public static final String PARAM_LATITUDE="latitude";
    public static final String PARAM_LONGTITUDE="lontitude";

    private static final String ACTION = "action";
    private String address;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        initMap(savedInstanceState);
    }

    private void initMap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);//创建地图
        if (aMap==null){
            aMap=mapView.getMap();
        }
        //定位图标
        myLocationStyle = new MyLocationStyle().myLocationIcon((BitmapDescriptorFactory.fromResource(R.mipmap.location_marker)));//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }
    public static void startActivityForResult(Context context, Bundle bundle, int action) {
        Intent intent = new Intent(context, LocationActivity.class);
        intent.putExtra("data", bundle);
        intent.putExtra("action", action);
        ((Activity) context).startActivityForResult(intent, action);
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
@OnClick(R.id.tv_sure)
public void back(){
    Intent data = new Intent();
    data.putExtra(PARAM_ADDRESS,address);
    data.putExtra(PARAM_LATITUDE,lat);
    data.putExtra(PARAM_LONGTITUDE,lon);
    setResult(action, data);
    finish();
}
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null&&aMapLocation != null) {
            if (aMapLocation != null &&aMapLocation.getErrorCode() == 0) {
                address=aMapLocation.getAddress();
                lat=aMapLocation.getLatitude();
                lon=aMapLocation.getLongitude();
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                tvAddress.setText("地址："+address);

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onTouch(MotionEvent motionEvent) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onCloudSearched(CloudResult cloudResult, int i) {

    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }
}
