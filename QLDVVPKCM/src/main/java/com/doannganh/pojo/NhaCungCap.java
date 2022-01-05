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
public class NhaCungCap {
    private int nhacungcap_id;
    private String tencongty;
    private String diachi;
    private String tinhthanh;
    private String quocgia;
    private String email;
    private String sodt;
    private int tongmathang;
    
    @Override
    public String toString() {
        return this.getTencongty();
    }

    /**
     * @return the nhacungcap_id
     */
    public int getNhacungcap_id() {
        return nhacungcap_id;
    }

    /**
     * @param nhacungcap_id the nhacungcap_id to set
     */
    public void setNhacungcap_id(int nhacungcap_id) {
        this.nhacungcap_id = nhacungcap_id;
    }

    /**
     * @return the tencongty
     */
    public String getTencongty() {
        return tencongty;
    }

    /**
     * @param tencongty the tencongty to set
     */
    public void setTencongty(String tencongty) {
        this.tencongty = tencongty;
    }

    /**
     * @return the diachi
     */
    public String getDiachi() {
        return diachi;
    }

    /**
     * @param diachi the diachi to set
     */
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    /**
     * @return the tinhthanh
     */
    public String getTinhthanh() {
        return tinhthanh;
    }

    /**
     * @param tinhthanh the tinhthanh to set
     */
    public void setTinhthanh(String tinhthanh) {
        this.tinhthanh = tinhthanh;
    }

    /**
     * @return the quocgia
     */
    public String getQuocgia() {
        return quocgia;
    }

    /**
     * @param quocgia the quocgia to set
     */
    public void setQuocgia(String quocgia) {
        this.quocgia = quocgia;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the sodt
     */
    public String getSodt() {
        return sodt;
    }

    /**
     * @param sodt the sodt to set
     */
    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    /**
     * @return the tongmathang
     */
    public int getTongmathang() {
        return tongmathang;
    }

    /**
     * @param tongmathang the tongmathang to set
     */
    public void setTongmathang(int tongmathang) {
        this.tongmathang = tongmathang;
    }
}
