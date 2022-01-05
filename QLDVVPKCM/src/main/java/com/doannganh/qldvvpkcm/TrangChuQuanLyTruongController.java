
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.User;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TrangChuQuanLyTruongController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    static User nd;
    @FXML 
    private AnchorPane acpLoad;
    
    @FXML 
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis ngay;

    @FXML
    private NumberAxis doanhThu;
    
    @FXML
    private Tab ngayhn;
    
    @FXML
    private Text dtNgay;
    
    @FXML
    private Text dtThang;
    
    @FXML
    private Text dtNam;
    
    @FXML
    private Tab thang;

    @FXML
    private Tab nam;
    
    @FXML
    private ListView<String> listDH;
    
    @FXML
    public void loadHangHoa() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("hanghoa.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    @FXML
    public void loadKhachHang() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("khachhang.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    public void loadBarchart(){
        try {
            XYChart.Series setData = new XYChart.Series<>();
            Connection conn = JdbcUtils.getConn();
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            DonHangService dhs = new DonHangService(conn);
            ArrayList<String> ngay = bayNgayGanNhat(); 
            for(int i = 6; i >= 0; i--){
                int tong = 0;
                List<Integer> idDH = dhs.getDHIDByDate(ngay.get(i));
                for(int j = 0; j < idDH.size(); j++){
                    tong += ctdhs.tongDHByID(idDH.get(j));
                }
                setData.getData().add(new XYChart.Data(ngay.get(i),tong));
            }
            this.barChart.getData().addAll(setData);
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void loadTabNgay(){
        try {
            Connection conn = JdbcUtils.getConn();
            DonHangService dhs = new DonHangService(conn);
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            LocalDate today = LocalDate.now();
            List<Integer> idDH = dhs.getDHIDByDate(today.toString());
            int tong = 0;
            for(int j = 0; j < idDH.size(); j++){
                    tong += ctdhs.tongDHByID(idDH.get(j));
                }
            this.dtNgay.setText(moneyFormat(tong));
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void loadTabThang(){
        try {
            Connection conn = JdbcUtils.getConn();
            DonHangService dhs = new DonHangService(conn);
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            LocalDate today = LocalDate.now();
            List<Integer> idDH = dhs.getDHIDByMonth(today.toString());
            int tong = 0;
            for(int j = 0; j < idDH.size(); j++){
                    tong += ctdhs.tongDHByID(idDH.get(j));
                }
            this.dtThang.setText(moneyFormat(tong));
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void loadTabNam(){
        try {
            Connection conn = JdbcUtils.getConn();
            DonHangService dhs = new DonHangService(conn);
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            LocalDate today = LocalDate.now();
            List<Integer> idDH = dhs.getDHIDByYear(today.toString());
            int tong = 0;
            for(int j = 0; j < idDH.size(); j++){
                    tong += ctdhs.tongDHByID(idDH.get(j));
                }
            this.dtNam.setText(moneyFormat(tong));
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadBarchart();
        loadListView();
    }
    
    public static void setTTUser(User u){
        nd = u;
    }
    
    @FXML
    public void loadTrangChu() throws IOException {
        App.setRoot("trangchuquanlytruong");
    }
    
    public void loadListView(){
        try {
            Connection conn = JdbcUtils.getConn();
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            DonHangService dhs = new DonHangService(conn);
            LocalDate today = LocalDate.now();
            List<Integer> idDH = dhs.getDHIDByDate(today.toString());
            for(int j = 0; j < idDH.size(); j++){
                    int tong = ctdhs.tongDHByID(idDH.get(j));
                    listDH.getItems().add("Mã đơn hàng: #" + idDH.get(j) + "\t\t\tTổng: " + moneyFormat(tong));
                }

        } catch (SQLException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void loadThongKe(ActionEvent event) throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("thongke.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    public void traCuuHangHoaQuanLyTruongHandler(ActionEvent evt) throws IOException{
        try {
            Parent tchh;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("tracuuhanghoaquanlytruong.fxml"));
            tchh = loader.load();
            Scene scene = new Scene(tchh);
            TraCuuHangHoaQuanLyTruongController controller = loader.getController();
            controller.setTTUser(nd);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String moneyFormat(int money){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return(formatter.format(money)+" VNĐ");
    }
    
    public ArrayList<String> bayNgayGanNhat(){
        ArrayList<String> ngay = new ArrayList<>();
        LocalDate today = LocalDate.now();
        ngay.add(today.toString());
        for(int i = 1; i < 7; i++){
            ngay.add(today.minusDays(i).toString());
        }
        return ngay;
    }
    
    public void logoutHandler(ActionEvent evt) throws IOException{
        try {
            App.setRoot("dangnhap");
//            Parent dx;
//            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("dangnhap.fxml"));
//            dx = loader.load();
//            Scene scene = new Scene(dx);
//            stage.setScene(scene);
//            //stage.setMaximized(true);
//            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
