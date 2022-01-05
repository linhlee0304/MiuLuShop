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
public class LoaiHangHoa {
    private int loaihanghoa_id;
    private String tenloai;
    
    @Override
    public String toString() {
        return this.tenloai;
    }

    /**
     * @return the loaihanghoa_id
     */
    public int getLoaihanghoa_id() {
        return loaihanghoa_id;
    }

    /**
     * @param loaihanghoa_id the loaihanghoa_id to set
     */
    public void setLoaihanghoa_id(int loaihanghoa_id) {
        this.loaihanghoa_id = loaihanghoa_id;
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
