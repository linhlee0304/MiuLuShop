/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.CapNhatHoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class CapNhatHoaDonService {
    private Connection conn;
    
    public CapNhatHoaDonService(Connection conn) {
        this.conn = conn;
    }
    
    public boolean themCNHD(CapNhatHoaDon cnhd) throws SQLException {
        String sql = "INSERT INTO capnhathoadon(donhang_id, nhanvien_id, ngaygiocapnhat, ghichu)"
                    + " VALUES(?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, cnhd.getDonhang_id());
        stm.setInt(2, cnhd.getNhanvien_id());
        stm.setString(3, cnhd.getNgayCapNhat());
        stm.setString(4, cnhd.getGhichu());
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
}
