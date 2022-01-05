/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.User;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.LoaiHangHoaService;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DateStringConverter;

/**
 * FXML Controller class
 * 
 * @author Admin
 */
public class TraCuuHangHoaQuanLyTruongController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField txtTraCuu;
    @FXML private TableView<HangHoa> tbHangHoa;
    @FXML private ComboBox<String> cbTraCuu;
    @FXML private ToggleButton tbt;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã hàng", "Tên hàng", "Thương hiệu", "Loại hàng", "Nhà cung cấp");
    User nd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        loadTable();
        loadHangHoa("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
        
        this.tbHangHoa.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(evt -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    HangHoaService s = new HangHoaService(conn);
                    HangHoa hh = this.tbHangHoa.getSelectionModel().getSelectedItem();
                    int rindex = this.tbHangHoa.getSelectionModel().getSelectedIndex();
                    TextInputDialog dialog = new TextInputDialog(hh.getGianiemyet());
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
                        else if (result.get().equals(hh.getGianiemyet()))
                            Utils.getBox("Vui lòng thay đổi giá niêm yết để cập nhật!", Alert.AlertType.WARNING).show();
                        else {
                            if (tf.getText().length() > 9)
                                Utils.getBox("Vui lòng nhập giá niêm yết < 1.000.000.000", Alert.AlertType.WARNING).show();
                            else if (Integer.parseInt(tf.getText()) < 10000)
                                    Utils.getBox("Vui lòng nhập giá niêm yết >= 10.000", Alert.AlertType.WARNING).show();
                            else if (Integer.parseInt(tf.getText()) <= Integer.parseInt(hh.getGianhap())) {
                                Utils.getBox("Vui lòng nhập giá niêm yết > giá nhập: " + hh.getGianhap(), Alert.AlertType.WARNING).show();
                            }
                                else {
                                    hh.setGianiemyet(result.get());
                                    if (s.suaGiaNiemYet(hh.getHanghoa_id(), hh.getGianiemyet())) {
                                        Utils.getBox("Cập nhật giá bán thành công!", Alert.AlertType.INFORMATION).show();
                                        this.tbHangHoa.getItems().set(rindex, hh);
                                    } else
                                        Utils.getBox("Cập nhật giá bán thất bại!!!", Alert.AlertType.ERROR).show();
                                }
                        } 
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTable(){
        TableColumn<HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
        
        TableColumn<HangHoa, String> colTenHangHoa = new TableColumn("Tên Hàng");
        colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
        
        TableColumn<HangHoa, String> colThuongHieu = new TableColumn("Thương Hiệu");
        colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
        
        TableColumn<HangHoa, BigDecimal> colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
        colSoLuong.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, BigDecimal> colGiaNhap = new TableColumn("Giá Nhập");
        colGiaNhap.setCellValueFactory(new PropertyValueFactory("gianhap"));
        colGiaNhap.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<HangHoa, BigDecimal> colGiaNiemYet = new TableColumn("Giá Niêm Yết");
        colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("gianiemyet"));
        colGiaNiemYet.setStyle( "-fx-alignment: CENTER-RIGHT;");

        TableColumn<HangHoa, java.util.Date> colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
        colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
        
        TableColumn<HangHoa, java.util.Date> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
        colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
        
        
        TableColumn<HangHoa, Boolean> colTinhTrang = new TableColumn("Tình trạng");
        colTinhTrang.setCellValueFactory(new PropertyValueFactory("tinhtrang"));
        //colTinhTrang.setEditable(false);
        List l = new ArrayList<>();
        l.add(0,false);
        l.add(1,true);
        ObservableList listTT = FXCollections.observableList(l);
        colTinhTrang.setCellFactory((TableColumn<HangHoa, Boolean> p) -> {
            ComboBoxTableCell<HangHoa, Boolean> cell = new ComboBoxTableCell<>(listTT);
            return cell;
        });
        /*colTinhTrang.setCellFactory((obj) -> {
            HBox hb = new HBox();
            ToggleGroup group = new ToggleGroup();
            ToggleButton btBan = new ToggleButton("Bán");
            btBan.setToggleGroup(group);
            ToggleButton btDung = new ToggleButton("Dừng");
            btDung.setToggleGroup(group);
            
            btn.setOnAction(evt -> {
                Utils.getBox("Bạn có xác nhận xóa hàng hóa không?", Alert.AlertType.CONFIRMATION)
                     .showAndWait().ifPresent(bt -> {
                        if (bt == ButtonType.OK) {
                            TableCell cell = (TableCell) ((ToggleButton) evt.getSource()).getParent();
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
            hb.getChildren().addAll(btBan, btDung);
            TableCell cell = new TableCell();
            cell.setGraphic(hb);
            return cell;
        });*/
        
        /*colTinhTrang.setOnEditStart((var evt) -> {
            HangHoa hh = evt.getRowValue();
            if (hh.isTinhtrang()) {
                chb.isSelected();
                chb.setSelected(true);
            }
        });*/
        /*colTinhTrang.setCellFactory((obj) -> {
            CheckBox chb = new CheckBox();
            chb.setOnAction(evt -> {
                TableCell cell = (TableCell) ((CheckBox) evt.getSource()).getParent();
                HangHoa hh = (HangHoa) cell.getTableRow().getItem();
                chb.setId(Integer.toString(hh.getHanghoa_id()));
                if (hh.isTinhtrang()) {
                    chb.isSelected();
                    chb.setSelected(true);
                }
            });
            TableCell cell = new TableCell();
            cell.setGraphic(chb);
            return cell;
        });*/
        
        TableColumn<HangHoa, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
        colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("tenloaihang"));
        
        TableColumn<HangHoa, String> colNhaCungCap= new TableColumn("Nhà Cung Cấp");
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory("nhacungcap"));
        
        this.tbHangHoa.getColumns().addAll(colMaHangHoa, colTenHangHoa, colThuongHieu
                , colSoLuong, colGiaNhap, colGiaNiemYet, colNgaySanXuat, colNgayHetHan
                , colTinhTrang, colLoaiHangHoa, colNhaCungCap);
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
            Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
