package com.example.qiang_pc.newtalkpal.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.example.qiang_pc.newtalkpal.R;
import com.example.qiang_pc.newtalkpal.adapter.NearPoiAdapter;
import com.example.qiang_pc.newtalkpal.adapter.SearchDataAdapter;
import com.example.qiang_pc.newtalkpal.bean.PoiBean;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends Activity implements
        OnGetPoiSearchResultListener,OnGetGeoCoderResultListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Context context;

    /**
     * 当前地点击点
     */
    private LatLng currentPt;

    // 定位相关
    private LocationClient mLocationClient;
    private boolean isFirstIn = true;
    private double mLatitude;
    private double mLongtitude;
    private String mCity;
    // poi搜索相关
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;

    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        this.context = this;
        initView();
        initListener();
        initData();
        mLocationClient = new LocationClient(this);     //声明LocationClient类
        mLocationClient.registerLocationListener(new MyLocationListener());    //注册监听函数
        // 初始化定位
        initLocation();
        // 初始化搜索模块，注册搜索事件监听
        initPoiSearch();
        // 开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    PoiBean poiBean;

    private void initData() {
    }

    private void initListener() {
        lv_nearpoi.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //获取此poi的地址，并销毁当前activity，然后显示在首页的地址框上
                PoiInfo item = (PoiInfo) arg0.getAdapter().getItem(arg2);
                Intent intent = new Intent();
                intent.putExtra("poidata", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        lv_keyword.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //获取此poi的地址，并销毁当前activity，然后显示在首页的地址框上
                PoiInfo item = (PoiInfo) arg0.getAdapter().getItem(arg2);
                Intent intent = new Intent();
                intent.putExtra("poidata", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() == 0) {
                    lv_keyword.setVisibility(View.GONE);
                } else {
                    //显示搜索结果布局
                    lv_keyword.setVisibility(View.VISIBLE);
                    /**
                     * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                     */
                    mPoiSearch.searchNearby((new PoiNearbySearchOption())
                            .keyword(cs.toString())
                            .location(mCurrentLatLng)
                            .radius(1000)//没设置不能显示，官方说m为单位，然而怎么设结果都一样
                            .sortType(PoiSortType.distance_from_near_to_far)
                            .pageNum(0)
                            .pageCapacity(20)
                    );
//                    mPoiSearch.searchInCity((new PoiCitySearchOption())
//                            .city(mCity)
//                            .keyword(cs.toString())
//                            .pageNum(0).pageCapacity(20));
                }
            }
        });

        mBaiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
            public void onMapDoubleClick(LatLng point) {
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(point));
            }
        });
    }

    private void addOverlay(LatLng point) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_overlay);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    private void initPoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    private void initView() {
        lv_nearpoi = (ListView) findViewById(R.id.lv_nearpoi);
        lv_keyword = (ListView) findViewById(R.id.lv_keyword);
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        //将范围缩小
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.setMapStatus(msu);

        // 隐藏缩放控件
        hideZoomControls();
        et_search = (EditText) findViewById(R.id.et_search);

    }

    private void hideZoomControls() {
        int childCount = mMapView.getChildCount();
        View zoom = null;
        for (int i = 0; i < childCount; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                zoom = child;
                break;
            }
        }

        zoom.setVisibility(View.GONE);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//       int span=1000;
//       option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    /**
     * 反向地理编码，即坐标——地址
     * @param result
     */
    private List<PoiInfo> poiList=new ArrayList<>();
    private NearPoiAdapter adapter;
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(LocationActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if(result.getPoiList().size()>0){
            //获取附近的poi列表
            poiList.clear();
            poiList.addAll(result.getPoiList());
            if(adapter==null){
                adapter = new NearPoiAdapter(context, poiList);
                lv_nearpoi.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
            Toast.makeText(LocationActivity.this, result.getAddress(),
                    Toast.LENGTH_LONG).show();
        }

    }
    private LatLng mCurrentLatLng;
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 更新经纬度
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            mCity = location.getCity();
            mCurrentLatLng=new LatLng(location.getLatitude(),location.getLongitude());
            //添加覆盖物
//            addOverlay(mCurrentLatLng);
            // 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(mCurrentLatLng));
            //设置地图信息
            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(0)//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);
            if (isFirstIn) {
                LatLng latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                //地图跳转到定位处
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;
                Toast.makeText(context, location.getAddrStr(),
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mPoiSearch.destroy();
        super.onDestroy();
    }

    /**
     * 定位到我的位置
     */
    private void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }

    /**
     * poi 查询结果回调
     */
    private List<PoiInfo> allPoi=new ArrayList<>();
    public void onGetPoiResult(PoiResult result) {
        allPoi.clear();
        Log.i("MainActivity", result.error + "onGetPoiResult");
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(context, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            if(searchDataAdapter!=null){
                searchDataAdapter.notifyDataSetChanged();
            }
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            //获取poi信息，坐标，名称，地址
            allPoi.addAll(result.getAllPoi());
                if (searchDataAdapter == null) {
                    searchDataAdapter = new SearchDataAdapter(context, allPoi,mCurrentLatLng);
                    lv_keyword.setAdapter(searchDataAdapter);
                } else {
                    searchDataAdapter.notifyDataSetChanged();
                }
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            if(searchDataAdapter!=null){
                searchDataAdapter.notifyDataSetChanged();
            }
            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(context, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * poi 详情查询结果回调
     */
    public void onGetPoiDetailResult(PoiDetailResult result) {
    }

    /**
     * 建议查询结果回调函数
     */
    private ListView lv_nearpoi;
    private ListView lv_keyword;
    private EditText et_search;
    private SearchDataAdapter searchDataAdapter;

}
