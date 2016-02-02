package com.example.qiang_pc.newtalkpal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/17.
 */
public class CommentBean implements Serializable{

    /**
     * code : 0
     * data : [{"_id":"5664fe6fcf594bc66d3f31f9","teacher_id":"5655740a1ce3d64e1c72aac8","star":5,"comment":"好评！","user_nickname":"Summer","__v":0,"create_at":"2015-12-07T03:35:11.560Z"}]
     */

    private int code;
    /**
     * _id : 5664fe6fcf594bc66d3f31f9
     * teacher_id : 5655740a1ce3d64e1c72aac8
     * star : 5
     * comment : 好评！
     * user_nickname : Summer
     * __v : 0
     * create_at : 2015-12-07T03:35:11.560Z
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

    public static class DataEntity implements Serializable{
        private String _id;
        private String teacher_id;
        private int star;
        private String comment;
        private String user_nickname;
        private int __v;
        private String create_at;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public void set__v(int __v) {
            this.__v = __v;
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

        public int getStar() {
            return star;
        }

        public String getComment() {
            return comment;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public int get__v() {
            return __v;
        }

        public String getCreate_at() {
            return create_at;
        }
    }
}
