/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.ChiTietDonHang;
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
public class ChiTietDonHangService {
    private Connection conn;
    
    public ChiTietDonHangService(Connection conn) {
        this.conn = conn;
    }
    
    public List<ChiTietDonHang> getCTDH(int id) throws SQLException {
        String sql = "SELECT *, tenhanghoa, (soluong * dongia * (1 - giamgia)) AS thanhtien"
                + " FROM chitietdonhang, hanghoa"
                + " WHERE donhang_id = ?"
                + " AND chitietdonhang.hanghoa_id = hanghoa.hanghoa_id"
                + " ORDER BY donhang_id";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        
        List<ChiTietDonHang> chiTietDonHang = new ArrayList<>();
        while (rs.next()) {
            ChiTietDonHang ctdh = new ChiTietDonHang();
            ctdh.setHanghoa_id(rs.getInt("hanghoa_id"));
            ctdh.setTenhang(rs.getString("tenhanghoa"));
            ctdh.setSoluong(rs.getString("soluong"));
            ctdh.setDongia(rs.getString("dongia"));
            ctdh.setGiamgia(rs.getString("giamgia"));
            ctdh.setThanhtien(rs.getString("thanhtien"));
            
            chiTietDonHang.add(ctdh);
        }
        return chiTietDonHang;
    }
    
    public ChiTietDonHang getCTDHByIDHHDH(int idDH, int idHH) throws SQLException {
        String sql = "SELECT *, tenhanghoa, (soluong * dongia * (1 - giamgia)) AS thanhtien"
                + " FROM chitietdonhang, hanghoa"
                + " WHERE donhang_id = ? AND chitietdonhang.hanghoa_id = ?"
                + " AND chitietdonhang.hanghoa_id = hanghoa.hanghoa_id"
                + " ORDER BY donhang_id";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idDH);
        stm.setInt(2, idHH);
        ResultSet rs = stm.executeQuery();
        
        ChiTietDonHang ctdh = new ChiTietDonHang();
        while (rs.next()) {
            ctdh.setHanghoa_id(rs.getInt("hanghoa_id"));
            ctdh.setTenhang(rs.getString("tenhanghoa"));
            ctdh.setSoluong(rs.getString("soluong"));
            ctdh.setDongia(rs.getString("dongia"));
            ctdh.setGiamgia(rs.getString("giamgia"));
            ctdh.setThanhtien(rs.getString("thanhtien"));
        }
        return ctdh;
    }
    
    public boolean themCTDH(ChiTietDonHang ctdh) throws SQLException {
        
        //suaKhoaNgoai0();
        String sql = "INSERT INTO chitietdonhang(donhang_id,hanghoa_id,soluong,dongia,giamgia)"
                + " VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, ctdh.getDonhang_id());
        stm.setInt(2, ctdh.getHanghoa_id());
        stm.setString(3, ctdh.getSoluong());
        stm.setString(4, ctdh.getDongia());
        stm.setString(5, ctdh.getGiamgia());
        //suaKhoaNgoai0();
        int row = stm.executeUpdate();
        //suaKhoaNgoai1();
        return row > 0;
    }
    
    public boolean suaSoLuongCTDH(ChiTietDonHang ctdh) throws SQLException {
        
        String sql = "UPDATE chitietdonhang SET soluong = ?"
                + " WHERE donhang_id = ? AND hanghoa_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ctdh.getSoluong());
        stm.setInt(2, ctdh.getDonhang_id());
        stm.setInt(3, ctdh.getHanghoa_id());
        int row = stm.executeUpdate();
        return row > 0;
    }
    
    public boolean xoaCTDH(ChiTietDonHang ctdh) throws SQLException {
        String sql = "DELETE FROM chitietdonhang WHERE donhang_id = ? AND hanghoa_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, ctdh.getDonhang_id());
        stm.setInt(2, ctdh.getHanghoa_id());
        int row = stm.executeUpdate();
        return row > 0;
    }
    
    public List<ChiTietDonHang> getChiTietByID(int id) throws SQLException{
        String sql = "SELECT * FROM qldvvpkcm.chitietdonhang " 
                + "Where donhang_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        List<ChiTietDonHang> chiTietDH= new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            ChiTietDonHang ct = new ChiTietDonHang();
            ct.setDonhang_id(rs.getInt("donhang_id"));
            ct.setDongia(rs.getString("dongia"));
            ct.setSoluong(rs.getString("soluong"));
            ct.setGiamgia(rs.getString("giamgia"));
            
            chiTietDH.add(ct);
        }
        return chiTietDH;
    }
    
    public int tongCTDHByIDDH(int id) throws SQLException{
        String sql = "SELECT COUNT(donhang_id) AS tongCTDH FROM chitietdonhang " 
                + "WHERE donhang_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        int tong = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            tong = rs.getInt("tongCTDH");
        }
        return tong;
    }
    
    public int tongDHByID(int id) throws SQLException{
        String sql = "SELECT sum((dongia*(1-giamgia))* soluong) as tong FROM qldvvpkcm.chitietdonhang " 
                + "Where donhang_id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        int tong = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            tong = rs.getInt("tong");
        }
        return tong;
    }
    
    public int getDoanhThuByDate(String date) throws SQLException{
        String sql = "SELECT sum((dongia*(1-giamgia)) * soluong) as doanhThu " 
                +"FROM qldvvpkcm.chitietdonhang, qldvvpkcm.donhang\n" 
                + "WHERE date(ngayTaoDH) = ?"
                + "and qldvvpkcm.donhang.donhang_id = qldvvpkcm.chitietdonhang.donhang_id";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int dt = 0;
        while(rs.next()){
            dt = rs.getInt("doanhThu");
        }
        return dt;
    }
    
    public int getDoanhThuByMonth(String date) throws SQLException{
        String sql = "SELECT sum((dongia*(1-giamgia)) * soluong) as doanhThu " 
                +"FROM qldvvpkcm.chitietdonhang, qldvvpkcm.donhang\n" 
                + "WHERE qldvvpkcm.donhang.donhang_id = qldvvpkcm.chitietdonhang.donhang_id\n" 
                +"and month(ngayTaoDH) = month(?) and year(ngayTaoDH) = year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        stm.setString(2, date);
        ResultSet rs = stm.executeQuery();
        int dt = 0;
        while(rs.next()){
            dt = rs.getInt("doanhThu");
        }
        return dt;
    }
    
    public int getDoanhThuByYear(String date) throws SQLException{
        String sql = "SELECT sum((dongia*(1-giamgia) * soluong)) as doanhThu " 
                +"FROM qldvvpkcm.chitietdonhang, qldvvpkcm.donhang\n" 
                + "WHERE year(ngayTaoDH) = year(?)"
                + " and qldvvpkcm.donhang.donhang_id = qldvvpkcm.chitietdonhang.donhang_id";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int dt = 0;
        while(rs.next()){
            dt = rs.getInt("doanhThu");
        }
        return dt;
    }
    
    public int getDoanhThuUntilDate(String date) throws SQLException{
        String sql = "SELECT sum((dongia*(1-giamgia)) * soluong) as doanhThu " 
                +"FROM qldvvpkcm.chitietdonhang, qldvvpkcm.donhang\n" 
                + "WHERE date(ngayTaoDH) <= ?"
                + "and qldvvpkcm.donhang.donhang_id = qldvvpkcm.chitietdonhang.donhang_id";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int dt = 0;
        while(rs.next()){
            dt = rs.getInt("doanhThu");
        }
        return dt;
    }
    
    
    public int getDoanhThuUntilMonth(String date) throws SQLException{
        String sql = "SELECT sum((dongia*(1-giamgia)) * soluong) as doanhThu " 
                +"FROM qldvvpkcm.chitietdonhang, qldvvpkcm.donhang\n" 
                + "WHERE qldvvpkcm.donhang.donhang_id = qldvvpkcm.chitietdonhang.donhang_id\n" 
                +"and (date(ngayTaoDH) <= ? or (month(ngayTaoDH) = month(?) and year(ngayTaoDH) = year(?)))";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        stm.setString(2, date);
        stm.setString(3, date);
        ResultSet rs = stm.executeQuery();
        int dt = 0;
        while(rs.next()){
            dt = rs.getInt("doanhThu");
        }
        return dt;
    }
    
    public int getDoanhThuUntilYear(String date) throws SQLException{
        String sql = "SELECT sum((dongia*(1-giamgia)) * soluong) as doanhThu " 
                +"FROM qldvvpkcm.chitietdonhang, qldvvpkcm.donhang\n" 
                + "WHERE qldvvpkcm.donhang.donhang_id = qldvvpkcm.chitietdonhang.donhang_id\n" 
                +"and year(ngayTaoDH) <= year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int dt = 0;
        while(rs.next()){
            dt = rs.getInt("doanhThu");
        }
        return dt;
    }
}
