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
public class User {
    private int user_id;
    private String hoten;
    private Date ngaysinh;
    private String gioitinh;
    private String cmnd;
    private String taikhoan;
    private String matkhau;
    private Date ngayvaolam;
    private String email;
    private String diachi;
    private String sdt;
    private int loaiuser_id;

    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the hoten
     */
    public String getHoten() {
        return hoten;
    }

    /**
     * @param hoten the hoten to set
     */
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    /**
     * @return the ngaysinh
     */
    public Date getNgaysinh() {
        return ngaysinh;
    }

    /**
     * @param ngaysinh the ngaysinh to set
     */
    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    /**
     * @return the gioitinh
     */
    public String getGioitinh() {
        return gioitinh;
    }

    /**
     * @param gioitinh the gioitinh to set
     */
    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    /**
     * @return the cmnd
     */
    public String getCmnd() {
        return cmnd;
    }

    /**
     * @param cmnd the cmnd to set
     */
    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    /**
     * @return the taikhoan
     */
    public String getTaikhoan() {
        return taikhoan;
    }

    /**
     * @param taikhoan the taikhoan to set
     */
    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    /**
     * @return the matkhau
     */
    public String getMatkhau() {
        return matkhau;
    }

    /**
     * @param matkhau the matkhau to set
     */
    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    /**
     * @return the ngayvaolam
     */
    public Date getNgayvaolam() {
        return ngayvaolam;
    }

    /**
     * @param ngayvaolam the ngayvaolam to set
     */
    public void setNgayvaolam(Date ngayvaolam) {
        this.ngayvaolam = ngayvaolam;
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
     * @return the sdt
     */
    public String getSdt() {
        return sdt;
    }

    /**
     * @param sdt the sdt to set
     */
    public void setSdt(String sdt) {
        this.sdt = sdt;
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

    
}
