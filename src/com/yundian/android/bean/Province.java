package com.yundian.android.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 地址
 *
 * @author liushenghan  Created  on 2017/12/29.
 */

public class Province implements Serializable {


    private String p;
    private int city_id;
    private List<Citys> citys;

    public void setCity(String city) {
        this.p = city;
    }

    public String getCity() {
        return p;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setAreas(List<Citys> areas) {
        this.citys = areas;
    }

    public List<Citys> getAreas() {
        return citys;
    }

    @Override
    public String toString() {
        return p;
    }

    public static class Citys {

        private String city;
        private int city_id;
        private List<Areas> areas;

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setAreas(List<Areas> areas) {
            this.areas = areas;
        }

        public List<Areas> getAreas() {
            return areas;
        }

        @Override
        public String toString() {
            return city;
        }
    }

    public static class Areas {
        private String area;
        private int area_id;

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea() {
            return area;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public int getArea_id() {
            return area_id;
        }

        @Override
        public String toString() {
            return area;
        }
    }

}
