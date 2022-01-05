/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.LoaiHangHoa;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.LoaiHangHoaService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class HanghoaController implements Initializable {

    @FXML
    private AnchorPane paneHH;

    @FXML
    private Button btThongKe;

    @FXML
    private TextField soLuong;

    @FXML
    private CheckBox spBanChay;

    @FXML
    private CheckBox loaiSPBanChay;

    @FXML
    private CheckBox loaiSPBanIt;

    @FXML
    private CheckBox spBanIt;

    @FXML
    private Button btCapNhat;

    @FXML
    private BarChart<?, ?> barChart;
    
    @FXML
    private CategoryAxis xBar;

    @FXML
    private NumberAxis yBar;
    
    @FXML
    public void loadTraCuuHHQLT() throws IOException {
        paneHH.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("tracuuhanghoaquanlytruong.fxml"));
        paneHH.getChildren().add(loader.load());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
//    public HashMap<String,Integer> loadDataMatHang(int top){
//        
//    }
    
    public void loadBarChartHH(HashMap<HangHoa, Integer> data){
        barChart.getData().clear();
        XYChart.Series setData = changeHashToSeriesHH(data);
        barChart.getData().add(setData);
        xBar.setLabel("ID hàng hóa");
        yBar.setLabel("Số lượng");
    }
    
    public void loadBarChartLHH(HashMap<LoaiHangHoa, Integer> data){
        barChart.getData().clear();
        XYChart.Series setData = changeHashToSeriesLHH(data);
        barChart.getData().add(setData);
        xBar.setLabel("Tên loại hàng hóa");
        yBar.setLabel("Số lượng");
    }
    
    public HashMap<HangHoa, Integer> loadSPBanChay(int sl) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        HangHoaService hhs = new HangHoaService(conn);
        
        HashMap<HangHoa,Integer> spBC = hhs.getTopSPBanChay(sl);
         
        return spBC;
    }
    
    public HashMap<HangHoa, Integer> loadSPBanIt(int sl) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        HangHoaService hhs = new HangHoaService(conn);
        
        HashMap<HangHoa,Integer> spBC = hhs.getTopSPBanIt(sl);
         
        return spBC;
    }
    
    public HashMap<LoaiHangHoa, Integer> loadLoaiSPBanIt(int sl) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        LoaiHangHoaService hhs = new LoaiHangHoaService(conn);
        
        HashMap<LoaiHangHoa,Integer> spBC = hhs.getTopLoaiSPBanIt(sl);
         
        return spBC;
    }

    public HashMap<LoaiHangHoa, Integer> loadLoaiSPBanChay(int sl) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        LoaiHangHoaService hhs = new LoaiHangHoaService(conn);
        
        HashMap<LoaiHangHoa,Integer> spBC = hhs.getTopLoaiSPBanChay(sl);
         
        return spBC;
    }

    
    public XYChart.Series changeHashToSeriesHH(HashMap<HangHoa,Integer> data){
    XYChart.Series setData = new XYChart.Series<>();
    for (Map.Entry<HangHoa,Integer> dt:data.entrySet()){
        setData.getData().add(new XYChart.Data(String.valueOf(dt.getKey().getHanghoa_id()), dt.getValue()));
    } 
    return setData;
    }
      
    public XYChart.Series changeHashToSeriesLHH(HashMap<LoaiHangHoa,Integer> data){
    XYChart.Series setData = new XYChart.Series<>();
    for (Map.Entry<LoaiHangHoa,Integer> dt:data.entrySet()){
        setData.getData().add(new XYChart.Data(String.valueOf(dt.getKey().getTenloai()), dt.getValue()));
    } 
    return setData;
    }

    
    @FXML
    void checkLoaiSPBanChay(ActionEvent event) {
        spBanIt.setSelected(false);
        spBanChay.setSelected(false);
        loaiSPBanIt.setSelected(false);
    }

    @FXML
    void checkLoaiSPBanIt(ActionEvent event) {
        loaiSPBanChay.setSelected(false);
        spBanChay.setSelected(false);
        spBanIt.setSelected(false);
    }

    @FXML
    void checkSPBanChay(ActionEvent event) {
        loaiSPBanChay.setSelected(false);
        loaiSPBanIt.setSelected(false);
        spBanIt.setSelected(false);
    }

    @FXML
    void checkSPBanIt(ActionEvent event) {
        loaiSPBanChay.setSelected(false);
        spBanChay.setSelected(false);
        loaiSPBanIt.setSelected(false);
    }

    @FXML
    void loadThongKe(ActionEvent event) throws SQLException {
        if(this.soLuong.getText().equals("")){
            Utils.getBox("Vui lòng nhập một số!", Alert.AlertType.WARNING).show();
        }
        if(!(this.soLuong.getText().matches("\\d+"))){
            Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
            this.soLuong.setText("");
        } 
        if(this.loaiSPBanChay.isSelected() == false && loaiSPBanIt.isSelected() == false &&
            spBanChay.isSelected() == false && spBanIt.isSelected() == false){
            Utils.getBox("Vui lòng chọn một loại thống kê!", Alert.AlertType.WARNING).show();
        }
        else{
            if(this.spBanChay.isSelected()){
                loadBarChartHH(loadSPBanChay(Integer.parseInt(soLuong.getText())));
            }
            if(this.spBanIt.isSelected()){
                loadBarChartHH(loadSPBanIt(Integer.parseInt(soLuong.getText())));
            }
            if(this.loaiSPBanIt.isSelected()){
                loadBarChartLHH(loadLoaiSPBanIt(Integer.parseInt(soLuong.getText())));
            }
            if(this.loaiSPBanChay.isSelected()){
                loadBarChartLHH(loadLoaiSPBanChay(Integer.parseInt(soLuong.getText())));
            }
        }
    }

    
}
