/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ThuCung;
import com.doannganh.pojo.User;
import com.doannganh.service.ThuCungService;
import com.doannganh.service.JdbcUtils;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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
public class QuanLyThuCungController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TableView<ThuCung> tbThuCung;
    @FXML private TextField txtTraCuu;
    @FXML private ComboBox<String> cbTraCuu;
    
    User nd = DangnhapController.nd;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã thú cưng", "Tên", "Mã khách hàng");
    ObservableList<String> listML = null;
    String c = "";
    int count;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        
        loadTable();
        loadThuCung("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            if (this.txtTraCuu.getText().isEmpty()) {
                loadThuCung("", this.cbTraCuu.getSelectionModel().getSelectedItem());
            }
            else
                loadThuCung(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
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
    
    
    public void loadTable(){
        this.tbThuCung.setEditable(true);

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

        TableColumn<ThuCung, String> colMaKhachHang = new TableColumn("Mã khách hàng");
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory("idKhachHang"));
        
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
                    if (empty)
                        setGraphic(null);
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
                    if (empty)
                        setGraphic(null);
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
}
