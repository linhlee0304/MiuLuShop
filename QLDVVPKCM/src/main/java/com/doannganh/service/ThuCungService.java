/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.ThuCung;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThuCungService {
    private Connection conn;
    
    public ThuCungService(Connection conn) {
        this.conn = conn;
    }
    
    public List<ThuCung> getThuCung(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        PreparedStatement stm = null;
        if (traCuu == "" || tuKhoa == "") {
            sql = "SELECT thucung.*"
                + " FROM thucung"
                + " ORDER BY thucung_id";
            stm = this.conn.prepareStatement(sql);
        }
        if (traCuu == "Mã thú cưng") {
            sql = "SELECT thucung.*"
                + " FROM thucung"
                + " WHERE thucung_id like concat('%', ?, '%')"
                + " ORDER BY thucung_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Tên") {
            sql = "SELECT thucung.*"
                + " FROM thucung"
                + " WHERE ten like concat('%', ?, '%')"
                + " ORDER BY thucung_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Mã khách hàng") {
            sql = "SELECT thucung.*"
                + " FROM thucung"
                + " WHERE khachhang_id like concat('%', ?, '%')"
                + " ORDER BY thucung_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        
        ResultSet rs = stm.executeQuery();
        
        List<ThuCung> thuCung = new ArrayList<>();
        while (rs.next()) {
            ThuCung tc = new ThuCung();
            tc.setIdThuCung(rs.getInt("thucung_id"));
            tc.setTen(rs.getString("ten"));
            tc.setNgaySinh(rs.getString("ngaysinh"));
            tc.setGioiTinh(rs.getString("gioitinh"));
            tc.setMauLong(rs.getString("maulong"));
            tc.setTinhTrangSucKhoe(rs.getString("tinhtrangsuckhoe"));
            tc.setIdKhachHang(rs.getInt("khachhang_id"));
            
            thuCung.add(tc);
        }
        return thuCung;
    }
    
    public ThuCung getTCByIDKH(int idKH) throws SQLException {
        String sql = "SELECT * FROM thucung"
                + " WHERE khachhang_id = ?"
                + " GROUP BY khachhang_id ORDER BY khachhang_id";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idKH);
        ResultSet rs = stm.executeQuery();
        
        ThuCung tc = new ThuCung();;
        while (rs.next()) {
            tc.setIdThuCung(rs.getInt("thucung_id"));
            tc.setTen(rs.getString("ten"));
            tc.setNgaySinh(rs.getString("ngaysinh"));
            tc.setGioiTinh(rs.getString("gioitinh"));
            tc.setMauLong(rs.getString("maulong"));
            tc.setTinhTrangSucKhoe(rs.getString("tinhtrangsuckhoe"));
            tc.setIdKhachHang(rs.getInt("khachhang_id"));
        }
        return tc;
    }
    
    public boolean suaTen(int id, String ten)  throws SQLException {
        String sql ="UPDATE thucung SET ten=? WHERE thucung_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaNgaySinh(int id, String ns)  throws SQLException {
        String sql ="UPDATE thucung SET ngaysinh=? WHERE thucung_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ns);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaMauLong(int id, String ml)  throws SQLException {
        String sql ="UPDATE thucung SET maulong=? WHERE thucung_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ml);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaTinhTrangSucKhoe(int id, String ttsk)  throws SQLException {
        String sql ="UPDATE thucung SET tinhtrangsuckhoe=? WHERE thucung_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ttsk);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaGioiTinh(int id, String gt)  throws SQLException {
        String sql ="UPDATE thucung SET gioitinh=? WHERE thucung_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, gt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaMaKH(int id, int idKH)  throws SQLException {
        String sql ="UPDATE thucung SET khachhang_id=? WHERE thucung_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idKH);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean themTC(ThuCung tc) throws SQLException {
        String sql = "INSERT INTO thucung(ten,ngaysinh,gioitinh,maulong,tinhtrangsuckhoe,khachhang_id)"
                    + " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, tc.getTen());
        stm.setString(2, tc.getNgaySinh());
        stm.setString(3, tc.getGioiTinh());
        stm.setString(4, tc.getMauLong());
        stm.setString(5, tc.getTinhTrangSucKhoe());
        stm.setInt(6, tc.getIdKhachHang());
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
}
