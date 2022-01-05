/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.HangHoa;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HangHoaService {
    private Connection conn;

    public HangHoaService(Connection conn) {
        this.conn = conn;
    }
    
    public List<HangHoa> getHangHoas() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " ORDER BY hanghoa.hanghoa_id");
        
        List<HangHoa> hangHoa = new ArrayList<>();
        while (rs.next()) {
            HangHoa hh = new HangHoa();
            hh.setHanghoa_id(rs.getInt("hanghoa.hanghoa_id"));
            hh.setTenhanghoa(rs.getString("tenhanghoa"));
            hh.setThuonghieu(rs.getString("thuonghieu"));
            hh.setSoluongtrongkho(rs.getString("soluongtrongkho"));
            hh.setGianhap(rs.getString("gianhap"));
            hh.setGianiemyet(rs.getString("gianiemyet"));
            hh.setNgaysanxuat(rs.getString("ngaysanxuat"));
            hh.setNgayhethan(rs.getString("ngayhethan"));
            hh.setHinhanh(rs.getString("hinhanh"));
            hh.setTinhtrang(rs.getBoolean("tinhtrang"));
            hh.setTenloaihang(rs.getString("tenloai"));
            hh.setNhacungcap(rs.getString("tencongty"));
            
            hangHoa.add(hh);
        }
        return hangHoa;
    }
    
    public List<HangHoa> getHangHoa(String tuKhoa, String traCuu) throws SQLException {
        if (tuKhoa == null)
            throw new SQLDataException();
        String sql = "";
        PreparedStatement stm = null;
        if (traCuu == "" || tuKhoa == "")  {
            sql = "SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " ORDER BY hanghoa.hanghoa_id";
            stm = this.conn.prepareStatement(sql);
        }
        if (traCuu == "Mã hàng") {
            sql = "SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE hanghoa.hanghoa_id like concat('%', ?, '%')"
                + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " ORDER BY hanghoa.hanghoa_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Tên hàng") {
            sql = "SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE tenhanghoa like concat('%', ?, '%')"
                + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " ORDER BY hanghoa.hanghoa_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Thương hiệu") {
            sql = "SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE thuonghieu like concat('%', ?, '%')"
                + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " ORDER BY hanghoa.hanghoa_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Loại hàng") {
            sql = "SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE tenloai like concat('%', ?, '%')"
                + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " ORDER BY hanghoa.hanghoa_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        if (traCuu == "Nhà cung cấp") {
            sql = "SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE tencongty like concat('%', ?, '%')"
                + " AND hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " ORDER BY hanghoa.hanghoa_id";
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, tuKhoa);
        }
        
        ResultSet rs = stm.executeQuery();
        
        List<HangHoa> hangHoa = new ArrayList<>();
        while (rs.next()) {
            HangHoa hh = new HangHoa();
            hh.setHanghoa_id(rs.getInt("hanghoa.hanghoa_id"));
            hh.setTenhanghoa(rs.getString("tenhanghoa"));
            hh.setThuonghieu(rs.getString("thuonghieu"));
            hh.setSoluongtrongkho(rs.getString("soluongtrongkho"));
            hh.setGianhap(rs.getString("gianhap"));
            hh.setGianiemyet(rs.getString("gianiemyet"));
            hh.setNgaysanxuat(rs.getString("ngaysanxuat"));
            hh.setNgayhethan(rs.getString("ngayhethan"));
            hh.setHinhanh(rs.getString("hinhanh"));
            hh.setTinhtrang(rs.getBoolean("tinhtrang"));
            hh.setTenloaihang(rs.getString("tenloai"));
            hh.setNhacungcap(rs.getString("tencongty"));
            
            hangHoa.add(hh);
        }
        return hangHoa;
    }
    
    public HangHoa getHangHoaByID(int id) throws SQLException {
        String sql= "SELECT hanghoa.*, tenloai, tencongty, gianhap"
                + " FROM hanghoa, loaihanghoa, nhacungcap, nhacungcap_hanghoa"
                + " WHERE hanghoa.loaihanghoa_id = loaihanghoa.loaihanghoa_id"
                + " AND hanghoa.hanghoa_id = nhacungcap_hanghoa.hanghoa_id"
                + " AND nhacungcap.nhacungcap_id = nhacungcap_hanghoa.nhacungcap_id"
                + " AND hanghoa.hanghoa_id = ?"
                + " ORDER BY hanghoa.hanghoa_id";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        HangHoa hh = new HangHoa();
        while (rs.next()) {
            hh.setHanghoa_id(rs.getInt("hanghoa.hanghoa_id"));
            hh.setTenhanghoa(rs.getString("tenhanghoa"));
            hh.setThuonghieu(rs.getString("thuonghieu"));
            hh.setSoluongtrongkho(rs.getString("soluongtrongkho"));
            hh.setGianhap(rs.getString("gianhap"));
            hh.setGianiemyet(rs.getString("gianiemyet"));
            hh.setNgaysanxuat(rs.getString("ngaysanxuat"));
            hh.setNgayhethan(rs.getString("ngayhethan"));
            hh.setHinhanh(rs.getString("hinhanh"));
            hh.setTinhtrang(rs.getBoolean("tinhtrang"));
            hh.setTenloaihang(rs.getString("tenloai"));
            hh.setNhacungcap(rs.getString("tencongty"));
        }
        return hh;
    }
    
    public int getIDByTenHang(String th) throws SQLException {
        String sql= "SELECT hanghoa_id FROM hanghoa WHERE tenhanghoa =?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, th);
        
        ResultSet rs = stm.executeQuery();
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("hanghoa_id");
        }
        return id;
    }
    
    public int getIDLoaiByTenLoai(String loai) throws SQLException {
        String sql = "SELECT loaihanghoa_id FROM loaihanghoa"
                    + " WHERE tenloai=?";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, loai);
        ResultSet rs = stm.executeQuery();
        
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("tenloai");
        }
        return id;
    }
    
    public List getIDByThuongHieu(String th) throws SQLException {
        String sql = "SELECT hanghoa_id FROM hanghoa"
                    + " WHERE thuonghieu=?";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, th);
        ResultSet rs = stm.executeQuery();
        
        List l = new ArrayList<>();
        while (rs.next()) {
            l.add(rs.getInt("hanghoa_id"));
        }
        return l;
    }
    
    public String getSoLuongByIDHH(int id) throws SQLException {
        String sql = "SELECT soluongtrongkho FROM hanghoa"
                    + " WHERE hanghoa_id=?";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        
        String sl = "";
        while (rs.next()) {
            sl = rs.getString("soluongtrongkho");
        }
        return sl;
    }
    
    public List getThuongHieu() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT thuonghieu FROM hanghoa"
                + " GROUP BY thuonghieu");
        
        List thuonghieu = new ArrayList<>();
        while (r.next()) {
            thuonghieu.add(r.getString("thuonghieu"));
        }
        return thuonghieu;
    }
    
    public boolean suaGiaNiemYet(int id, String gny) throws SQLException {
        String sql = "UPDATE hanghoa SET gianiemyet=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, gny);
        stm.setInt(2, id);
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaTenHH(int id, String ten)  throws SQLException {
        String sql ="UPDATE hanghoa SET tenhanghoa=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ten);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaThuongHieu(int id, String th)  throws SQLException {
        String sql ="UPDATE hanghoa SET thuonghieu=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, th);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaSoLuong(int id, String sl)  throws SQLException {
        String sql ="UPDATE hanghoa SET soluongtrongkho=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, sl);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaGiaNhap(int id, String gn, int idNCC)  throws SQLException {
        String sql ="UPDATE nhacungcap_hanghoa SET gianhap=? WHERE hanghoa_id=? AND nhacungcap_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, gn);
        stm.setInt(2, id);
        stm.setInt(3, idNCC);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaNgaySX(int id, String ngaySX)  throws SQLException {
        String sql ="UPDATE hanghoa SET ngaysanxuat=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ngaySX);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaNgayHH(int id, String ngayHH)  throws SQLException {
        String sql ="UPDATE hanghoa SET ngayhethan=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, ngayHH);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    
    public boolean suaTinhTrang(int id, Boolean tt)  throws SQLException {
        String sql ="UPDATE hanghoa SET tinhtrang=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setBoolean(1, tt);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        return row > 0;
    }
    
    public boolean suaLoaiHH(int id, int loaiHH)  throws SQLException {
        suaKhoaNgoai0();
        String sql ="UPDATE hanghoa SET loaihanghoa_id=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, loaiHH);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        suaKhoaNgoai1();
        return row > 0;
    }
    
    public boolean suaNhaCC(int id, int idNCC)  throws SQLException {
        suaKhoaNgoai0();
        String sql ="UPDATE nhacungcap_hanghoa SET nhacungcap_id=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idNCC);
        stm.setInt(2, id);
        int row = stm.executeUpdate();
        suaKhoaNgoai1();
        return row > 0;
    }
    
    /*public boolean suaHangHoa(HangHoa hh)  throws SQLException {
        String sql ="";
        int row = 0;
        if (hh.getTenhanghoa() != null) {
            sql = "UPDATE hanghoa SET tenhanghoa=? WHERE hanghoa_id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, hh.getTenhanghoa());
            stm.setInt(2, hh.getHanghoa_id());
            row = stm.executeUpdate();
        }
        if (hh.getThuonghieu() != null) {
            sql = "UPDATE hanghoa SET thuonghieu=? WHERE hanghoa_id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, hh.getThuonghieu());
            stm.setInt(2, hh.getHanghoa_id());
            row = stm.executeUpdate();
        }
        if (hh.getSoluongtrongkho() != null) {
            sql = "UPDATE hanghoa SET soluongtrongkho=? WHERE hanghoa_id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setBigDecimal(1, hh.getSoluongtrongkho());
            stm.setInt(2, hh.getHanghoa_id());
            row = stm.executeUpdate();
        }
        if (hh.getGianhap() != null) {
            sql = "UPDATE hanghoa SET gianhap=? WHERE hanghoa_id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setBigDecimal(1, hh.getGianhap());
            stm.setInt(2, hh.getHanghoa_id());
            row = stm.executeUpdate();
        }
        if (hh.getNgaysanxuat() != null) {
            sql = "UPDATE hanghoa SET ngaysanxuat=? WHERE hanghoa_id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, hh.getNgaysanxuat());
            stm.setInt(2, hh.getHanghoa_id());
            row = stm.executeUpdate();
        }
        if (hh.getNgayhethan() != null) {
            sql = "UPDATE hanghoa SET ngayhethan=? WHERE hanghoa_id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, hh.getNgayhethan());
            stm.setInt(2, hh.getHanghoa_id());
            row = stm.executeUpdate();
        }
        if (hh.getLoaihanghoa_id() != null) {
            sql = "UPDATE hanghoa SET loaihanghoa_id=? WHERE hanghoa_id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, hh.getLoaihanghoa_id());
            stm.setInt(2, hh.getHanghoa_id());
            row = stm.executeUpdate();
            suaLoaiHangHoa(hh.getHanghoa_id(), hh.getLoaihanghoa_id());
        }
        
        return row > 0;
    }*/
    
    public void suaKhoaNgoai0()  throws SQLException {
        Statement stm = this.conn.createStatement();
        stm.executeQuery("SET FOREIGN_KEY_CHECKS=0");
    }
    
    public void suaKhoaNgoai1()  throws SQLException {
        Statement stm = this.conn.createStatement();
        stm.executeQuery("SET FOREIGN_KEY_CHECKS=1");
    }
    
    /*public void suaLoaiHangHoa(int id, String idLoai) throws SQLException {
        String sql = "UPDATE hanghoa SET loaihanghoa_id=? WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, idLoai);
        stm.setInt(2, id);
        
        stm.executeUpdate();
    }*/
    
    public boolean deleteHH(int id) throws SQLException {
        suaKhoaNgoai0();
        String sql = "DELETE FROM hanghoa WHERE hanghoa_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        int row = stm.executeUpdate();
        suaKhoaNgoai1();
        return row > 0;
    }
    public HashMap<HangHoa, Integer> getTopSPBanChay(int top) throws SQLException{

        HashMap<HangHoa, Integer> hh = new HashMap<>();
        String sql = "SELECT hanghoa.*, sum(soluong) as soluong\n" 
                +"FROM qldvvpkcm.hanghoa, qldvvpkcm.chitietdonhang\n" 
                +"WHERE hanghoa.hanghoa_id = chitietdonhang.hanghoa_id\n" 
                +"GROUP BY hanghoa.hanghoa_id\n" 
                +"order by soluong desc\n" 
                +"LIMIT ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, top);
        ResultSet rs = stm.executeQuery();
        int sl = 0;
        while (rs.next()) {
            HangHoa h = new HangHoa();
            h.setHanghoa_id(rs.getInt("hanghoa.hanghoa_id"));
            h.setTenhanghoa(rs.getString("tenhanghoa"));
            h.setThuonghieu(rs.getString("thuonghieu"));
            h.setSoluongtrongkho(rs.getString("soluongtrongkho"));
            h.setGianiemyet(rs.getString("gianiemyet"));
            h.setNgaysanxuat(rs.getString("ngaysanxuat"));
            h.setNgayhethan(rs.getString("ngayhethan"));
            h.setHinhanh(rs.getString("hinhanh"));
            h.setTinhtrang(rs.getBoolean("tinhtrang"));
            h.setLoaihanghoa_id(rs.getInt("loaihanghoa_id"));
            sl = rs.getInt("soluong");
            
            hh.put(h,sl);
        }
        return hh;
    }
    public HashMap<HangHoa, Integer> getTopSPBanIt(int top) throws SQLException{

        HashMap<HangHoa, Integer> hh = new HashMap<>();
        String sql = "SELECT hanghoa.*, sum(soluong) as soluong\n" 
                +"FROM qldvvpkcm.hanghoa, qldvvpkcm.chitietdonhang\n" 
                +"WHERE hanghoa.hanghoa_id = chitietdonhang.hanghoa_id\n" 
                +"GROUP BY hanghoa.hanghoa_id\n" 
                +"order by soluong asc\n" 
                +"LIMIT ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, top);
        ResultSet rs = stm.executeQuery();
        int sl = 0;
        while (rs.next()) {
            HangHoa h = new HangHoa();
            h.setHanghoa_id(rs.getInt("hanghoa.hanghoa_id"));
            h.setTenhanghoa(rs.getString("tenhanghoa"));
            h.setThuonghieu(rs.getString("thuonghieu"));
            h.setSoluongtrongkho(rs.getString("soluongtrongkho"));
            h.setGianiemyet(rs.getString("gianiemyet"));
            h.setNgaysanxuat(rs.getString("ngaysanxuat"));
            h.setNgayhethan(rs.getString("ngayhethan"));
            h.setHinhanh(rs.getString("hinhanh"));
            h.setTinhtrang(rs.getBoolean("tinhtrang"));
            h.setLoaihanghoa_id(rs.getInt("loaihanghoa_id"));
            sl = rs.getInt("soluong");
            
            hh.put(h,sl);
        }
        return hh;
       }

}
