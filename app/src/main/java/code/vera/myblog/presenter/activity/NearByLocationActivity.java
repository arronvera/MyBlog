package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.view.other.NearByLocationView;
import ww.com.core.Debug;

/**
 * 定位及其附近
 */
public class NearByLocationActivity extends PresenterActivity<NearByLocationView, VoidModel> {
    public static String PARAM_LOCATION_LATITUDE = "latitude";
    public static String PARAM_LOCATION_LONGTITUDE = "longtitude";
    private AMap aMap;
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
        if (aMap==null){
            aMap=mapView.getMap();
        }
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra(PARAM_LOCATION_LATITUDE, 0);
        double lon = intent.getDoubleExtra(PARAM_LOCATION_LONGTITUDE, 0);
        //todo 显示
        Debug.d("lat=" + lat + ",lon=" + lon);
        if (lat != 0 && lon != 0) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 19.5f));
        }

    }

    @Override
    public void onAttach(View viRoot) {
        super.onAttach(viRoot);
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, NearByLocationActivity.class);
        intent.putExtra(PARAM_LOCATION_LATITUDE, bundle.getDouble(PARAM_LOCATION_LATITUDE));
        intent.putExtra(PARAM_LOCATION_LONGTITUDE, bundle.getDouble(PARAM_LOCATION_LONGTITUDE));
        context.startActivity(intent);
    }
}
