/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TrangChuController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    static User nd;
    @FXML private AnchorPane acpLoad;
    @FXML private ScrollPane spLoad;
    @FXML private HBox hbLoad;
    @FXML
    public void loadTraCuuHHQLT() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("tracuuhanghoaquanlytruong.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    @FXML
    public void loadTraCuuHHTK() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("tracuuhanghoathukho.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    @FXML
    public void loadQuanLyNCC() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("quanlynhacungcap.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    @FXML
    public void loadTaoHoaDon() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("taohoadon.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    @FXML
    public void loadDoiTra() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("doitra.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    @FXML
    public void loadQuanLyKhachHang() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("quanlykhachhang.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    @FXML
    public void loadQuanLyThuCung() throws IOException {
        acpLoad.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("quanlythucung.fxml"));
        acpLoad.getChildren().add(loader.load());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
    public static void setTTUser(User u){
        nd = u;
    }
    
    /*public void traCuuHangHoaThuKhoHandler(MouseEvent evt) throws IOException{
        try {
            Parent tchh;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("tracuuhanghoathukho.fxml"));
            tchh = loader.load();
            Scene scene = new Scene(tchh);
            
            TraCuuHangHoaThuKhoController controller = loader.getController();
            controller.setTTUser(nd);
            //stage.setResizable(false);
            //stage.initStyle(StageStyle.UTILITY);
            //stage.setFullScreen(true);
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TrangChuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void traCuuHangHoaQuanLyTruongHandler(MouseEvent evt) throws IOException{
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
            Logger.getLogger(TrangChuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    
    
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
            Logger.getLogger(TrangChuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
