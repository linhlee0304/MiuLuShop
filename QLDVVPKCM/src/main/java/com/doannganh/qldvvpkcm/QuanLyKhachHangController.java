/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.KhachHang;
import com.doannganh.pojo.KhachHang;
import com.doannganh.pojo.ThuCung;
import com.doannganh.pojo.User;
import static com.doannganh.qldvvpkcm.QuanLyThuCungController.getDayCellFactory;
import com.doannganh.service.KhachHangService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.KhachHangService;
import com.doannganh.service.ThuCungService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class QuanLyKhachHangController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TableView<KhachHang> tbKhachHang;
    @FXML private TableView<ThuCung> tbThuCung;
    @FXML private TextField txtTraCuu;
    @FXML private ComboBox<String> cbTraCuu;
    @FXML private TextField txtHoTen;
    @FXML private DatePicker dpNgaySinh;
    @FXML private ComboBox<String> cbGioiTinh;
    @FXML private TextField txtDiaChi;
    @FXML private TextField txtSDT;
    @FXML private TextField txtTen;
    @FXML private DatePicker dpNgaySinhTC;
    @FXML private ComboBox<String> cbGioiTinhTC;
    @FXML private ComboBox<String> cbMauLong;
    @FXML private ComboBox<String> cbTinhTrangSucKhoe;
    
    
    User nd = DangnhapController.nd;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã khách hàng", "Họ tên", "Số điện thoại");
    ObservableList<String> listML = null;
    ObservableList listMaKH = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        this.cbGioiTinh.getItems().addAll("Nam", "Nữ");
        this.cbGioiTinh.getSelectionModel().selectFirst();
        this.dpNgaySinh.setValue(LocalDate.of(1990, 3, 11));
        this.dpNgaySinh.setEditable(false);
        Callback<DatePicker, DateCell> dayCellFactory = getDayCellFactoryy();
        this.dpNgaySinh.setDayCellFactory(dayCellFactory);
        
        this.cbGioiTinhTC.getItems().addAll("Đực", "Cái");
        this.cbGioiTinhTC.getSelectionModel().selectFirst();
        this.cbMauLong.getItems().addAll("Trắng", "Đen", "Vàng", "Xám", "Nâu");
        this.cbMauLong.getSelectionModel().selectFirst();
        this.cbTinhTrangSucKhoe.getItems().addAll("Bình Thường", "Kém");
        this.cbTinhTrangSucKhoe.getSelectionModel().selectFirst();
        this.dpNgaySinhTC.setValue(LocalDate.of(2019, 2, 5));
        this.dpNgaySinhTC.setEditable(false);
        Callback<DatePicker, DateCell> dayCellFactoryTC = getDayCellFactory();
        this.dpNgaySinhTC.setDayCellFactory(dayCellFactoryTC);
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        this.dpNgaySinh.setConverter(converter);
        this.dpNgaySinhTC.setConverter(converter);
        
        try {
            Connection conn = JdbcUtils.getConn();
            KhachHangService khs = new KhachHangService(conn);
            listMaKH = FXCollections.observableList(khs.getIDKhachHang());
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadTable();
        loadKhachHang("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        loadTableTC();
        loadThuCung("", "");
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            if (this.txtTraCuu.getText().isEmpty()) {
                loadKhachHang("", this.cbTraCuu.getSelectionModel().getSelectedItem());
                loadThuCung("", "");
            }
            else
                loadKhachHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
        
        this.tbKhachHang.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(evt -> {
                KhachHang kh = this.tbKhachHang.getSelectionModel().getSelectedItem();
                loadThuCung(String.valueOf(kh.getIdKhachHang()), "Mã khách hàng");
            });
            return r;
        });
        
        this.tbThuCung.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked((var e) -> {
                ThuCung tc = this.tbThuCung.getSelectionModel().getSelectedItem();
                this.cbTraCuu.getSelectionModel().select("Mã khách hàng");
                this.txtTraCuu.setText(String.valueOf(tc.getIdKhachHang()));
            });
            return r;
        });
    }
    
    public void loadKhachHang(String tuKhoa, String traCuu){
        try {
            this.tbKhachHang.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            KhachHangService khs = new KhachHangService(conn);
            this.tbKhachHang.setItems(FXCollections.observableList(
                    khs.getKhachHang(tuKhoa, traCuu)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTable(){

        TableColumn<KhachHang, Integer> colMaKhachHang = new TableColumn("Mã Khách Hàng");
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory("idKhachHang"));

        TableColumn<KhachHang, String> colTenKhachHang = new TableColumn("Họ tên");
        colTenKhachHang.setCellValueFactory(new PropertyValueFactory("hoTen"));

        TableColumn<KhachHang, String> colNgaySinh = new TableColumn("Ngày Sinh");
        colNgaySinh.setCellValueFactory(new PropertyValueFactory("ngaySinh"));

        TableColumn<KhachHang, String> colGioiTinh = new TableColumn("Giới Tính");
        colGioiTinh.setCellValueFactory(new PropertyValueFactory("gioiTinh"));

        TableColumn<KhachHang, String> colDiaChi = new TableColumn("Địa Chỉ");
        colDiaChi.setCellValueFactory(new PropertyValueFactory("diaChi"));

        TableColumn<KhachHang, String> colSdt = new TableColumn("Số Điện Thoại");
        colSdt.setCellValueFactory(new PropertyValueFactory("sdt"));
        colSdt.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<KhachHang, String> colDiemTichLuy = new TableColumn("Điểm Tích Lũy");
        colDiemTichLuy.setCellValueFactory(new PropertyValueFactory("diemTichLuy"));
        colDiemTichLuy.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        colMaKhachHang.setOnEditStart(evt -> {
            Utils.getBox("Không thể sửa mã khách hàng!!!", Alert.AlertType.ERROR).show();
        });
        
        colTenKhachHang.setCellFactory(TextFieldTableCell.forTableColumn());
        colTenKhachHang.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService khs = new KhachHangService(conn);
                KhachHang kh = evt.getRowValue();
                String c = kh.getHoTen();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                kh.setHoTen(m);
                if (m == "") {
                    kh.setHoTen(c);
                    Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi họ tên để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (khs.suaHoTen(kh.getIdKhachHang(), kh.getHoTen())) {
                        Utils.getBox("Cập nhật họ tên thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        kh.setHoTen(c);
                        Utils.getBox("Cập nhật họ tên thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbKhachHang.refresh();
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        colNgaySinh.setCellFactory((TableColumn<KhachHang, String> param) -> {
            DatePicker dp = new DatePicker();
            TableCell<KhachHang, String> cell = new TableCell<KhachHang, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setText("");
                        setGraphic(null);
                    }
                    else {
                        setEditable(false);
                        
                        dp.setValue(LocalDate.parse(item));
                        dp.getEditor().setText(item);
                        Callback<DatePicker, DateCell> dayCellFactory = getDayCellFactory();
                        dp.setDayCellFactory(dayCellFactory);
                        
                        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            @Override
                            public String toString(LocalDate date) {
                                if (date != null) {
                                    return dateFormatter.format(date);
                                } else {
                                    return "";
                                }
                            }
                            @Override
                            public LocalDate fromString(String string) {
                                if (string != null && !string.isEmpty()) {
                                    return LocalDate.parse(string, dateFormatter);
                                } else {
                                    return null;
                                }
                            }
                        };
                        dp.setConverter(converter);
                        dp.setPromptText("yyyy-MM-dd");
                        
                        setText(item);
                    }
                }
            };
            
            cell.setOnMouseClicked((MouseEvent event) -> {
                KhachHang kh = tbKhachHang.getSelectionModel().getSelectedItem();
                if (kh == null)
                    cell.setGraphic(null);
                else {
                    if(event.getButton().equals(MouseButton.PRIMARY)) {
                        cell.setEditable(true);
                        dp.setEditable(false);
                    }
                    if(event.getClickCount()==2 && cell.isEditable() ) {
                        cell.setText(null);
                        cell.setGraphic(dp);
                    }
                }
            });
            
            cell.setOnKeyPressed((KeyEvent event) -> {
                if(event.getCode().equals(KeyCode.ENTER))
                {
                    try {
                        Connection conn = JdbcUtils.getConn();
                        KhachHangService khs = new KhachHangService(conn);
                        KhachHang kh = (KhachHang) tbKhachHang.getSelectionModel().getSelectedItem();
                        String ngay = dp.getEditor().getText();
                        if(kh != null) {
                            if (ngay != kh.getNgaySinh()) {
                                if (khs.suaNgaySinh(tbKhachHang.getItems().get(cell.getIndex()).getIdKhachHang(), ngay)) {
                                    kh.setNgaySinh(ngay);
                                    Utils.getBox("Cập nhật ngày sinh thành công!", Alert.AlertType.INFORMATION).show();
                                }
                            } else
                                Utils.getBox("Vui lòng thay đổi để cập nhật!!!", Alert.AlertType.WARNING).show();
                            cell.setEditable(false);
                            tbKhachHang.refresh();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return cell;
        });
        
        colGioiTinh.setCellFactory((TableColumn<KhachHang, String> p) -> {
            ComboBoxTableCell<KhachHang, String> cell = new ComboBoxTableCell<>("Nam", "Nữ");
            return cell;
        });
        colGioiTinh.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService tcs = new KhachHangService(conn);
                KhachHang tc = evt.getRowValue();
                String c = tc.getGioiTinh();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                tc.setGioiTinh(m);
                if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi giới tính để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (tcs.suaGioiTinh(tc.getIdKhachHang(), tc.getGioiTinh())) {
                        Utils.getBox("Cập nhật giới tính thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        tc.setGioiTinh(c);
                        Utils.getBox("Cập nhật giới tính thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbKhachHang.refresh();
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        colDiaChi.setCellFactory(TextFieldTableCell.forTableColumn());
        colDiaChi.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService khs = new KhachHangService(conn);
                KhachHang kh = evt.getRowValue();
                String c = kh.getDiaChi();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                kh.setDiaChi(m);
                if (m == "") {
                    kh.setDiaChi(c);
                    Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi địa chỉ để cập nhật!!!", Alert.AlertType.WARNING).show();
                } else {
                    if (khs.suaDiaChi(kh.getIdKhachHang(), kh.getDiaChi())) {
                        Utils.getBox("Cập nhật địa chỉ thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        kh.setDiaChi(c);
                        Utils.getBox("Cập nhật địa chỉthất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbKhachHang.refresh();
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        colSdt.setCellFactory(TextFieldTableCell.forTableColumn());
        colSdt.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService khs = new KhachHangService(conn);
                KhachHang kh = evt.getRowValue();
                String c = kh.getSdt();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                kh.setSdt(m);
                if (m == "") {
                    kh.setSdt(c);
                    Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi số điện thoại để cập nhật!!!", Alert.AlertType.WARNING).show();
                } else if (!m.matches("\\d+")) {
                        kh.setSdt(c);
                        Utils.getBox("Vui lòng chỉ nhập số!!!", Alert.AlertType.WARNING).show();
                } else if (m.length() != 10) {
                            kh.setSdt(c);
                            Utils.getBox("Vui lòng nhập 10 số!!!", Alert.AlertType.WARNING).show();
                } else{
                    if (khs.suaSDT(kh.getIdKhachHang(), kh.getSdt())) {
                        Utils.getBox("Cập nhật số điện thoại thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        kh.setSdt(c);
                        Utils.getBox("Cập nhật số điện thoại thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbKhachHang.refresh();
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        colDiemTichLuy.setOnEditStart(evt -> {
            Utils.getBox("Không thể điểm tích lũy!!!", Alert.AlertType.ERROR).show();
        });
        
        this.tbKhachHang.getColumns().addAll(colMaKhachHang, colTenKhachHang
                , colNgaySinh, colGioiTinh, colDiaChi
                , colSdt, colDiemTichLuy);
    }
    
    public void loadThuCung(String tuKhoa, String traCuu){
        try {
            this.tbThuCung.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            ThuCungService tcs = new ThuCungService(conn);
            this.tbThuCung.setItems(FXCollections.observableList(
                    tcs.getThuCung(tuKhoa, traCuu)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(QuanLyThuCungController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadTableTC(){
        this.tbThuCung.getColumns().clear();
        TableColumn<ThuCung, Integer> colMaThuCung = new TableColumn("Mã Thú Cưng");
        colMaThuCung.setCellValueFactory(new PropertyValueFactory("idThuCung"));

        TableColumn<ThuCung, String> colTen = new TableColumn("Tên");
        colTen.setCellValueFactory(new PropertyValueFactory("ten"));

        TableColumn<ThuCung, String> colNgaySinh = new TableColumn("Ngày Sinh");
        colNgaySinh.setCellValueFactory(new PropertyValueFactory("ngaySinh"));

        TableColumn<ThuCung, String> colGioiTinh = new TableColumn("Giới Tính");
        colGioiTinh.setCellValueFactory(new PropertyValueFactory("gioiTinh"));

        TableColumn<ThuCung, String> colMauLong = new TableColumn("Màu Lông");
        colMauLong.setCellValueFactory(new PropertyValueFactory("mauLong"));

        TableColumn<ThuCung, String> colTinhTrangSucKhoe = new TableColumn("Tình Trạng Sức Khỏe");
        colTinhTrangSucKhoe.setCellValueFactory(new PropertyValueFactory("tinhTrangSucKhoe"));

        TableColumn<ThuCung, Integer> colMaKhachHang = new TableColumn("Mã khách hàng");
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory("idKhachHang"));
        colMaKhachHang.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        colMaThuCung.setOnEditStart(evt -> {
            Utils.getBox("Không thể sửa mã thú cưng!!!", Alert.AlertType.ERROR).show();
        });
        
        colTen.setCellFactory(TextFieldTableCell.forTableColumn());
        colTen.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                ThuCungService tcs = new ThuCungService(conn);
                ThuCung tc = evt.getRowValue();
                String c = tc.getTen();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                tc.setTen(m);
                if (m == "") {
                    tc.setTen(c);
                    Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                } else if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi tên để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (tcs.suaTen(tc.getIdThuCung(), tc.getTen())) {
                        Utils.getBox("Cập nhật tên thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        tc.setTen(c);
                        Utils.getBox("Cập nhật tên thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbThuCung.refresh();
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyThuCungController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        colNgaySinh.setCellFactory((TableColumn<ThuCung, String> param) -> {
            DatePicker dp = new DatePicker();
            TableCell<ThuCung, String> cell = new TableCell<ThuCung, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setText("");
                        setGraphic(null);
                    }
                    else {
                        setEditable(false);
                        
                        dp.setValue(LocalDate.parse(item));
                        dp.getEditor().setText(item);
                        Callback<DatePicker, DateCell> dayCellFactory= getDayCellFactory();
                        dp.setDayCellFactory(dayCellFactory);
                        
                        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            @Override
                            public String toString(LocalDate date) {
                                if (date != null) {
                                    return dateFormatter.format(date);
                                } else {
                                    return "";
                                }
                            }
                            @Override
                            public LocalDate fromString(String string) {
                                if (string != null && !string.isEmpty()) {
                                    return LocalDate.parse(string, dateFormatter);
                                } else {
                                    return null;
                                }
                            }
                        };
                        dp.setConverter(converter);
                        dp.setPromptText("yyyy-MM-dd");
                        
                        setText(item);
                    }
                }
            };
            
            cell.setOnMouseClicked((MouseEvent event) -> {
                ThuCung tc = tbThuCung.getSelectionModel().getSelectedItem();
                if (tc == null)
                    cell.setGraphic(null);
                else {
                    if(event.getButton().equals(MouseButton.PRIMARY)) {
                        cell.setEditable(true);
                        dp.setEditable(false);
                    }
                    if(event.getClickCount()==2 && cell.isEditable() ) {
                        cell.setText(null);
                        cell.setGraphic(dp);
                    }
                }
            });
            
            cell.setOnKeyPressed((KeyEvent event) -> {
                if(event.getCode().equals(KeyCode.ENTER))
                {
                    try {
                        Connection conn = JdbcUtils.getConn();
                        ThuCungService tcs = new ThuCungService(conn);
                        ThuCung tc = (ThuCung) tbThuCung.getSelectionModel().getSelectedItem();
                        String ngay = dp.getEditor().getText();
                        if(tc != null) {
                            if (ngay != tc.getNgaySinh()) {
                                if (tcs.suaNgaySinh(tbThuCung.getItems().get(cell.getIndex()).getIdThuCung(), ngay)) {
                                    tc.setNgaySinh(ngay);
                                    Utils.getBox("Cập nhật ngày sinh thành công!", Alert.AlertType.INFORMATION).show();
                                }
                            } else
                                Utils.getBox("Vui lòng thay đổi để cập nhật!!!", Alert.AlertType.WARNING).show();
                            cell.setEditable(false);
                            tbThuCung.refresh();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return cell;
        });
        
        colGioiTinh.setCellFactory((TableColumn<ThuCung, String> p) -> {
            ComboBoxTableCell<ThuCung, String> cell = new ComboBoxTableCell<>("Đực", "Cái");
            return cell;
        });
        colGioiTinh.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                ThuCungService tcs = new ThuCungService(conn);
                ThuCung tc = evt.getRowValue();
                String c = tc.getGioiTinh();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                tc.setGioiTinh(m);
                if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi giới tính để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (tcs.suaGioiTinh(tc.getIdThuCung(), tc.getGioiTinh())) {
                        Utils.getBox("Cập nhật giới tính thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        tc.setGioiTinh(c);
                        Utils.getBox("Cập nhật giới tính thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbThuCung.refresh();
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyThuCungController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        colMauLong.setCellFactory((TableColumn<ThuCung, String> param) -> {
            ComboBox<String> cb = new ComboBox<>();
            listML = FXCollections.observableArrayList("Trắng", "Đen", "Vàng", "Xám", "Nâu");
            TableCell<ThuCung, String> cell = new TableCell<ThuCung, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setText("");
                        setGraphic(null);
                    }
                    else {
                        setEditable(false);
                        cb.getEditor().setText(cb.getValue());
                        cb.setValue(item);
                        cb.getSelectionModel().select(item);
                        setText(item);
                        
                        boolean trung = false;
                        for (Object n: cb.getItems())
                            if (n.equals(cb.getValue())) {
                                trung = true;
                                break;
                            }
                        if (!trung)
                            listML.add(cb.getValue());
                    }
                }
            };
            cell.setOnMouseClicked((MouseEvent event) -> {
                ThuCung tc = tbThuCung.getSelectionModel().getSelectedItem();
                if (tc == null)
                    cell.setGraphic(null);
                else {
                    if(event.getButton().equals(MouseButton.PRIMARY)) {
                        cb.getItems().clear();
                        cb.getItems().addAll(listML);
                        cell.setEditable(true);
                        cb.setEditable(true);
                    }
                    if(event.getClickCount()==2 && cell.isEditable() ) {
                        cb.getSelectionModel().select(cb.getEditor().getText());
                        cell.setText(null);
                        cell.setGraphic(cb);
                    }
                }
            });
            
            cell.setOnKeyPressed((KeyEvent event) -> {
                if(event.getCode().equals(KeyCode.ENTER))
                {
                    try {
                        Connection conn = JdbcUtils.getConn();
                        ThuCungService tcs = new ThuCungService(conn);
                        ThuCung tc = (ThuCung) tbThuCung.getSelectionModel().getSelectedItem();
                        if(tc != null) {
                            if (!"".equals(cb.getEditor().getText())) {
                                String ml = cb.getEditor().getText().toLowerCase();
                                String d = String.valueOf(ml.charAt(0));
                                cb.setValue(ml.replaceFirst(d, d.toUpperCase()));
                                
                                if (tcs.suaMauLong(tbThuCung.getItems().get(cell.getIndex()).getIdThuCung(), cb.getValue())) {
                                    tc.setMauLong(cb.getValue());
                                    Utils.getBox("Cập nhật màu lông thành công!", Alert.AlertType.INFORMATION).show();
                                }
                            } else {
                                tc.setMauLong(cb.getValue());
                                Utils.getBox("Vui lòng không để trống!!!", Alert.AlertType.WARNING).show();
                            }
                            cell.setEditable(false);
                            tbThuCung.refresh();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return cell;
        });
        
        colTinhTrangSucKhoe.setCellFactory((TableColumn<ThuCung, String> p) -> {
            ComboBoxTableCell<ThuCung, String> cell = new ComboBoxTableCell<>("Bình Thường", "Kém");
            return cell;
        });
        colTinhTrangSucKhoe.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                ThuCungService tcs = new ThuCungService(conn);
                ThuCung tc = evt.getRowValue();
                String c = tc.getTinhTrangSucKhoe();
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue();
                tc.setTinhTrangSucKhoe(m);
                if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi tình trạng sức khỏe để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (tcs.suaTinhTrangSucKhoe(tc.getIdThuCung(), tc.getTinhTrangSucKhoe())) {
                        tc.setTinhTrangSucKhoe(m);
                        Utils.getBox("Cập nhật tình trạng sức khỏe thành công!", Alert.AlertType.INFORMATION).show();
                    } else {
                        tc.setTinhTrangSucKhoe(c);
                        Utils.getBox("Cập nhật tình trạng sức khỏe thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
                this.tbThuCung.refresh();
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyThuCungController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        try {
            Connection conn = JdbcUtils.getConn();
            KhachHangService khs = new KhachHangService(conn);
            listMaKH.clear();
            listMaKH = FXCollections.observableList(khs.getIDKhachHang());
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
        colMaKhachHang.setCellFactory(ComboBoxTableCell.forTableColumn(listMaKH));
        colMaKhachHang.setOnEditCommit((var evt) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                ThuCungService tcs = new ThuCungService(conn);
                ThuCung tc = evt.getRowValue();
                String c = String.valueOf(tc.getIdKhachHang());
                String m = "";
                if (!"".equals(evt.getNewValue()))
                    m = evt.getNewValue().toString();
                tc.setIdKhachHang(Integer.parseInt(m));
                if (m.equals(c)) {
                    Utils.getBox("Vui lòng thay đổi mã khách hàng để cập nhật!", Alert.AlertType.WARNING).show();
                } else {
                    if (tcs.suaMaKH(tc.getIdThuCung(), tc.getIdKhachHang())) {
                        tc.setIdKhachHang(Integer.parseInt(m));
                        Utils.getBox("Cập nhật tình mã khách hàng thành công!", Alert.AlertType.INFORMATION).show();
                        this.tbKhachHang.getSelectionModel().clearSelection();
                        loadThuCung(String.valueOf(tc.getIdKhachHang()), "Mã khách hàng");
                    } else {
                        tc.setIdKhachHang(Integer.parseInt(c));
                        Utils.getBox("Cập nhật mã khách hàng thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }
            }catch (SQLException ex) {
                Logger.getLogger(QuanLyThuCungController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.tbThuCung.getColumns().addAll(colMaThuCung, colTen
                , colNgaySinh, colGioiTinh, colMauLong
                , colTinhTrangSucKhoe, colMaKhachHang);
    }
    
    public static Callback<DatePicker, DateCell> getDayCellFactory() {
        final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
        return dayCellFactory;
    }
    
    public static Callback<DatePicker, DateCell> getDayCellFactoryy() {
        final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                
                int nam = LocalDate.now().getYear() - 1;
                if (item.getYear() > nam) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
        return dayCellFactory;
    }
    
    public void themKH(ActionEvent evt) throws IOException {
        if (this.txtHoTen.getText().isEmpty())
            Utils.getBox("Vui lòng nhập họ tên!!!", Alert.AlertType.WARNING).show();
        else if (this.dpNgaySinh.getEditor().getText().isEmpty())
            Utils.getBox("Vui lòng chọn ngày sinh!!!", Alert.AlertType.WARNING).show();
        else if (this.txtDiaChi.getText().isEmpty())
            Utils.getBox("Vui lòng nhập địa chỉ!!!", Alert.AlertType.WARNING).show();
        else if (this.txtSDT.getText().isEmpty())
            Utils.getBox("Vui lòng nhập số điện thoại!!!", Alert.AlertType.WARNING).show();
        else if (!this.txtSDT.getText().matches("\\d+"))
            Utils.getBox("Vui lòng chỉ nhập số!!!", Alert.AlertType.WARNING).show();
        else if (this.txtSDT.getText().length() != 10)
            Utils.getBox("Vui lòng nhập 10 số!!!", Alert.AlertType.WARNING).show();
        else {
            try {
                Connection conn = JdbcUtils.getConn();
                KhachHangService khs = new KhachHangService(conn);
                KhachHang kh = new KhachHang();
                kh.setHoTen(this.txtHoTen.getText());
                kh.setNgaySinh(this.dpNgaySinh.getEditor().getText());
                kh.setGioiTinh(this.cbGioiTinh.getSelectionModel().getSelectedItem());
                kh.setDiaChi(this.txtDiaChi.getText());
                kh.setSdt(this.txtSDT.getText());
                kh.setDiemTichLuy("0");
                if(khs.themKH(kh)) {
                    Utils.getBox("Thêm khách hàng thành công!",Alert.AlertType.INFORMATION).show();
                    loadKhachHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                    listMaKH.clear();
                    listMaKH = FXCollections.observableList(khs.getIDKhachHang());
                } else
                    Utils.getBox("Thêm khách hàng thất bại!",Alert.AlertType.ERROR).show();
                this.tbKhachHang.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void themTC(ActionEvent evt) throws IOException {
        if (this.txtTen.getText().isEmpty())
            Utils.getBox("Vui lòng nhập tên!!!", Alert.AlertType.WARNING).show();
        else if (this.tbKhachHang.getSelectionModel().getSelectedItem() == null)
            Utils.getBox("Vui lòng chọn khách hàng để thêm thú cưng!!!", Alert.AlertType.WARNING).show();
        else {
            try {
                Connection conn = JdbcUtils.getConn();
                ThuCungService tcs = new ThuCungService(conn);
                ThuCung tc = new ThuCung();
                tc.setTen(this.txtTen.getText());
                tc.setNgaySinh(this.dpNgaySinhTC.getEditor().getText());
                tc.setGioiTinh(this.cbGioiTinhTC.getSelectionModel().getSelectedItem());
                tc.setTinhTrangSucKhoe(this.cbTinhTrangSucKhoe.getSelectionModel().getSelectedItem());
                tc.setMauLong(this.cbMauLong.getEditor().getText());
                tc.setIdKhachHang(this.tbKhachHang.getSelectionModel().getSelectedItem().getIdKhachHang());
                boolean trung = false;
                for (String n: this.cbMauLong.getItems())
                    if (n.equals(this.cbMauLong.getEditor().getText())) {
                        trung = true;
                        break;
                    }
                if (!trung)
                    this.cbMauLong.getItems().add(this.cbMauLong.getEditor().getText());
                if(tcs.themTC(tc)) {
                    Utils.getBox("Thêm thú cưng thành công!",Alert.AlertType.INFORMATION).show();
                    loadThuCung(String.valueOf(tc.getIdKhachHang()), "Mã khách hàng");
                } else
                    Utils.getBox("Thêm thú cưng thất bại!",Alert.AlertType.ERROR).show();
                this.tbKhachHang.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyKhachHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void clearHandler(ActionEvent evt) throws IOException {
        if (!this.txtTraCuu.getText().isEmpty())
            this.txtTraCuu.setText("");
        
    }
}
