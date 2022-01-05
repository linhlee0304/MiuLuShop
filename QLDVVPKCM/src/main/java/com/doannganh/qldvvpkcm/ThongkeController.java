/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.NhaCungCap_HangHoa;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.NhaCungCapService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;


public class ThongkeController implements Initializable {

    @FXML
    private DatePicker ngayBD;

    @FXML
    private DatePicker ngayKT;

    @FXML
    private ComboBox<String> loaiTK;

    @FXML
    private Button btPieChart;

    @FXML
    private Button btLineChart;

    @FXML
    private Button btBarChart;

    @FXML
    private Button btAreaChart;
    
    @FXML
    private CheckBox cbDoanhThu;

    @FXML
    private CheckBox cbLoiNhuan;

    @FXML
    private CheckBox cbChiPhi;
    
    @FXML
    private AnchorPane chartPane;
    
    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xBar;

    @FXML
    private NumberAxis yBar;

    @FXML
    private LineChart<?, ?> lineChart;
    
    @FXML
    private NumberAxis yLine;
    
    @FXML
    private AreaChart<?, ?> areaChart;
    
    @FXML
    private NumberAxis yArea;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCBData();
        this.ngayKT.setValue(LocalDate.now());
        this.ngayBD.setValue(LocalDate.of(2021, 01, 11));
        this.cbDoanhThu.setSelected(true);    
    }    
     
    @FXML
    void loadPieChart(ActionEvent event) throws SQLException{
//        pieChart.setData(loadPieChartData());
        pieChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
        
        HashMap<String,Integer> data = new HashMap<>();

        if(this.cbLoiNhuan.isSelected()){
            data = tinhLoiNhuan(ngayBD, ngayKT, loaiTK);
        } 
        if(this.cbDoanhThu.isSelected()){
            data = tinhDoanhThu(ngayBD, ngayKT, loaiTK);
        }
        if(this.cbChiPhi.isSelected()){
            data = tinhChiPhi(ngayBD, ngayKT, loaiTK);
        }
        for (Map.Entry<String,Integer> entry:data.entrySet()){
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.getData().forEach(dt-> {
            String percent = moneyFormat((int)dt.getPieValue());
            Tooltip tool = new Tooltip(percent);
            Tooltip.install(dt.getNode(), tool);
        });
        setVisible(1);
    }
    
    @FXML
    void loadLineChart(ActionEvent event) throws SQLException {
        lineChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
        
        HashMap<String,Integer> data = new HashMap<>();

        if(this.cbDoanhThu.isSelected() ){
            data = tinhDoanhThu(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Doanh thu(VNĐ)");
        }
        if(this.cbLoiNhuan.isSelected()){
            data = tinhLoiNhuan(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Lợi nhuận(VNĐ)");
        }
        if(this.cbChiPhi.isSelected()){
            data = tinhChiPhi(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Chi phí(VNĐ)");
        }
        this.lineChart.getData().addAll(changeHashToSeries(data));
        setVisible(2);
    }
    
    @FXML
    void loadBarChart(ActionEvent event) throws SQLException {
        barChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
        
        HashMap<String,Integer> data = new HashMap<>();
   
        if(this.cbDoanhThu.isSelected() ){
            data = tinhDoanhThu(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Doanh thu(VNĐ)");
        }
        if(this.cbLoiNhuan.isSelected()){
            data = tinhLoiNhuan(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Lợi nhuận(VNĐ)");
        }
        if(this.cbChiPhi.isSelected()){
            data = tinhChiPhi(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Chi phí(VNĐ)");
        }
        
        this.barChart.getData().addAll(changeHashToSeries(data));

        setVisible(3);
    }
    
    @FXML
    void loadAreaChart(ActionEvent event) throws SQLException {
        areaChart.getData().clear();
        LocalDate ngayBD = this.ngayBD.getValue();
        LocalDate ngayKT = this.ngayKT.getValue();
        String loaiTK = this.loaiTK.getValue().toString();
        
        HashMap<String,Integer> data = new HashMap<>();
   
        if(this.cbDoanhThu.isSelected() ){
            data = tinhDoanhThu(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Doanh thu(VNĐ)");
        }
        if(this.cbLoiNhuan.isSelected()){
            data = tinhLoiNhuan(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Lợi nhuận(VNĐ)");
        }
        if(this.cbChiPhi.isSelected()){
            data = tinhChiPhi(ngayBD, ngayKT, loaiTK);
            yArea.setLabel("Chi phí(VNĐ)");
        }
        
        this.areaChart.getData().addAll(changeHashToSeries(data));
        setVisible(4);
    }
    
    public HashMap<String,Integer> tinhDoanhThu(LocalDate ngayBD, LocalDate ngayKT, String loaiTK) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
        
        HashMap<String,Integer> doanhThu = new HashMap<>();
        if(loaiTK.equals("Ngày")){
            int i = 0;
            while((ngayBD.plusDays(i)).isAfter(ngayKT)== false){
                int dt = ctdhs.getDoanhThuByDate((ngayBD.plusDays(i)).format(dateFormatter));

                if(dt != 0){
                    doanhThu.put((ngayBD.plusDays(i)).format(dateFormatter), dt);
                }
                i++;
            }
        }
        else if(loaiTK.equals("Tháng")){
            int i = 0;
            while(((ngayBD.plusMonths(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusMonths(i).getMonthValue() == ngayKT.getMonthValue()){
                
                int dt = ctdhs.getDoanhThuByMonth(ngayBD.plusMonths(i).format(dateFormatter));
                
                if(dt != 0){
                    doanhThu.put((ngayBD.plusMonths(i)).getMonth().toString()
                            + "-".concat(Integer.toString(ngayBD.plusMonths(i).getYear())), dt);
                }
                i++;
            }
        }
        else{
            int i = 0;
            while(((ngayBD.plusYears(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusYears(i).getYear() == ngayKT.getYear()){
                int dt = ctdhs.getDoanhThuByYear((ngayBD.plusYears(i)).format(dateFormatter));

                if(dt != 0){
                    doanhThu.put(Integer.toString(ngayBD.plusYears(i).getYear()), dt);
                }
                i++;
            }
        }
        conn.close();
        return doanhThu;
    }
    
    public HashMap<String,Integer> tinhChiPhi(LocalDate ngayBD, LocalDate ngayKT, String loaiTK) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        NhaCungCapService nccs = new NhaCungCapService(conn);
        
        HashMap<String,Integer> chiPhi = new HashMap<>();
        if(loaiTK.equals("Ngày")){
            int i = 0;
            while((ngayBD.plusDays(i)).isAfter(ngayKT)== false){
                int cp = nccs.tinhChiPhiByDate((ngayBD.plusDays(i)).format(dateFormatter));

                if(cp != 0){
                    chiPhi.put((ngayBD.plusDays(i)).format(dateFormatter), cp );
                }
                i++;
            }
        }
        else if(loaiTK.equals("Tháng")){
            int i = 0;
            while(((ngayBD.plusMonths(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusMonths(i).getMonthValue() == ngayKT.getMonthValue()){
                int cp = nccs.tinhChiPhiByMonth((ngayBD.plusMonths(i)).format(dateFormatter));
                
                if(cp != 0){
                    chiPhi.put((ngayBD.plusMonths(i)).getMonth().toString()
                            + "-".concat(Integer.toString(ngayBD.plusMonths(i).getYear())), cp);
                }
                i++;
            }
        }
        else{
            int i = 0;
            while(((ngayBD.plusYears(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusYears(i).getYear() == ngayKT.getYear()){
                int cpN = nccs.tinhChiPhiByYear((ngayBD.plusYears(i)).format(dateFormatter));

                if(cpN != 0){
                    chiPhi.put(Integer.toString(ngayBD.plusYears(i).getYear()), cpN);
                }
                i++;
            } 
        }
        conn.close();
        return chiPhi;
    }
    
    public HashMap<String,Integer> tinhLoiNhuan(LocalDate ngayBD, LocalDate ngayKT, String loaiTK) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        DonHangService dhs = new DonHangService(conn);
        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
        NhaCungCapService nccs = new NhaCungCapService(conn);
        
        HashMap<String,Integer> loiNhuan = new HashMap<>();

        if(loaiTK.equals("Ngày")){
            int i = 0;
            while((ngayBD.plusDays(i)).isAfter(ngayKT)== false){
        
                int chiPhiHN = nccs.tinhChiPhiUntilDate((ngayBD.plusDays(i)).format(dateFormatter));
                int doanhThuHN = ctdhs.getDoanhThuUntilDate((ngayBD.plusDays(i)).format(dateFormatter));
                int lnHN = doanhThuHN - chiPhiHN;
                
                loiNhuan.put(ngayBD.plusDays(i).format(dateFormatter), lnHN);

                i++;
            }

        }
        else if(loaiTK.equals("Tháng")){
            int i = 0;
            while(((ngayBD.plusMonths(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusMonths(i).getMonthValue() == ngayKT.getMonthValue()){

                int chiPhiTN = nccs.tinhChiPhiUntilDate((ngayBD.plusMonths(i)).format(dateFormatter));
                int doanhThuTN = ctdhs.getDoanhThuUntilDate((ngayBD.plusMonths(i)).format(dateFormatter));
                int lnTN = doanhThuTN - chiPhiTN;
                
                loiNhuan.put((ngayBD.plusMonths(i)).getMonth().toString()
                        + "-".concat(Integer.toString(ngayBD.plusMonths(i).getYear())), lnTN );
                i++;
            }
            
        }
        else{
            int i = 0;
            while(((ngayBD.plusYears(i)).isBefore(ngayKT)) == true
                  || ngayBD.plusYears(i).getYear() == ngayKT.getYear()){

                int chiPhiN = nccs.tinhChiPhiUntilYear((ngayBD.plusYears(i)).format(dateFormatter));
                int doanhThuN = ctdhs.getDoanhThuUntilYear((ngayBD.plusYears(i)).format(dateFormatter));
                int lnN = doanhThuN - chiPhiN;
                
                loiNhuan.put(Integer.toString(ngayBD.plusYears(i).getYear()), lnN );

                i++;
            }
        }
        
        conn.close();
        return loiNhuan;
    }
    
    
    public XYChart.Series changeHashToSeries(HashMap<String,Integer> data){
        
        TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(data);
        Set<Map.Entry<String, Integer>> sort = sorted.entrySet();
        XYChart.Series setData = new XYChart.Series<>();
        for (Map.Entry<String,Integer> dt:sort){
            setData.getData().add(new XYChart.Data(dt.getKey(), dt.getValue()));
        } 
        return setData;
    }
    
    public void loadCBData(){
        ObservableList<String> list = FXCollections.observableArrayList("Ngày", "Tháng", "Năm");
        this.loaiTK.setItems(list);
        this.loaiTK.getSelectionModel().selectFirst();
    }

     @FXML
    void selectedNgayBD(ActionEvent event) {
        if((this.ngayBD.getValue()).isAfter(LocalDate.now())){
            Utils.getBox("Vui lòng chọn ngày trong quá khứ!!!", Alert.AlertType.INFORMATION).show();
            this.ngayBD.setValue(LocalDate.of(2021, 01, 11));
        }
    }

    @FXML
    void selectedNgayKT(ActionEvent event) {
        if((this.ngayKT.getValue()).isBefore(this.ngayBD.getValue())){
            Utils.getBox("Vui lòng chọn ngày sau ngày bắt đầu!!!", Alert.AlertType.INFORMATION).show();
            this.ngayKT.setValue(LocalDate.now());
        }
    }
    
    public void setVisible(int i){
        switch (i) {
            case 1:
                this.pieChart.setVisible(true);
                this.lineChart.setVisible(false);
                this.barChart.setVisible(false);
                this.areaChart.setVisible(false);
                break;
            case 2:
                this.pieChart.setVisible(false);
                this.lineChart.setVisible(true);
                this.barChart.setVisible(false);
                this.areaChart.setVisible(false);
                break;
            case 3:
                this.pieChart.setVisible(false);
                this.lineChart.setVisible(false);
                this.barChart.setVisible(true);
                this.areaChart.setVisible(false);
                break;
            case 4:
                this.pieChart.setVisible(false);
                this.lineChart.setVisible(false);
                this.barChart.setVisible(false);
                this.areaChart.setVisible(true);
                break;
        }
    }
    
    @FXML
    void checkChiPhi(ActionEvent event) {
        cbDoanhThu.setSelected(false);
        cbLoiNhuan.setSelected(false);
    }
    @FXML
    void checkDoanhThu(ActionEvent event) {
        cbChiPhi.setSelected(false);
        cbLoiNhuan.setSelected(false);
    }
    @FXML
    void checkLoiNhuan(ActionEvent event) {
        cbDoanhThu.setSelected(false);
        cbChiPhi.setSelected(false);
    }
    
    public String moneyFormat(int money){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return(formatter.format(money)+" VNĐ");
    }
    
}
