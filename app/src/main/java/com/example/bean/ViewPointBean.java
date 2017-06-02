package com.example.bean;

/**
 * Created by weiyuanfei on 2017/5/31.
 */

public class ViewPointBean {

    public ViewPointBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getMatrix() {
        return matrix;
    }

    public void setMatrix(byte[] matrix) {
        this.matrix = matrix;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int id;
    /**
     * 视点名称
     */
    private String name;
    /**
     * 视点OSG矩阵
     */
    private byte[] matrix;
    /**
     * 视点缩略图
     */
    private byte[] bitmap;

    private int status; // 状态 0-normal 1-selected
}
