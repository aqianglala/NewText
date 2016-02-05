package com.example.qiang_pc.newtalkpal.bean;

import java.io.Serializable;

public class PoiBean implements Serializable {
    public String city;
    public String name;
    public String address;
    //	public LatLng latLng;//我发现要想在Activity之间传递数据必须所有的内容都实现serializable接口才行
    public double longitude;
    public double latitude;
    public double distance;
}
