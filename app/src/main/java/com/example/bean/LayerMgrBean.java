package com.example.bean;

import android.graphics.Bitmap;

/**
 * Created by weiyuanfei on 2017/5/26.
 */

public class LayerMgrBean {
    public LayerMgrBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    private String id;      //编号
    private String name;    //名字
    private Bitmap bitmap;  //缩略图
    private boolean show;   //图层是否显示
}
