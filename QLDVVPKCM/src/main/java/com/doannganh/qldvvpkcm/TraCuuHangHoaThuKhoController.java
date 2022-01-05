/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.LoaiHangHoa;
import com.doannganh.pojo.NhaCungCap;
import com.doannganh.pojo.User;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.LoaiHangHoaService;
import com.doannganh.service.NhaCungCapService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TraCuuHangHoaThuKhoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField txtTraCuu;
    @FXML private TableView<HangHoa> tbHangHoa;
    @FXML private ComboBox<String> cbTraCuu;
    @FXML private TableView<NhaCungCap> tbNCC;
    
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã hàng", "Tên hàng", "Thương hiệu", "Loại hàng", "Nhà cung cấp");
    ObservableList listTH;
    private String th;
    User nd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        
        loadTable();
        loadTableNCC();
        loadHangHoa("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        loadNCC("", "Tên công ty");
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            if (this.txtTraCuu.getText().isEmpty()) {
                loadHangHoa("", this.cbTraCuu.getSelectionModel().getSelectedItem());
                loadNCC("", "Tên công ty");
            }
            else
                loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
        
        this.tbHangHoa.setRowFactory(obj -> {
            TableRow r = new TableRow();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem item = new MenuItem("Sửa thương hiệu");
            r.setOnMouseClicked((var e) -> {
                HangHoa hh = this.tbHangHoa.getSelectionModel().getSelectedItem();
                this.tbHangHoa.editingCellProperty().addListener(ev -> {
                    loadNCC(hh.getNhacungcap(), "Tên công ty");
                });
                loadNCC(hh.getNhacungcap(), "Tên công ty");
            });
            item.setOnAction((var e) -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    HangHoaService s = new HangHoaService(conn);
                    listTH = FXCollections.observableList(s.getThuongHieu());
                    HangHoa hh = this.tbHangHoa.getSelectionModel().getSelectedItem();
                    int rindex = this.tbHangHoa.getSelectionModel().getSelectedIndex();
                    HBox hb = new HBox();
                    VBox vb = new VBox();
                    Button bt = new Button("Sửa");
                    Button btAll = new Button("Sửa tất cả");
                    TextField tf = new TextField(hh.getThuonghieu());
                    vb.getChildren().setAll(bt, btAll);
                    hb.getChildren().setAll(tf, vb);
                    Scene scene = new Scene(hb);
                    Stage stage = new Stage();
                    stage.setTitle("Thương Hiệu");
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(App.stage);
                    stage.showAndWait();
                    
                    bt.setOnAction((var evt) -> {
                        String tam = "";
                        boolean co = true;
                        if (tf.getText().isEmpty())
                            Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                        else {
                            tam = tf.getText();
                            for (int i = 0; i < listTH.size(); i++) {
                                if (tam.equals(listTH.get(i))) {
                                    Utils.getBox("Vui lòng thay đổi thương hiệu để sửa!", Alert.AlertType.WARNING).show();
                                    co = false;
                                    break;
                                } else {
                                    tam = tf.getText();
                                }
                            }
                            if (co) {
                                hh.setThuonghieu(tam);
                                try {
                                    if (s.suaThuongHieu(hh.getHanghoa_id(), hh.getThuonghieu())) {
                                        Utils.getBox("Sửa thương hiệu thành công!", Alert.AlertType.INFORMATION).show();
                                        this.tbHangHoa.getItems().set(rindex, hh);
                                        this.tbNCC.refresh();
                                    } else
                                        Utils.getBox("Sửa thương hiệu thất bại!!!", Alert.AlertType.ERROR).show();
                                } catch (SQLException ex) {
                                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        try {
                            listTH = FXCollections.observableList(s.getThuongHieu());
                        } catch (SQLException ex) {
                            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    btAll.setOnAction((var evt) -> {
                        String tam = "";
                        boolean co = true;
                        if (tf.getText().isEmpty())
                            Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                        else {
                            tam = tf.getText();
                            for (int i = 0; i < listTH.size(); i++) {
                                if (tam.equals(listTH.get(i))) {
                                    Utils.getBox("Vui lòng thay đổi thương hiệu để sửa!", Alert.AlertType.WARNING).show();
                                    co = false;
                                    break;
                                } else {
                                    tam = tf.getText();
                                }
                            }
                            if (co) {
                                try {
                                    List l = s.getIDByThuongHieu(hh.getThuonghieu());
                                    for (int i = 0; i < l.size(); i++) {
                                        if (s.suaThuongHieu((int) l.get(i), tam)) {
                                            this.tbHangHoa.refresh();
                                            this.tbNCC.refresh();
                                        }
                                    }
                                    if (!l.isEmpty())
                                        Utils.getBox("Sửa thương hiệu thành công!", Alert.AlertType.INFORMATION).show();
                                    else
                                        Utils.getBox("Sửa thương hiệu thất bại!!!", Alert.AlertType.ERROR).show();
                                } catch (SQLException ex) {
                                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                        try {
                            listTH = FXCollections.observableList(s.getThuongHieu());
                        } catch (SQLException ex) {
                            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                } catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            contextMenu.getItems().add(item);
            r.setOnContextMenuRequested(evt -> {
                contextMenu.show(r, evt.getScreenX(), evt.getSceneY());
            });
            return r;
        });
        this.tbNCC.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked((var e) -> {
                NhaCungCap ncc = this.tbNCC.getSelectionModel().getSelectedItem();
                this.tbNCC.editingCellProperty().addListener(ev -> {
                    loadHangHoa(ncc.getTencongty(), "Nhà cung cấp");
                });
                this.cbTraCuu.getSelectionModel().select("Nhà cung cấp");
                this.txtTraCuu.setText(ncc.getTencongty());
            });
            return r;
        });
        
    }
    
    public void setTTUser(User u){
        nd = u;
    }
    
    public void loadHangHoa(String tuKhoa, String traCuu){
        try {
            this.tbHangHoa.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            HangHoaService s = new HangHoaService(conn);
            this.tbHangHoa.setItems(FXCollections.observableList(
                    s.getHangHoa(tuKhoa, traCuu)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadTable(){
        try {
            this.tbHangHoa.setEditable(true);
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
            colSoLuong.setStyle( "-fx-alignment: CENTER-RIGHT;");
            
            TableColumn<HangHoa, String> colGiaNhap = new TableColumn("Giá Nhập");
            colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
            colGiaNhap.setStyle( "-fx-alignment: CENTER-RIGHT;");
            
            TableColumn<HangHoa, String> colGiaNiemYet = new TableColumn("Giá Niêm Yết");
            colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("gianiemyet"));
            colGiaNiemYet.setStyle( "-fx-alignment: CENTER-RIGHT;");
            
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
            
            colMaHangHoa.setOnEditStart(evt -> {
                Utils.getBox("Không thể sửa mã hàng hóa!!!", Alert.AlertType.ERROR).show();
            });
            colTenHangHoa.setCellFactory(TextFieldTableCell.forTableColumn());
            colTenHangHoa.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getTenhanghoa();
                    String m = "";
                    if (!"".equals(evt.getNewValue()))
                        m = evt.getNewValue();
                    hh.setTenhanghoa(m);
                    if (m == "") {
                        hh.setTenhanghoa(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else if (m.equals(c)) {
                        Utils.getBox("Vui lòng thay đổi tên hàng hóa để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaTenHH(hh.getHanghoa_id(), hh.getTenhanghoa())) {
                            Utils.getBox("Cập nhật tên hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setTenhanghoa(c);
                            Utils.getBox("Cập nhật tên hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            
            
            //ContextMenu contextMenu = new ContextMenu();
            //MenuItem item1 = new MenuItem("Thêm thương hiệu");
            //MenuItem item2 = new MenuItem("Sửa thương hiệu");
            //contextMenu.getItems().addAll(item1, item2);
            //contextMenu.show(colThuongHieu);
            listTH = FXCollections.observableList(s.getThuongHieu());
            colThuongHieu.setCellFactory((TableColumn<HangHoa, String> p) -> {
                ComboBoxTableCell<HangHoa, String> cell = new ComboBoxTableCell<>(listTH);
                return cell;
            });
            colThuongHieu.setOnEditCommit((TableColumn.CellEditEvent<HangHoa, String> evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = String.valueOf(evt.getOldValue());
                    String m = String.valueOf(evt.getNewValue());
                    hh.setThuonghieu(m);
                    
                    if (m.equals(c)) {
                        hh.setThuonghieu(c);
                        Utils.getBox("Vui lòng thay đổi thương hiệu để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaThuongHieu(hh.getHanghoa_id(), hh.getThuonghieu())) {
                            Utils.getBox("Cập nhật thương hiệu thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setThuonghieu(c);
                            Utils.getBox("Cập nhật thương hiệu thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }   
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                    listTH = FXCollections.observableList(s.getThuongHieu());
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colSoLuong.setCellFactory(TextFieldTableCell.forTableColumn());
            colSoLuong.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getSoluongtrongkho();
                    String m = "";
                    if ("".equals(evt.getNewValue())) {
                        hh.setSoluongtrongkho(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else {
                        m = evt.getNewValue();
                        hh.setSoluongtrongkho(m);
                        if (!m.matches("\\d+")) {
                            hh.setSoluongtrongkho(c);
                            Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            hh.setSoluongtrongkho(c);
                            Utils.getBox("Vui lòng thay đổi số lượng để cập nhật!", Alert.AlertType.WARNING).show();
                        } else {
                            if (m.length() > 4) {
                                hh.setSoluongtrongkho(c);
                                Utils.getBox("Vui lòng nhập số lượng <= 1.000", Alert.AlertType.WARNING).show();
                            } else if (Integer.parseInt(m) < 0) {
                                hh.setSoluongtrongkho(c);
                                Utils.getBox("Vui lòng nhập số lượng >= 0", Alert.AlertType.WARNING).show();
                            } else {
                                if (s.suaSoLuong(hh.getHanghoa_id(), hh.getSoluongtrongkho())) {
                                    Utils.getBox("Cập nhật số lượng thành công!", Alert.AlertType.INFORMATION).show();
                                } else {
                                    hh.setSoluongtrongkho(c);
                                    Utils.getBox("Cập nhật số lượng thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                            }
                        }
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colGiaNhap.setCellFactory(TextFieldTableCell.forTableColumn());
            colGiaNhap.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getGianhap();
                    String m;
                    if ("".equals(evt.getNewValue())) {
                        hh.setGianhap(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else {
                        m = evt.getNewValue();
                        hh.setGianhap(m);
                        if (m.matches("\\d+") == false) {
                            hh.setGianhap(c);
                            Utils.getBox("Vui lòng nhập số!", Alert.AlertType.WARNING).show();
                        } else if (m.equals(c)) {
                            hh.setGianhap(c);
                            Utils.getBox("Vui lòng thay đổi giá nhập để cập nhật!", Alert.AlertType.WARNING).show();
                        } else {
                            if (m.length() > 9) {
                                hh.setGianhap(c);
                                Utils.getBox("Vui lòng nhập giá nhập < 1.000.000.000!", Alert.AlertType.WARNING).show();
                            } else if (Integer.parseInt(m) < 10000) {
                                hh.setGianhap(c);
                                Utils.getBox("Vui lòng nhập giá nhập >= 10.000", Alert.AlertType.WARNING).show();
                            } else if (Integer.parseInt(m) >= Integer.parseInt(hh.getGianiemyet())) {
                                hh.setGianhap(c);
                                Utils.getBox("Vui lòng nhập giá nhập < giá niêm yết: " + hh.getGianiemyet(), Alert.AlertType.WARNING).show();
                            } else {
                                if (s.suaGiaNhap(hh.getHanghoa_id(), hh.getGianhap(), nccs.getNCCByTen(hh.getNhacungcap()))) {
                                    
                                    Utils.getBox("Cập nhật giá nhập thành công!", Alert.AlertType.INFORMATION).show();
                                } else {
                                    hh.setGianhap(c);
                                    Utils.getBox("Cập nhật giá nhập thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                            }
                        }
                        
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colGiaNiemYet.setOnEditStart(evt -> {
                Utils.getBox("Không có quyền sửa giá niêm yết!", Alert.AlertType.ERROR).show();
            });
            colNgaySanXuat.setCellFactory(TextFieldTableCell.forTableColumn());
            colNgaySanXuat.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getNgaysanxuat();
                    String m = "";
                    if (!"".equals(evt.getNewValue()))
                        m = evt.getNewValue();
                    hh.setNgaysanxuat(m);
                    if (m == "") {
                        hh.setNgaysanxuat(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else if (m.equals(c)) {
                        Utils.getBox("Vui lòng thay đổi ngày sản xuất để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaNgaySX(hh.getHanghoa_id(), hh.getNgaysanxuat())) {
                            Utils.getBox("Cập nhật ngày sản xuất thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setNgaysanxuat(c);
                            Utils.getBox("Cập nhật ngày sản xuất thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            colNgayHetHan.setCellFactory(TextFieldTableCell.forTableColumn());
            colNgayHetHan.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = hh.getNgayhethan();
                    String m = "";
                    if (!"".equals(evt.getNewValue()))
                        m = evt.getNewValue();
                    hh.setNgayhethan(m);
                    if (m == "") {
                        hh.setNgayhethan(c);
                        Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                    } else if (m.equals(c)) {
                        Utils.getBox("Vui lòng thay đổi ngày hết hạn để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaNgayHH(hh.getHanghoa_id(), hh.getNgayhethan())) {
                            Utils.getBox("Cập nhật ngày hết hạn thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setNgayhethan(c);
                            Utils.getBox("Cập nhật ngày hết hạn thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            List l = new ArrayList<>();
            l.add(0,false);
            l.add(1,true);
            ObservableList listTT = FXCollections.observableList(l);
            colTinhTrang.setCellFactory(ComboBoxTableCell.<HangHoa, Boolean>forTableColumn(listTT));
            colTinhTrang.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    Boolean c = evt.getOldValue();
                    Boolean m = evt.getNewValue();
                    hh.setTinhtrang(m);
                    if (m.equals(c)) {
                        hh.setTinhtrang(c);
                        Utils.getBox("Vui lòng chọn loại hàng hóa để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaTinhTrang(hh.getHanghoa_id(), hh.isTinhtrang())) {
                            Utils.getBox("Cập nhật loại hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setTinhtrang(c);
                            Utils.getBox("Cập nhật loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            ObservableList listLHH = FXCollections.observableList(ss.getLoaiHH());
            colLoaiHangHoa.setCellFactory(ComboBoxTableCell.<HangHoa, String>forTableColumn(listLHH));
            colLoaiHangHoa.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = String.valueOf(evt.getOldValue());
                    String m = String.valueOf(evt.getNewValue());
                    hh.setTenloaihang(m);
                    if (m.equals(c)) {
                        hh.setTenloaihang(c);
                        Utils.getBox("Vui lòng chọn loại hàng hóa để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaLoaiHH(hh.getHanghoa_id(), ss.getLoaiHHByTen(hh.getTenloaihang()))) {
                            Utils.getBox("Cập nhật loại hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                        } else {
                            hh.setTenloaihang(c);
                            Utils.getBox("Cập nhật loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            ObservableList listNCC = FXCollections.observableList(nccs.getNCCs());
            colNhaCungCap.setCellFactory(ComboBoxTableCell.<HangHoa, String>forTableColumn(listNCC));
            colNhaCungCap.setOnEditCommit((var evt) -> {
                try {
                    HangHoa hh = evt.getRowValue();
                    String c = String.valueOf(evt.getOldValue());
                    String m = String.valueOf(evt.getNewValue());
                    hh.setNhacungcap(m);
                    if (m.equals(c)) {
                        hh.setNhacungcap(c);
                        Utils.getBox("Vui lòng chọn loại hàng hóa để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (s.suaNhaCC(hh.getHanghoa_id(), nccs.getNCCByTen(hh.getNhacungcap()))) {
                            Utils.getBox("Cập nhật loại hàng hóa thành công!", Alert.AlertType.INFORMATION).show();
                            if (this.cbTraCuu.getSelectionModel().isSelected(4))
                                this.txtTraCuu.setText(hh.getNhacungcap());
                        } else {
                            hh.setNhacungcap(c);
                            Utils.getBox("Cập nhật loại hàng hóa thất bại!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                    this.tbHangHoa.refresh();
                    this.tbNCC.refresh();
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableColumn colAction = new TableColumn();
            colAction.setCellFactory((obj) -> {
                Button btn = new Button("Xóa");
                
                btn.setOnAction(evt -> {
                    Utils.getBox("Bạn có xác nhận xóa hàng hóa không?", Alert.AlertType.CONFIRMATION)
                         .showAndWait().ifPresent(bt -> {
                            if (bt == ButtonType.OK) {
                                TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                                HangHoa hh = (HangHoa) cell.getTableRow().getItem();

                                if ("0".equals(hh.getSoluongtrongkho())) {
                                    try {
                                        if (s.deleteHH(hh.getHanghoa_id())){
                                            loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                                            Utils.getBox("Đã xóa hàng hóa thành công", Alert.AlertType.INFORMATION).show();
                                        } else
                                            Utils.getBox("Đã xóa hàng hóa thất bại", Alert.AlertType.ERROR).show();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else
                                    Utils.getBox("Chưa thể xóa hàng hóa khi số lượng trong kho > 0", Alert.AlertType.WARNING).show();
                                
                            }
                         });
                });
                TableCell cell = new TableCell();
                cell.setGraphic(btn);
                return cell;
            });
            this.tbHangHoa.getColumns().addAll(colMaHangHoa, colTenHangHoa
                    , colThuongHieu, colSoLuong, colGiaNhap, colGiaNiemYet
                    , colNgaySanXuat, colNgayHetHan, colTinhTrang
                    , colLoaiHangHoa, colNhaCungCap);
        } catch (SQLException ex) {
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadNCC(String tuKhoa, String traCuu){
        try {
            this.tbNCC.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            NhaCungCapService s = new NhaCungCapService(conn);
            this.tbNCC.setItems(FXCollections.observableList(
                    s.getNCC(tuKhoa, traCuu)));
            /*NhaCungCap ncc = new NhaCungCap();
            ncc.setNhacungcap_id(this.tbNCC.getItems().size() + 1);
            ncc.setTencongty("NULL");
            ncc.setDiachi("NULL");
            ncc.setTinhthanh("NULL");
            ncc.setQuocgia("NULL");
            ncc.setEmail("NULL");
            ncc.setSodt("NULL");
            ncc.setTongmathang(0);
            this.tbNCC.getItems().add(ncc);*/
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(QuanLyNhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableNCC(){
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
            colSoDT.setStyle( "-fx-alignment: CENTER-RIGHT;");
            
            TableColumn<NhaCungCap, Integer> colTongMatHang = new TableColumn("Tổng mặt hàng");
            colTongMatHang.setCellValueFactory(new PropertyValueFactory("tongmathang"));
            colTongMatHang.setStyle( "-fx-alignment: CENTER-RIGHT;");
            
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
                                this.txtTraCuu.setText(ncc.getTencongty());
                            } else {
                                ncc.setTencongty(c);
                                Utils.getBox("Cập nhật tên công ty thất bại!!!", Alert.AlertType.ERROR).show();
                            }
                        }
                        this.tbNCC.refresh();
                        this.tbHangHoa.refresh();
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
                        this.tbNCC.refresh();
                        this.tbHangHoa.refresh();
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
                        this.tbNCC.refresh();
                        this.tbHangHoa.refresh();
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
                        this.tbNCC.refresh();
                        this.tbHangHoa.refresh();
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
                        this.tbNCC.refresh();
                        this.tbHangHoa.refresh();
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
                        this.tbNCC.refresh();
                        this.tbHangHoa.refresh();
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            Stage stage = new Stage();
            colTongMatHang.setOnEditStart(evt -> {
                NhaCungCap ncc = evt.getRowValue();
                /*TableView tb = new TableView();
                loadTableHH(tb);
                if (ncc.getNhacungcap_id() != this.tbNCC.getItems().size())
                    loadHangHoa(tb, ncc.getNhacungcap_id());
                Scene scene = new Scene(tb);
                stage.setTitle("Hàng hóa");
                stage.setScene(scene);
                stage.showAndWait();*/
            });
            /*colTongMatHang.setOnEditCancel(evt -> {
                stage.close();
            });*/
            this.tbNCC.getColumns().addAll(colMaNCC, colTenCongTy, colDiaChi
                    , colTinhThanh, colQuocGia, colEmail, colSoDT, colTongMatHang);
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
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clearHandler(ActionEvent evt) throws IOException {
        if (!this.txtTraCuu.getText().isEmpty())
            this.txtTraCuu.setText("");
        
    }
}
