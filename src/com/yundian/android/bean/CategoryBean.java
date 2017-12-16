package com.yundian.android.bean;

import java.util.List;

public class CategoryBean {

    private int state;
    private String msg;
    private String name;
    private List<Info> info;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public List<Info> getInfo() {
        return info;
    }

    public class Info {

        private String name;
        private int id;
        private List<Info2> info;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setInfo(List<Info2> info) {
            this.info = info;
        }

        public List<Info2> getInfo() {
            return info;
        }

    }

    public class Info2 {

        private String P_Name;
        private int P_ID;

        public void setP_Name(String P_Name) {
            this.P_Name = P_Name;
        }

        public String getP_Name() {
            return P_Name;
        }

        public void setP_ID(int P_ID) {
            this.P_ID = P_ID;
        }

        public int getP_ID() {
            return P_ID;
        }

    }

}