package com.yundian.android.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liushenghan on 2017/12/16.
 *
 * @author liushenghan
 */

public class CategoryInfo implements Serializable {


    private String name;
    private int id;
    private List<SubCategory> info;
    private List<List<SubCategory>> dirs;
    // 这段代码不要删 模拟数据用的 用来显示三级分类的
//    private final static String[] items = new String[]{
//
//            "刹车盘", "离合器", "车灯", "倒车镜", "电子扇", "其他", "减震器", "散热器", "节温器", "雨刮器", "悬挂", "球头",
//            "扣手", "扶手", "储物盒", "修理包", "挡泥板轴", "杆轴", "承十字", "节泵", "方向机", "电瓶", "油底壳", "气缸", "活塞", "飞轮", "气门", "齿轮", "链条"
//    };
//    private static List<SubCategory> subs = new ArrayList<SubCategory>() {
//        {
//            for (final String item : items) {
//                add(new SubCategory() {
//                    {
//                        setName(item);
//                    }
//                });
//            }
//        }
//    };


    public static class SubCategory implements Serializable {

        @SerializedName("P_Name")
        String name;
        @SerializedName("P_ID")
        int id;
        List<SubCategory> info;

        public SubCategory() {
        }

        public SubCategory(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<SubCategory> getInfo() {
            return info;
        }

        public void setInfo(List<SubCategory> info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "SubCategory{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", info=" + info +
                    '}';
        }
    }


    public CategoryInfo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public CategoryInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SubCategory> getInfo() {
        return info;
    }

    public void setInfo(List<SubCategory> info) {
        this.info = info;
    }

    public List<List<SubCategory>> getDirs() {
        return dirs;
    }

    public void setDirs(List<List<SubCategory>> dirs) {
        this.dirs = dirs;
    }

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", info=" + info +
                ", dirs=" + dirs +
                '}';
    }
}
