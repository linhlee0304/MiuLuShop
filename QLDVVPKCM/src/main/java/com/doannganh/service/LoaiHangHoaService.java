/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.LoaiHangHoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public class LoaiHangHoaService {
    private Connection conn;
    
    public LoaiHangHoaService(Connection conn) {
        this.conn = conn;
    }
    
    public List<LoaiHangHoa> getLoaiHH() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM loaihanghoa");
        
        List<LoaiHangHoa> rs = new ArrayList<>();
        while (r.next()) {
            LoaiHangHoa lhh = new LoaiHangHoa();
            lhh.setLoaihanghoa_id(r.getInt("loaihanghoa_id"));
            lhh.setTenloai(r.getString("tenloai"));
            rs.add(lhh);
        }
        return rs;
    }
    
    public int getLoaiHHByTen(String ten) throws SQLException {
        
        String sql= "SELECT loaihanghoa_id FROM loaihanghoa WHERE tenloai=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        int id = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("loaihanghoa_id");
        }
        return id;
    }
    
    public String getLoaiHHByid(int id) throws SQLException {
        
        String sql= "SELECT tenloai FROM loaihanghoa WHERE loaihanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        String ten = "";
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            ten = rs.getString("tenloai");
        }
        return ten;
    }
    
    public HashMap<LoaiHangHoa, Integer> getTopLoaiSPBanChay(int top) throws SQLException{

        HashMap<LoaiHangHoa, Integer> hh = new HashMap<>();
        String sql = "SELECT distinct loaihanghoa.*, sum(soluong) as soluong\n" 
                +"FROM qldvvpkcm.hanghoa, qldvvpkcm.chitietdonhang,  qldvvpkcm.loaihanghoa\n" 
                +"WHERE  hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id\n" 
                +"and hanghoa.hanghoa_id = chitietdonhang.hanghoa_id\n" 
                +"GROUP BY loaihanghoa.loaihanghoa_id\n" 
                +"order by soluong desc\n" 
                +"LIMIT ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, top);
        ResultSet rs = stm.executeQuery();
        int sl = 0;
        while (rs.next()) {
            LoaiHangHoa h = new LoaiHangHoa();
            h.setLoaihanghoa_id(rs.getInt("loaihanghoa_id"));
            h.setTenloai(rs.getString("tenloai"));
            sl = rs.getInt("soluong");
            
            hh.put(h,sl);
        }
        return hh;
       }

    public HashMap<LoaiHangHoa, Integer> getTopLoaiSPBanIt(int top) throws SQLException{

        HashMap<LoaiHangHoa, Integer> hh = new HashMap<>();
        String sql = "SELECT distinct loaihanghoa.*, sum(soluong) as soluong\n" 
                +"FROM qldvvpkcm.hanghoa, qldvvpkcm.chitietdonhang,  qldvvpkcm.loaihanghoa\n" 
                +"WHERE  hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id\n" 
                +"and hanghoa.hanghoa_id = chitietdonhang.hanghoa_id\n" 
                +"GROUP BY loaihanghoa.loaihanghoa_id\n" 
                +"order by soluong asc\n" 
                +"LIMIT ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, top);
        ResultSet rs = stm.executeQuery();
        int sl = 0;
        while (rs.next()) {
            LoaiHangHoa h = new LoaiHangHoa();
            h.setLoaihanghoa_id(rs.getInt("loaihanghoa_id"));
            h.setTenloai(rs.getString("tenloai"));
            sl = rs.getInt("soluong");
            
            hh.put(h,sl);
        }
        return hh;
       }
    /*public List getTenLoai() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT tenloai FROM loaihanghoa");
        
        List rs = new ArrayList<>();
        while (r.next()) {
            LoaiHangHoa lhh = new LoaiHangHoa();
            lhh.setTenloai(r.getString("tenloai"));
            rs.add(lhh);
        }
        return rs;
    }*/
}
