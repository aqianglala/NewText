package com.example.qiang_pc.newtalkpal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.example.qiang_pc.newtalkpal.R;

import java.util.List;


public class NearPoiAdapter extends MyBaseAdapter<PoiInfo> {
	private Context context;
	public NearPoiAdapter(Context context, List<PoiInfo> data) {
		super(data);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 列表项布局，装载列表项数据
		PoiInfo dataItem = (PoiInfo) getItem(position);
		HolderView holderView;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_list_nearpoi, parent, false);
			holderView = new HolderView();
			holderView.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			holderView.tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		// 设置数据
		holderView.tv_name.setText(dataItem.name);
		holderView.tv_address.setText(dataItem.address);
		return convertView;
	}

	public class HolderView {
		TextView tv_name;
		TextView tv_address;
	}
}
