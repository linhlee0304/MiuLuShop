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
public class ThuCung {
    private int idThuCung;
    private String ten;
    private String ngaySinh;
    private String gioiTinh;
    private String mauLong;
    private String tinhTrangSucKhoe;
    private int idKhachHang;

    /**
     * @return the idThuCung
     */
    public int getIdThuCung() {
        return idThuCung;
    }

    /**
     * @param idThuCung the idThuCung to set
     */
    public void setIdThuCung(int idThuCung) {
        this.idThuCung = idThuCung;
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return the ngaySinh
     */
    public String getNgaySinh() {
        return ngaySinh;
    }

    /**
     * @param ngaySinh the ngaySinh to set
     */
    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    /**
     * @return the gioiTinh
     */
    public String getGioiTinh() {
        return gioiTinh;
    }

    /**
     * @param gioiTinh the gioiTinh to set
     */
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    /**
     * @return the mauLong
     */
    public String getMauLong() {
        return mauLong;
    }

    /**
     * @param mauLong the mauLong to set
     */
    public void setMauLong(String mauLong) {
        this.mauLong = mauLong;
    }

    /**
     * @return the tinhTrangSucKhoe
     */
    public String getTinhTrangSucKhoe() {
        return tinhTrangSucKhoe;
    }

    /**
     * @param tinhTrangSucKhoe the tinhTrangSucKhoe to set
     */
    public void setTinhTrangSucKhoe(String tinhTrangSucKhoe) {
        this.tinhTrangSucKhoe = tinhTrangSucKhoe;
    }

    /**
     * @return the idKhachHang
     */
    public int getIdKhachHang() {
        return idKhachHang;
    }

    /**
     * @param idKhachHang the idKhachHang to set
     */
    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }
}
