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
public class CapNhatHoaDon {
    private int donhang_id;
    private int nhanvien_id;
    private String ngayCapNhat;
    private String ghichu;

    /**
     * @return the donhang_id
     */
    public int getDonhang_id() {
        return donhang_id;
    }

    /**
     * @param donhang_id the donhang_id to set
     */
    public void setDonhang_id(int donhang_id) {
        this.donhang_id = donhang_id;
    }

    /**
     * @return the nhanvien_id
     */
    public int getNhanvien_id() {
        return nhanvien_id;
    }

    /**
     * @param nhanvien_id the nhanvien_id to set
     */
    public void setNhanvien_id(int nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    /**
     * @return the ngayCapNhat
     */
    public String getNgayCapNhat() {
        return ngayCapNhat;
    }

    /**
     * @param ngayCapNhat the ngayCapNhat to set
     */
    public void setNgayCapNhat(String ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    /**
     * @return the ghichu
     */
    public String getGhichu() {
        return ghichu;
    }

    /**
     * @param ghichu the ghichu to set
     */
    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
