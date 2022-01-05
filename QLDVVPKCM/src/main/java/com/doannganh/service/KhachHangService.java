/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangService {
    private Connection conn;
    
    public KhachHangService(Connection conn) {
        this.conn = conn;
    }
    
    public List<KhachHang> getKhachHang(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        PreparedStatement stm = null;
        if (traCuu == "" || tuKhoa == "") {
            sql = "SELECT khachhang.*"
                + " FROM khachhang WHERE khachhang_id != 1"
                + " GROUP BY khachhang_id ORDER BY khachhang_id DESC";
            stm = this.conn.prepareStatement(sql);
        }
        if (traCuu == "Mã khách hàng") {
            sql = "SELECT khachhang.*"
                + " FROM khachhang"
                + " WHERE khachhang_id like concat('%', ?, '%')"
                + " AND khachhang_id != 1"
                + " GROUP BY khachhang_id ORDER BY khachhang_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Họ tên") {
            sql = "SELECT khachhang.*"
                + " FROM khachhang"
                + " WHERE hoten like concat('%', ?, '%')"
                + " AND khachhang_id != 1"
                + " GROUP BY khachhang_id ORDER BY khachhang_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Số điện thoại") {
            sql = "SELECT khachhang.*"
                + " FROM khachhang"
                + " WHERE sdt like concat('%', ?, '%')"
                + " AND khachhang_id != 1"
                + " GROUP BY khachhang_id ORDER BY khachhang_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        
        ResultSet rs = stm.executeQuery();
        
        List<KhachHang> khachHang = new ArrayList<>();
        while (rs.next()) {
            KhachHang kh = new KhachHang();
            kh.setIdKhachHang(rs.getInt("khachhang_id"));
            kh.setHoTen(rs.getString("hoten"));
            kh.setNgaySinh(rs.getString("ngaysinh"));
            kh.setGioiTinh(rs.getString("gioitinh"));
            kh.setDiaChi(rs.getString("diachi"));
            kh.setSdt(rs.getString("sdt"));
            kh.setDiemTichLuy(rs.getString("diemtichluy"));
            
            khachHang.add(kh);
        }
        return khachHang;
    }
    
    public List getIDKhachHang() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT khachhang_id FROM khachhang");
        
        List<Integer> id = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("khachhang_id") != 1)
                id.add(rs.getInt("khachhang_id"));
        }
        return id;
    }
    
    public boolean suaHoTen(int id, String ht)  throws SQLException {
        String sql ="UPDATE khachhang SET hoten=? WHERE khachhang_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ht);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaNgaySinh(int id, String ns)  throws SQLException {
        String sql ="UPDATE khachhang SET ngaysinh=? WHERE khachhang_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ns);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaGioiTinh(int id, String gt)  throws SQLException {
        String sql ="UPDATE khachhang SET gioitinh=? WHERE khachhang_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, gt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaDiaChi(int id, String dc)  throws SQLException {
        String sql ="UPDATE khachhang SET diachi=? WHERE khachhang_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, dc);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaSDT(int id, String sdt)  throws SQLException {
        String sql ="UPDATE khachhang SET sdt=? WHERE khachhang_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, sdt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean themKH(KhachHang kh) throws SQLException {
        String sql = "INSERT INTO khachhang(hoten,ngaysinh,gioitinh,diachi,sdt,diemtichluy)"
                    + " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kh.getHoTen());
        stm.setString(2, kh.getNgaySinh());
        stm.setString(3, kh.getGioiTinh());
        stm.setString(4, kh.getDiaChi());
        stm.setString(5, kh.getSdt());
        stm.setString(6, kh.getDiemTichLuy());
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public int slKHT() throws SQLException{
        String sql = "SELECT Count(donhang_id) as soluong FROM qldvvpkcm.donhang WHERE khachhang_id is null";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int kq = 0;
        ResultSet rs = stm.executeQuery();
        
        while(rs.next()){
            kq = rs.getInt("soluong");
        }
        return kq;
    }
    
    
    public int slKHTT() throws SQLException{
        String sql = "SELECT Count(donhang_id) as soluong FROM qldvvpkcm.donhang WHERE khachhang_id is not null";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int kq = 0;
        ResultSet rs = stm.executeQuery();
        
        while(rs.next()){
            kq = rs.getInt("soluong");
        }
        return kq;
    }
}
