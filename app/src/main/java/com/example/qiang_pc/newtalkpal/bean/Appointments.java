package com.example.qiang_pc.newtalkpal.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/19.
 */
public class Appointments {

    /**
     * code : 0
     * data : [{"_id":"56728561465955173047f257","teacher_id":"566a347e1314ca5e648a046d","teacher_name":"Luwam","price":0.01,"joinNum":1,"place":"体育中心","comment":"","time":"2015-12-19T03:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-17T09:50:25.290Z","create_at":"2015-12-17T09:50:25.290Z"},{"_id":"566e94d49d51b00c3b1aabb8","teacher_id":"56693512246d37d851cf0539","teacher_name":"Victoria","price":125,"joinNum":1,"place":"星巴克","comment":"","time":"2015-12-14T03:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-14T10:07:16.249Z","create_at":"2015-12-14T10:07:16.249Z"},{"_id":"566e7f8c9d51b00c3b1aabb7","teacher_id":"5667f7b9b22d15ff25ad3c2e","teacher_name":"Ezeqviel","price":125,"joinNum":1,"place":"金利来大厦","comment":"","time":"2015-12-15T04:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-14T08:36:28.045Z","create_at":"2015-12-14T08:36:28.045Z"},{"_id":"566e718665e4604639059ccc","teacher_id":"564999ac874f1de0084d5723","teacher_name":"Dhivhia ","price":0.01,"joinNum":1,"place":"金利来","comment":"","time":"2015-12-18T07:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-14T07:36:38.411Z","create_at":"2015-12-14T07:36:38.411Z"},{"_id":"566bc74d65e4604639059cc6","teacher_id":"564999ac874f1de0084d5695","teacher_name":"Gian","price":0.01,"joinNum":1,"place":"吃饭","comment":"","time":"2015-12-14T02:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-12T07:05:49.675Z","create_at":"2015-12-12T07:05:49.675Z"},{"_id":"566aeb76968c69141bd7a186","teacher_id":"566a347e1314ca5e648a046d","teacher_name":"Luwam","price":0.01,"joinNum":1,"place":"大姐姐","comment":"","time":"2015-12-11T02:30:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"已付款","__v":0,"updated":"2015-12-11T15:27:50.409Z","create_at":"2015-12-11T15:27:50.409Z"},{"_id":"56694e192a1e7f525bdbbfdd","teacher_id":"564999ac874f1de0084d56e4","teacher_name":"Johan","price":0.01,"joinNum":1,"place":"ff","comment":"","time":"2015-12-11T03:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-10T10:04:09.091Z","create_at":"2015-12-10T10:04:09.091Z"},{"_id":"566928e4246d37d851cf0538","teacher_id":"564999ac874f1de0084d567d","teacher_name":"Rahul","price":0.01,"joinNum":1,"place":"很好的","comment":"","time":"2015-12-15T02:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"已付款","__v":0,"updated":"2015-12-10T07:25:24.496Z","create_at":"2015-12-10T07:25:24.496Z"},{"_id":"566925b7fcc6e6ac4f8850ea","teacher_id":"564999ac874f1de0084d56a8","teacher_name":"Danny","price":0.01,"joinNum":1,"place":"计算机","comment":"","time":"2015-12-14T01:30:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-10T07:11:51.423Z","create_at":"2015-12-10T07:11:51.423Z"},{"_id":"566924af6c4875494f1a4292","teacher_id":"564999ac874f1de0084d5700","teacher_name":"Jaqviel","price":0.01,"joinNum":1,"place":"仍然","comment":"","time":"2015-12-12T03:00:00.000Z","timeLong":1,"phone":"15622788095","user_unionid":"f2460fc4-3525-4e74-9599-cb9ae0c93484","user_nickname":"Summer","state":"待处理","__v":0,"updated":"2015-12-10T07:07:27.955Z","create_at":"2015-12-10T07:07:27.955Z"}]
     */

    private int code;
    /**
     * _id : 56728561465955173047f257
     * teacher_id : 566a347e1314ca5e648a046d
     * teacher_name : Luwam
     * price : 0.01
     * joinNum : 1
     * place : 体育中心
     * comment :
     * time : 2015-12-19T03:00:00.000Z
     * timeLong : 1
     * phone : 15622788095
     * user_unionid : f2460fc4-3525-4e74-9599-cb9ae0c93484
     * user_nickname : Summer
     * state : 待处理
     * __v : 0
     * updated : 2015-12-17T09:50:25.290Z
     * create_at : 2015-12-17T09:50:25.290Z
     */

    private List<DataEntity> data=new ArrayList<DataEntity>();

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String _id;
        private String teacher_id;
        private String teacher_name;
        private String teacher_headImg;

        public String getTeacher_headImg() {
            return teacher_headImg;
        }

        public void setTeacher_headImg(String teacher_headImg) {
            this.teacher_headImg = teacher_headImg;
        }

        private double price;
        private int joinNum;
        private String place;
        private String comment;
        private String time;
        private int timeLong;
        private String phone;
        private String user_unionid;
        private String user_nickname;
        private String state;
        private int __v;
        private String updated;
        private String create_at;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setJoinNum(int joinNum) {
            this.joinNum = joinNum;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setTimeLong(int timeLong) {
            this.timeLong = timeLong;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setUser_unionid(String user_unionid) {
            this.user_unionid = user_unionid;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String get_id() {
            return _id;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public double getPrice() {
            return price;
        }

        public int getJoinNum() {
            return joinNum;
        }

        public String getPlace() {
            return place;
        }

        public String getComment() {
            return comment;
        }

        public String getTime() {
            return time;
        }

        public int getTimeLong() {
            return timeLong;
        }

        public String getPhone() {
            return phone;
        }

        public String getUser_unionid() {
            return user_unionid;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public String getState() {
            return state;
        }

        public int get__v() {
            return __v;
        }

        public String getUpdated() {
            return updated;
        }

        public String getCreate_at() {
            return create_at;
        }
    }
}
