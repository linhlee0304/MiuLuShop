/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.pojo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class HangHoa {
    private int hanghoa_id;
    private String tenhanghoa;
    private String thuonghieu;
    private String soluongtrongkho;
    private String gianhap;
    private String gianiemyet;
    private String ngaysanxuat;
    private String ngayhethan;
    private String hinhanh;
    private boolean tinhtrang;
    private int loaihanghoa_id;
    private String tenloaihang;
    private String nhacungcap;

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
     * @return the tenhanghoa
     */
    public String getTenhanghoa() {
        return tenhanghoa;
    }

    /**
     * @param tenhanghoa the tenhanghoa to set
     */
    public void setTenhanghoa(String tenhanghoa) {
        this.tenhanghoa = tenhanghoa;
    }

    /**
     * @return the thuonghieu
     */
    public String getThuonghieu() {
        return thuonghieu;
    }

    /**
     * @param thuonghieu the thuonghieu to set
     */
    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    /**
     * @return the soluongtrongkho
     */
    public String getSoluongtrongkho() {
        return soluongtrongkho;
    }

    /**
     * @param soluongtrongkho the soluongtrongkho to set
     */
    public void setSoluongtrongkho(String soluongtrongkho) {
        this.soluongtrongkho = soluongtrongkho;
    }

    /**
     * @return the gianhap
     */
    public String getGianhap() {
        return gianhap;
    }

    /**
     * @param gianhap the gianhap to set
     */
    public void setGianhap(String gianhap) {
        this.gianhap = gianhap;
    }

    /**
     * @return the gianiemyet
     */
    public String getGianiemyet() {
        return gianiemyet;
    }

    /**
     * @param gianiemyet the gianiemyet to set
     */
    public void setGianiemyet(String gianiemyet) {
        this.gianiemyet = gianiemyet;
    }

    /**
     * @return the ngaysanxuat
     */
    public String getNgaysanxuat() {
        return ngaysanxuat;
    }

    /**
     * @param ngaysanxuat the ngaysanxuat to set
     */
    public void setNgaysanxuat(String ngaysanxuat) {
        this.ngaysanxuat = ngaysanxuat;
    }

    /**
     * @return the ngayhethan
     */
    public String getNgayhethan() {
        return ngayhethan;
    }

    /**
     * @param ngayhethan the ngayhethan to set
     */
    public void setNgayhethan(String ngayhethan) {
        this.ngayhethan = ngayhethan;
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
     * @return the tinhtrang
     */
    public boolean isTinhtrang() {
        return tinhtrang;
    }

    /**
     * @param tinhtrang the tinhtrang to set
     */
    public void setTinhtrang(boolean tinhtrang) {
        this.tinhtrang = tinhtrang;
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
     * @return the tenloaihang
     */
    public String getTenloaihang() {
        return tenloaihang;
    }

    /**
     * @param tenloaihang the tenloaihang to set
     */
    public void setTenloaihang(String tenloaihang) {
        this.tenloaihang = tenloaihang;
    }

    /**
     * @return the nhacungcap
     */
    public String getNhacungcap() {
        return nhacungcap;
    }

    /**
     * @param nhacungcap the nhacungcap to set
     */
    public void setNhacungcap(String nhacungcap) {
        this.nhacungcap = nhacungcap;
    }

    
}
