/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.service.JdbcUtils;
import com.doannganh.service.KhachHangService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class KhachhangController implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis xBar;

    @FXML
    private NumberAxis yBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadBarChart();
        } catch (SQLException ex) {
            Logger.getLogger(KhachhangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    public void loadBarChart() throws SQLException{
        this.barChart.getData().clear();
        XYChart.Series setData = new XYChart.Series<>();
        setData.getData().add(new XYChart.Data<String, Number>("Khách hàng thường", slKHT() ));
        setData.getData().add(new XYChart.Data<String, Number>("Khách hàng thân thiết", slKHTT()));
        this.barChart.getData().add(setData);
        xBar.setLabel("Loại khách hàng");
        yBar.setLabel("Số lượng đơn hàng đã đặt");
    }
    
    public int slKHT() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        KhachHangService khs = new KhachHangService(conn);
        
        return khs.slKHT();
    }

    public int slKHTT() throws SQLException{
        Connection conn = JdbcUtils.getConn();
        KhachHangService khs = new KhachHangService(conn);
        
        return khs.slKHTT();
    }
}
