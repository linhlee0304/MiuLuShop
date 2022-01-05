/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class ChiTietDonHang {
    private int donhang_id;
    private int hanghoa_id;
    private String tenhang;
    private String loaihang;
    private String soluong;
    private String dongia;
    private String giamgia;
    private String thanhtien;
    private String hinhanh;

    

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
     * @return the hanghoa_id
     */
    public int getHanghoa_id() {
        return hanghoa_id;
    }

    /**
     * @param hanghoa_id the hanghoa_id to set
     */
    public void setHanghoa_id(int hanghoa_id) {
        this.hanghoa_id = hanghoa_id;
    }

    /**
     * @return the tenhang
     */
    public String getTenhang() {
        return tenhang;
    }

    /**
     * @param tenhang the tenhang to set
     */
    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    /**
     * @return the loaihang
     */
    public String getLoaihang() {
        return loaihang;
    }

    /**
     * @param loaihang the loaihang to set
     */
    public void setLoaihang(String loaihang) {
        this.loaihang = loaihang;
    }


    /**
     * @return the dongia
     */
    public String getDongia() {
        return dongia;
    }

    /**
     * @param dongia the dongia to set
     */
    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    /**
     * @return the thanhtien
     */
    public String getThanhtien() {
        return thanhtien;
    }

    /**
     * @param thanhtien the thanhtien to set
     */
    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }

    /**
     * @return the hinhanh
     */
    public String getHinhanh() {
        return hinhanh;
    }

    /**
     * @param hinhanh the hinhanh to set
     */
    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    /**
     * @return the soluong
     */
    public String getSoluong() {
        return soluong;
    }

    /**
     * @param soluong the soluong to set
     */
    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    /**
     * @return the giamgia
     */
    public String getGiamgia() {
        return giamgia;
    }

    /**
     * @param giamgia the giamgia to set
     */
    public void setGiamgia(String giamgia) {
        this.giamgia = giamgia;
    }

}
