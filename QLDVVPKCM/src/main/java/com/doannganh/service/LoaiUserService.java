/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.service;

import com.doannganh.pojo.LoaiUser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class LoaiUserService {
    private Connection conn;
    
    public LoaiUserService(Connection conn) {
        this.conn = conn;
    }
    
    public List<LoaiUser> getLoaiTK() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM loaiuser");
        
        List<LoaiUser> rs = new ArrayList<>();
        while (r.next()) {
            LoaiUser lu = new LoaiUser();
            lu.setLoaiuser_id(r.getInt("loaiuser_id"));
            lu.setTenloai(r.getString("tenloai"));
            
            rs.add(lu);
        }
        return rs;
    }
}
