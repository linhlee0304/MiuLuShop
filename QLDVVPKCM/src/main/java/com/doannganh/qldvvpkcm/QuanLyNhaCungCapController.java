/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.User;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.NhaCungCapService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.LoaiHangHoaService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class QuanLyNhaCungCapController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField txtTraCuu;
    @FXML private TableView<NhaCungCap> tbNCC;
    @FXML private ComboBox<String> cbTraCuu;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã nhà cung cấp", "Tên công ty");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectLast();
        loadTable();
        loadNCC("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            loadNCC(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
        
        /*this.tbNCC.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(evt -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    NhaCungCapService s = new NhaCungCapService(conn);
                    NhaCungCap ncc = this.tbNCC.getSelectionModel().getSelectedItem();
                    int rindex = this.tbNCC.getSelectionModel().getSelectedIndex();
                    TextInputDialog dialog = new TextInputDialog(ncc.getGianiemyet());
                    dialog.setTitle("Cập nhật");
                    dialog.setHeaderText("Hãy nhập giá niêm yết:");
                    dialog.setContentText("Giá niêm yết:");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        TextField tf = dialog.getEditor();
                        if (tf.getText().isEmpty())
                            Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                        else if (!tf.getText().matches("\\d+"))
                            Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
                        else if (result.get().equals(ncc.getGianiemyet()))
                            Utils.getBox("Vui lòng thay đổi giá niêm yết để cập nhật!", Alert.AlertType.WARNING).show();
                        else {
                            if (tf.getText().length() > 9)
                                Utils.getBox("Vui lòng nhập giá niêm yết < 1.000.000.000", Alert.AlertType.WARNING).show();
                            else if (Integer.parseInt(tf.getText()) < 10000)
                                    Utils.getBox("Vui lòng nhập giá niêm yết >= 10.000", Alert.AlertType.WARNING).show();
                            else if (Integer.parseInt(tf.getText()) <= Integer.parseInt(ncc.getGianhap())) {
                                Utils.getBox("Vui lòng nhập giá niêm yết > giá nhập: " + ncc.getGianhap(), Alert.AlertType.WARNING).show();
                            }
                                else {
                                    ncc.setGianiemyet(result.get());
                                    if (s.suaGiaNiemYet(ncc.getHanghoa_id(), ncc.getGianiemyet())) {
                                        Utils.getBox("Cập nhật giá bán thành công!", Alert.AlertType.INFORMATION).show();
                                        this.tbNCC.getItems().set(rindex, ncc);
                                    } else
                                        Utils.getBox("Cập nhật giá bán thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                        } 
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return r;
        });*/
    }
    
    public void loadNCC(String tuKhoa, String traCuu){
        try {
            this.tbNCC.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            NhaCungCapService s = new NhaCungCapService(conn);
            this.tbNCC.setItems(FXCollections.observableList(
                    s.getNCC(tuKhoa, traCuu)));
            NhaCungCap ncc = new NhaCungCap();
            ncc.setNhacungcap_id(this.tbNCC.getItems().size() + 1);
            ncc.setTencongty("NULL");
            ncc.setDiachi("NULL");
            ncc.setTinhthanh("NULL");
            ncc.setQuocgia("NULL");
            ncc.setEmail("NULL");
            ncc.setSodt("NULL");
            ncc.setTongmathang(0);
            this.tbNCC.getItems().add(ncc);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTable(){
        try {
            this.tbNCC.setEditable(true);
            Connection conn;
            conn = JdbcUtils.getConn();
            NhaCungCapService nccs = new NhaCungCapService(conn);
            TableColumn<NhaCungCap, Integer> colMaNCC = new TableColumn("Mã");
            colMaNCC.setCellValueFactory(new PropertyValueFactory("nhacungcap_id"));
            
            TableColumn<NhaCungCap, String> colTenCongTy = new TableColumn("Tên Công Ty");
            colTenCongTy.setCellValueFactory(new PropertyValueFactory("tencongty"));
            
            TableColumn<NhaCungCap, String> colDiaChi = new TableColumn("Địa Chỉ");
            colDiaChi.setCellValueFactory(new PropertyValueFactory("diachi"));
            
            TableColumn<NhaCungCap, String> colTinhThanh = new TableColumn("Tỉnh Thành");
            colTinhThanh.setCellValueFactory(new PropertyValueFactory("tinhthanh"));
            
            TableColumn<NhaCungCap, String> colQuocGia = new TableColumn("Quốc Gia");
            colQuocGia.setCellValueFactory(new PropertyValueFactory("quocgia"));
            
            TableColumn<NhaCungCap, String> colEmail = new TableColumn("Email");
            colEmail.setCellValueFactory(new PropertyValueFactory("email"));
            
            TableColumn<NhaCungCap, String> colSoDT = new TableColumn("Số điện thoại");
            colSoDT.setCellValueFactory(new PropertyValueFactory("sodt"));
            
            TableColumn<NhaCungCap, Integer> colTongMatHang = new TableColumn("Tổng mặt hàng");
            colTongMatHang.setCellValueFactory(new PropertyValueFactory("tongmathang"));
            
            colMaNCC.setOnEditStart(evt -> {
                Utils.getBox("Không thể sửa mã nhà cung cấp!!!", Alert.AlertType.ERROR).show();
            });
            
            colTenCongTy.setCellFactory(TextFieldTableCell.forTableColumn());
            colTenCongTy.setOnEditCommit((var evt) -> {
                try {
                    NhaCungCap ncc = evt.getRowValue();
                    if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size()) {
                        String c = ncc.getTencongty();
                        String m = "";
                        if (!"".equals(evt.getNewValue()))
                            m = evt.getNewValue();
                        ncc.setTencongty(m);
                        if ("".equals(m)) {
                            ncc.setTencongty(c);
                            Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            Utils.getBox("Vui lòng thay đổi tên công ty để cập nhật!!!", Alert.AlertType.WARNING).show();
                        } else {
                            if (nccs.suaTenCongTy(ncc.getNhacungcap_id(), ncc.getTencongty())) {
                                Utils.getBox("Cập nhật tên công ty thành công!", Alert.AlertType.INFORMATION).show();
                            } else {
                                ncc.setTencongty(c);
                                Utils.getBox("Cập nhật tên công ty thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        tbNCC.refresh();
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            colDiaChi.setCellFactory(TextFieldTableCell.forTableColumn());
            colDiaChi.setOnEditCommit((var evt) -> {
                try {
                    NhaCungCap ncc = evt.getRowValue();
                    if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size()) {
                        String c = ncc.getDiachi();
                        String m = "";
                        if (!"".equals(evt.getNewValue()))
                            m = evt.getNewValue();
                        ncc.setDiachi(m);
                        if ("".equals(m)) {
                            ncc.setDiachi(c);
                            Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            Utils.getBox("Vui lòng thay đổi địa chỉ để cập nhật!!!", Alert.AlertType.WARNING).show();
                        } else {
                            if (nccs.suaDiaChi(ncc.getNhacungcap_id(), ncc.getDiachi())) {
                                Utils.getBox("Cập nhật địa chỉ thành công!", Alert.AlertType.INFORMATION).show();
                            } else {
                                ncc.setDiachi(c);
                                Utils.getBox("Cập nhật địa chỉ thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        tbNCC.refresh();
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            colTinhThanh.setCellFactory(TextFieldTableCell.forTableColumn());
            colTinhThanh.setOnEditCommit((var evt) -> {
                try {
                    NhaCungCap ncc = evt.getRowValue();
                    if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size()) {
                        String c = ncc.getTinhthanh();
                        String m = "";
                        if (!"".equals(evt.getNewValue()))
                            m = evt.getNewValue();
                        ncc.setTinhthanh(m);
                        if ("".equals(m)) {
                            ncc.setTinhthanh(c);
                            Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            Utils.getBox("Vui lòng thay đổi tỉnh thành để cập nhật!!!", Alert.AlertType.WARNING).show();
                        } else {
                            if (nccs.suaTinhThanh(ncc.getNhacungcap_id(), ncc.getTinhthanh())) {
                                Utils.getBox("Cập nhật tỉnh thành thành công!", Alert.AlertType.INFORMATION).show();
                            } else {
                                ncc.setTinhthanh(c);
                                Utils.getBox("Cập nhật tỉnh thành thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        tbNCC.refresh();
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            colQuocGia.setCellFactory(TextFieldTableCell.forTableColumn());
            colQuocGia.setOnEditCommit((var evt) -> {
                try {
                    NhaCungCap ncc = evt.getRowValue();
                    if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size()) {
                        String c = ncc.getQuocgia();
                        String m = "";
                        if (!"".equals(evt.getNewValue()))
                            m = evt.getNewValue();
                        ncc.setQuocgia(m);
                        if ("".equals(m)) {
                            ncc.setQuocgia(c);
                            Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            Utils.getBox("Vui lòng thay đổi quốc gia để cập nhật!!!", Alert.AlertType.WARNING).show();
                        } else {
                            if (nccs.suaQuocGia(ncc.getNhacungcap_id(), ncc.getQuocgia())) {
                                Utils.getBox("Cập nhật quốc gia thành công!", Alert.AlertType.INFORMATION).show();
                            } else {
                                ncc.setQuocgia(c);
                                Utils.getBox("Cập nhật quốc gia thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        tbNCC.refresh();
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
            colEmail.setOnEditCommit((var evt) -> {
                try {
                    NhaCungCap ncc = evt.getRowValue();
                    if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size()) {
                        String c = ncc.getEmail();
                        String m = "";
                        if (!"".equals(evt.getNewValue()))
                            m = evt.getNewValue();
                        ncc.setEmail(m);
                        if ("".equals(m)) {
                            ncc.setEmail(c);
                            Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            Utils.getBox("Vui lòng thay đổi email để cập nhật!!!", Alert.AlertType.WARNING).show();
                        } else {
                            if (nccs.suaEmail(ncc.getNhacungcap_id(), ncc.getEmail())) {
                                Utils.getBox("Cập nhật email thành công!", Alert.AlertType.INFORMATION).show();
                            } else {
                                ncc.setEmail(c);
                                Utils.getBox("Cập nhật email thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        tbNCC.refresh();
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            colSoDT.setCellFactory(TextFieldTableCell.forTableColumn());
            colSoDT.setOnEditCommit((var evt) -> {
                try {
                    NhaCungCap ncc = evt.getRowValue();
                    if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size()) {
                        String c = ncc.getSodt();
                        String m = "";
                        if (!"".equals(evt.getNewValue()))
                            m = evt.getNewValue();
                        ncc.setSodt(m);
                        if ("".equals(m)) {
                            ncc.setSodt(c);
                            Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            Utils.getBox("Vui lòng thay đổi số điện thoại để cập nhật!!!", Alert.AlertType.WARNING).show();
                        } else {
                            if (nccs.suaSoDT(ncc.getNhacungcap_id(), ncc.getSodt())) {
                                Utils.getBox("Cập nhật số điện thoại thành công!", Alert.AlertType.INFORMATION).show();
                            } else {
                                ncc.setSodt(c);
                                Utils.getBox("Cập nhật số điện thoại thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        tbNCC.refresh();
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            Stage stage = new Stage();
            colTongMatHang.setOnEditStart(evt -> {
                NhaCungCap ncc = evt.getRowValue();
                TableView tb = new TableView();
                loadTableHH(tb);
                if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size())
                    loadHangHoa(tb, ncc.getNhacungcap_id());
                Scene scene = new Scene(tb);
                stage.setTitle("Hàng hóa");
                stage.setScene(scene);
                stage.showAndWait();
            });
            colTongMatHang.setOnEditCancel(evt -> {
                stage.close();
            });
            this.tbNCC.getColumns().addAll(colMaNCC, colTenCongTy, colDiaChi
                    , colTinhThanh, colQuocGia, colEmail, colSoDT, colTongMatHang);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadHangHoa(TableView tb, int idNCC){
        try {
            tb.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            HangHoaService s = new HangHoaService(conn);
            NhaCungCapService nccs = new NhaCungCapService(conn);
            
            tb.setItems(FXCollections.observableList(
                    s.getHangHoa(nccs.getTenCTByid(idNCC), "Nhà cung cấp")));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableHH(TableView tb){
        try {
            tb.setEditable(true);
            Connection conn;
            conn = JdbcUtils.getConn();
            HangHoaService s = new HangHoaService(conn);
            LoaiHangHoaService ss = new LoaiHangHoaService(conn);
            NhaCungCapService nccs = new NhaCungCapService(conn);
            
            TableColumn<HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
            colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
            
            TableColumn<HangHoa, String> colTenHangHoa = new TableColumn("Tên Hàng");
            colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
            
            TableColumn<HangHoa, String> colThuongHieu = new TableColumn("Thương Hiệu");
            colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
            
            TableColumn<HangHoa, String> colSoLuong = new TableColumn("Số Lượng");
            colSoLuong.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
            
            TableColumn<HangHoa, String> colGiaNhap = new TableColumn("Giá Nhập");
            colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
            
            TableColumn<HangHoa, String> colGiaNiemYet = new TableColumn("Giá Niêm Yết");
            colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("gianiemyet"));
            
            TableColumn<HangHoa, String> colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
            colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
            
            TableColumn<HangHoa, String> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
            colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
            
            TableColumn<HangHoa, Boolean> colTinhTrang = new TableColumn("Tình Trạng");
            colTinhTrang.setCellValueFactory(new PropertyValueFactory("tinhtrang"));
            
            TableColumn<HangHoa, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
            colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("tenloaihang"));
            
            TableColumn<HangHoa, String> colNhaCungCap = new TableColumn("Nhà Cung Cấp");
            colNhaCungCap.setCellValueFactory(new PropertyValueFactory("nhacungcap"));
            
            
            tb.getColumns().addAll(colMaHangHoa, colTenHangHoa
                    , colThuongHieu, colSoLuong, colGiaNhap, colGiaNiemYet
                    , colNgaySanXuat, colNgayHetHan, colTinhTrang
                    , colLoaiHangHoa, colNhaCungCap);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void themNCC(ActionEvent evt) throws IOException {
        try {
            Connection conn = JdbcUtils.getConn();
            NhaCungCapService nccs = new NhaCungCapService(conn);
            NhaCungCap ncc = this.tbNCC.getItems().get(this.tbNCC.getItems().size() - 1);
            nccs.themNCC(ncc);
            loadNCC(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logoutHandler(ActionEvent evt) throws IOException {
        try {
            Parent root;
            Stage stage = (Stage)((Node) evt.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("dangnhap.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
