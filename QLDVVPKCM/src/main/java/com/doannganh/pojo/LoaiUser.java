/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.pojo;

/**
 *
 * @author Admin
 */
public class LoaiUser {
    private int loaiuser_id;
    private String tenloai;

    @Override
    public String toString() {
        return this.tenloai;
    }

    /**
     * @return the loaiuser_id
     */
    public int getLoaiuser_id() {
        return loaiuser_id;
    }

    /**
     * @param loaiuser_id the loaiuser_id to set
     */
    public void setLoaiuser_id(int loaiuser_id) {
        this.loaiuser_id = loaiuser_id;
    }

    /**
     * @return the tenloai
     */
    public String getTenloai() {
        return tenloai;
    }

    /**
     * @param tenloai the tenloai to set
     */
    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
    
    
}