package com.example.qiang_pc.newtalkpal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.qiang_pc.newtalkpal.R;

import java.util.List;


public class SearchDataAdapter extends MyBaseAdapter<PoiInfo> {
	private Context context;
	private LatLng mCurrentLatLng;
	public SearchDataAdapter(Context context, List<PoiInfo> data, LatLng currentLatLng) {
		super(data);
		this.context = context;
		this.mCurrentLatLng=currentLatLng;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 列表项布局，装载列表项数据
		PoiInfo item = (PoiInfo) getItem(position);
		HolderView holderView;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_list_searchpoi, parent, false);
			holderView = new HolderView();
			holderView.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			holderView.tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			holderView.tv_distance = (TextView) convertView
					.findViewById(R.id.tv_distance);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		// 设置数据
		holderView.tv_name.setText(item.name);
		holderView.tv_address.setText(item.address);
		double distance = getDistance(item.location,mCurrentLatLng) / 1000;
		Log.i("distance",distance+"");
		holderView.tv_distance.setText(String.format("%.1f",distance )+"km");
		return convertView;
	}

	public class HolderView {
		TextView tv_name;
		TextView tv_address;
		TextView tv_distance;
	}
	private double getDistance(LatLng location, LatLng currentLatLng) {
		double distance = DistanceUtil.getDistance(currentLatLng, location);//单位为米
		Log.i("MainActivity", distance + "");
		return distance;
	}
}
