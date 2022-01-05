/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.pojo;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class DonHang {
    private int donhang_id;
    private int nhanvien_id;
    private int khachhang_id;
    private String ngaytaodh;
    private String hoTenNV;
    private String hoTenKH;
    private int tongCTDH;
    private String tongTien;

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
     * @return the khachhang_id
     */
    public int getKhachhang_id() {
        return khachhang_id;
    }

    /**
     * @param khachhang_id the khachhang_id to set
     */
    public void setKhachhang_id(int khachhang_id) {
        this.khachhang_id = khachhang_id;
    }

    /**
     * @return the ngaytaodh
     */
    public String getNgaytaodh() {
        return ngaytaodh;
    }

    /**
     * @param ngaytaodh the ngaytaodh to set
     */
    public void setNgaytaodh(String ngaytaodh) {
        this.ngaytaodh = ngaytaodh;
    }

    /**
     * @return the hoTenKH
     */
    public String getHoTenKH() {
        return hoTenKH;
    }

    /**
     * @param hoTenKH the hoTenKH to set
     */
    public void setHoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
    }

    /**
     * @return the hoTenNV
     */
    public String getHoTenNV() {
        return hoTenNV;
    }

    /**
     * @param hoTenNV the hoTenNV to set
     */
    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    /**
     * @return the tongCTDH
     */
    public int getTongCTDH() {
        return tongCTDH;
    }

    /**
     * @param tongCTDH the tongCTDH to set
     */
    public void setTongCTDH(int tongCTDH) {
        this.tongCTDH = tongCTDH;
    }

    /**
     * @return the tongTien
     */
    public String getTongTien() {
        return tongTien;
    }

    /**
     * @param tongTien the tongTien to set
     */
    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }
}
