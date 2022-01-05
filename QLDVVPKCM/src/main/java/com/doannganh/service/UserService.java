/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class UserService {
    private Connection conn;

    public UserService(Connection conn) {
        this.conn = conn;
    }
    
    public boolean login(User u) throws SQLException{
        String sql = "SELECT * FROM user WHERE taikhoan=? AND matkhau=? AND loaiuser_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, u.getTaikhoan());
        stm.setString(2, u.getMatkhau());
        stm.setInt(3, u.getLoaiuser_id());
        ResultSet rs = stm.executeQuery();
        
        if (rs.next())
            return true;
        return false;
    }
    
    public User getUser(String taiKhoan) throws SQLException {
        String sql = "SELECT user_id, hoten, ngaysinh, gioitinh, cmnd, taikhoan"
                + ", ngayVaoLam, email, diachi, sdt, loaiuser_id"
                + " FROM user WHERE taikhoan=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, taiKhoan);
        
        ResultSet rs = stm.executeQuery();
        User u = null;
        while (rs.next()) {
            u = new User();
            u.setUser_id(rs.getInt("user_id"));
            u.setHoten(rs.getString("hoTen"));
            u.setNgaysinh(rs.getDate("ngaysinh"));
            u.setGioitinh(rs.getString("gioitinh"));
            u.setCmnd(rs.getString("cmnd"));
            u.setTaikhoan(rs.getString("taikhoan"));
            u.setNgayvaolam(rs.getDate("ngayVaoLam"));
            u.setEmail(rs.getString("email"));
            u.setDiachi(rs.getString("diachi"));
            u.setSdt(rs.getString("sdt"));
            u.setLoaiuser_id(rs.getInt("loaiuser_id"));
        }
        return u;
    }
    public User getUserByID(int id) throws SQLException {
        String sql = "SELECT user_id, hoten, ngaysinh, gioitinh, cmnd, taikhoan"
                + ", ngayVaoLam, email, diachi, sdt, loaiuser_id"
                + " FROM user WHERE user_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        User u = null;
        while (rs.next()) {
            u = new User();
            u.setUser_id(rs.getInt("user_id"));
            u.setHoten(rs.getString("hoten"));
            u.setNgaysinh(rs.getDate("ngaysinh"));
            u.setGioitinh(rs.getString("gioitinh"));
            u.setCmnd(rs.getString("cmnd"));
            u.setTaikhoan(rs.getString("taikhoan"));
            u.setNgayvaolam(rs.getDate("ngayVaoLam"));
            u.setEmail(rs.getString("email"));
            u.setDiachi(rs.getString("diachi"));
            u.setSdt(rs.getString("sdt"));
            u.setLoaiuser_id(rs.getInt("loaiuser_id"));
        }
        return u;
    }
}
