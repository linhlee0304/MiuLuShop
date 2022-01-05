/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.NhaCungCap_HangHoa;
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
public class NhaCungCapService {
    private Connection conn;
    
    public NhaCungCapService(Connection conn) {
        this.conn = conn;
    }
    
    public List<NhaCungCap> getNCCs() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM nhacungcap");
        
        List<NhaCungCap> rs = new ArrayList<>();
        while (r.next()) {
            NhaCungCap ncc = new NhaCungCap();
            ncc.setNhacungcap_id(r.getInt("nhacungcap_id"));
            ncc.setTencongty(r.getString("tencongty"));
            ncc.setDiachi(r.getString("diachi"));
            ncc.setTinhthanh(r.getString("tinhthanh"));
            ncc.setQuocgia(r.getString("quocgia"));
            ncc.setEmail(r.getString("email"));
            ncc.setSodt(r.getString("sodt"));
            rs.add(ncc);
        }
        return rs;
    }
    
    public List<NhaCungCap> getNCC(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        PreparedStatement stm = null;
        if (traCuu == "" || tuKhoa == "") {
            sql = "SELECT nhacungcap.*, COUNT(hanghoa_id) AS tongmathang"
                + " FROM nhacungcap, nhacungcap_hanghoa"
                + " WHERE nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " GROUP BY nhacungcap_id ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
        }
        if (traCuu == "Mã nhà cung cấp") {
            sql = "SELECT nhacungcap.*, COUNT(hanghoa_id) AS tongmathang"
                + " FROM nhacungcap, nhacungcap_hanghoa"
                + " WHERE nhacungcap.nhacungcap_id like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " GROUP BY nhacungcap_id ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Tên công ty") {
            sql = "SELECT nhacungcap.*, COUNT(hanghoa_id) AS tongmathang"
                + " FROM nhacungcap, nhacungcap_hanghoa"
                + " WHERE tencongty like concat('%', ?, '%')"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " GROUP BY nhacungcap_id ORDER BY nhacungcap_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        
        ResultSet rs = stm.executeQuery();
        
        List<NhaCungCap> nhaCungCap = new ArrayList<>();
        while (rs.next()) {
            NhaCungCap ncc = new NhaCungCap();
            ncc.setNhacungcap_id(rs.getInt("nhacungcap_id"));
            ncc.setTencongty(rs.getString("tencongty"));
            ncc.setDiachi(rs.getString("diachi"));
            ncc.setTinhthanh(rs.getString("tinhthanh"));
            ncc.setQuocgia(rs.getString("quocgia"));
            ncc.setEmail(rs.getString("email"));
            ncc.setSodt(rs.getString("sodt"));
            ncc.setTongmathang(rs.getInt("tongmathang"));
            
            nhaCungCap.add(ncc);
        }
        return nhaCungCap;
    }
    
    public int getNCCByTen(String ten) throws SQLException {
        
        String sql= "SELECT nhacungcap_id FROM nhacungcap WHERE tencongty=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        int id = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt("nhacungcap_id");
        }
        return id;
    }
    
    public String getTenCTByid(int id) throws SQLException {
        
        String sql= "SELECT tencongty FROM nhacungcap WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        String tenCT = "";
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            tenCT = rs.getString("tencongty");
        }
        return tenCT;
    }
    
    public boolean suaTenCongTy(int id, String ten)  throws SQLException {
        String sql ="UPDATE nhacungcap SET tencongty=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaDiaChi(int id, String dc)  throws SQLException {
        String sql ="UPDATE nhacungcap SET diachi=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, dc);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaTinhThanh(int id, String tt)  throws SQLException {
        String sql ="UPDATE nhacungcap SET tinhthanh=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, tt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaQuocGia(int id, String qg)  throws SQLException {
        String sql ="UPDATE nhacungcap SET quocgia=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, qg);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaEmail(int id, String email)  throws SQLException {
        String sql ="UPDATE nhacungcap SET email=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, email);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaSoDT(int id, String sodt)  throws SQLException {
        String sql ="UPDATE nhacungcap SET sodt=? WHERE nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, sodt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean themNCC(NhaCungCap ncc) throws SQLException {
        String sql = "INSERT INTO nhacungcap(tencongty, diachi, tinhthanh"
                + ", quocgia, email, sodt)"
                + " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ncc.getTencongty());
        stm.setString(2, ncc.getDiachi());
        stm.setString(3, ncc.getTinhthanh());
        stm.setString(4, ncc.getQuocgia());
        stm.setString(5, ncc.getEmail());
        stm.setString(6, ncc.getSodt());
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public int tinhChiPhiByDate(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where date(ngaynhap) = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiByMonth(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where month(ngaynhap) = month(?) and year(ngaynhap) = year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        stm.setString(2, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiByYear(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where year(ngaynhap) = year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiUntilDate(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where date(ngaynhap) <= ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiUntilMonth(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where date(ngaynhap) <= ? or (month(ngaynhap) = month(?) and year(ngaynhap) = year(?))";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        stm.setString(2, date);
        stm.setString(3, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
    
    public int tinhChiPhiUntilYear(String date) throws SQLException{
        String sql = "SELECT sum(gianhap * soluong) as chiphi FROM qldvvpkcm.nhacungcap_hanghoa"
                + " where year(ngaynhap) <= year(?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, date);
        ResultSet rs = stm.executeQuery();
        int chiphi = 0;
        while (rs.next()) {
            chiphi = rs.getInt("chiphi");
        }
        return chiphi;
    }
}
