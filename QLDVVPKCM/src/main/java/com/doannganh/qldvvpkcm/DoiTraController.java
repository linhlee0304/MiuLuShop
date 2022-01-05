/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.CapNhatHoaDon;
import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.DonHang;
import com.doannganh.pojo.User;
import com.doannganh.service.CapNhatHoaDonService;
import com.doannganh.service.ChiTietDonHangService;
import com.doannganh.service.DonHangService;
import com.doannganh.service.HangHoaService;
import com.doannganh.service.JdbcUtils;
import com.doannganh.service.UserService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class DoiTraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TableView<DonHang> tbDH;
    @FXML private TableView<ChiTietDonHang> tbCTDH;
    @FXML private TextField txtTraCuu;
    @FXML private ComboBox<String> cbTraCuu;
    
    User nd = DangnhapController.nd;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã đơn hàng", "Ngày mua hàng", "Nhân viên", "Khách hàng");
    boolean co = true;
    List<ChiTietDonHang> chonCTDH = new ArrayList<>();
    int indexDH = -1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tbCTDH.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //this.tbCTDH.getSelectionModel().setCellSelectionEnabled(true);
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        loadTableDH();
        loadDonHang("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        loadTableCTDH();
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            if (this.txtTraCuu.getText().isEmpty()) {
                loadDonHang("", this.cbTraCuu.getSelectionModel().getSelectedItem());
            }
            else
                loadDonHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
            this.tbCTDH.getItems().clear();
        });
        
        this.tbDH.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked((var e) -> {
                DonHang dh = this.tbDH.getSelectionModel().getSelectedItem();
                indexDH = this.tbDH.getSelectionModel().getSelectedIndex();
                /*this.tbDH.editingCellProperty().addListener(ev -> {
                    loadCTDH(dh.getNhacungcap(), "Tên công ty");
                });*/
                loadCTDH(dh.getDonhang_id());
            });
            return r;
        });
        
        this.tbCTDH.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked((var e) -> {
                ChiTietDonHang ctdh = this.tbCTDH.getSelectionModel().getSelectedItem();
                boolean trung = false;
                for (ChiTietDonHang n: chonCTDH) {
                    if(n.equals(ctdh)) {
                        trung = true;
                        break;
                    } 
                }
                if (trung)
                    chonCTDH.remove(ctdh);
                else
                    chonCTDH.add(ctdh);
            });
            return r;
        });
    }    
    
    public void loadDonHang(String tuKhoa, String traCuu){
        try {
            this.tbDH.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            DonHangService dhs = new DonHangService(conn);
            this.tbDH.setItems(FXCollections.observableList(
                    dhs.getDonHang(tuKhoa, traCuu)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadTableDH(){
            
        TableColumn<DonHang, Integer> colMaDonHang = new TableColumn("Mã Đơn Hàng");
        colMaDonHang.setCellValueFactory(new PropertyValueFactory<>("donhang_id"));

        TableColumn<DonHang, String> colNgayTaoDH = new TableColumn("Ngày Mua Hàng");
        colNgayTaoDH.setCellValueFactory(new PropertyValueFactory("ngaytaodh"));

        TableColumn<DonHang, String> colNhanVien = new TableColumn("Nhân Viên");
        colNhanVien.setCellValueFactory(new PropertyValueFactory("hoTenNV"));

        TableColumn<DonHang, String> colKhachHang = new TableColumn("Khách Hàng");
        colKhachHang.setCellValueFactory(new PropertyValueFactory("hoTenKH"));

        TableColumn<DonHang, Integer> colTongCTDH = new TableColumn("Tổng Hàng Hóa");
        colTongCTDH.setCellValueFactory(new PropertyValueFactory("tongCTDH"));
        colTongCTDH.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<DonHang, String> colTongTien = new TableColumn("Tổng Tiền");
        colTongTien.setCellValueFactory(new PropertyValueFactory("tongTien"));
        colTongTien.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn colInHD = new TableColumn();
        colInHD.setCellFactory((obj) -> {
            Button btn = new Button("In Hóa Đơn");
            btn.setOnAction(evt -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    DonHangService dhs = new DonHangService(conn);
                    TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                    DonHang dh = (DonHang) cell.getTableRow().getItem();
                    int index = cell.getTableRow().getIndex();
                    if (!cell.isEmpty()) {
                        dh.setNhanvien_id(dhs.getIDNVByIDDH(dh.getDonhang_id()));
                        inHoaDon(dh);
                    }
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        colTongTien.setCellFactory(tc -> new TableCell<DonHang, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    BigDecimal tien = new BigDecimal(value);
                    setText(Utils.moneyBigDecimalFormat(tien));
                }
            }
        });

        this.tbDH.getColumns().addAll(colMaDonHang, colNgayTaoDH
                , colNhanVien, colKhachHang, colTongCTDH, colTongTien, colInHD);
        
    }
    
    public void loadCTDH(int id){
        try {
            chonCTDH.clear();
            this.tbCTDH.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
            this.tbCTDH.setItems(FXCollections.observableList(
                    ctdhs.getCTDH(id)));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadTableCTDH(){
        TableColumn<ChiTietDonHang, Integer> colMaHangHoa = new TableColumn("Mã Hàng Hóa");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));

        TableColumn<ChiTietDonHang, String> colTenHang = new TableColumn("Tên Hàng Hóa");
        colTenHang.setCellValueFactory(new PropertyValueFactory("tenhang"));

        TableColumn<ChiTietDonHang, String> colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soluong"));
        colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, String> colDonGia = new TableColumn("Đơn Giá");
        colDonGia.setCellValueFactory(new PropertyValueFactory("dongia"));
        colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, Integer> colGiamGia = new TableColumn("Giảm Giá");
        colGiamGia.setCellValueFactory(new PropertyValueFactory("giamgia"));
        colGiamGia.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, String> colThanhTien = new TableColumn("Thành Tiền");
        colThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");

        /*TableColumn colAction = new TableColumn();
        colAction.setCellFactory((obj) -> {
            CheckBox chbx = new CheckBox();
            //Lỗi check box nhảy, tự chọn trùng idhanghoa
            chbx.setOnAction(evt -> {
                TableCell cell = (TableCell) ((CheckBox) evt.getSource()).getParent();
                ChiTietDonHang ctdh = (ChiTietDonHang) cell.getTableRow().getItem();
                List<ChiTietDonHang> dsCTDH = chonCTDH;
                if (!cell.isEmpty() ) {
                    ctdh.setDonhang_id(this.tbDH.getSelectionModel().getSelectedItem().getDonhang_id());
                    chbx.setId(String.valueOf(ctdh.getHanghoa_id()));
                    if (chbx.isSelected()) {
                        dsCTDH.add(ctdh);
                    }
                    else {
                       dsCTDH.remove(ctdh);
                    }
                }
                chonCTDH = dsCTDH;
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(chbx);
            return cell;
        });*/

        colDonGia.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    BigDecimal dg = new BigDecimal(value);
                    setText(Utils.moneyBigDecimalFormat(dg));
                }
            }
        });

        colThanhTien.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    BigDecimal tien = new BigDecimal(value);
                    setText(Utils.moneyBigDecimalFormat(tien));
                }
            }
        });

        this.tbCTDH.getColumns().addAll(colMaHangHoa, colTenHang, colSoLuong
                , colDonGia, colGiamGia, colThanhTien);
        
    }
    
    public void inHoaDon(DonHang dh){
        try {
            Connection conn = JdbcUtils.getConn();
            UserService s = new UserService(conn);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/qldvvpkcm", "root", "12345678");
            String reportPath = "/report/HoaDon.jrxml";
            Map<String, Object> params = new HashMap<>();  
            params.put("donhang_id", dh.getDonhang_id());  
            params.put("ngayTaoDH", dh.getNgaytaodh());
            params.put("hoten", s.getUserByID(dh.getNhanvien_id()).getHoten());
            JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(reportPath));
            JasperPrint jp = JasperFillManager.fillReport(jr, params, con);
            JasperViewer jv = new JasperViewer(jp, false);
            //asperViewer.viewReport(jp, false);
            jv.setTitle("Hóa Đơn");
            jv.setZoomRatio(new Float(0.75));
            jv.setVisible(true);
            con.close();
        } catch(ClassNotFoundException | SQLException | JRException ex) {
            ex.printStackTrace();
        }
    }
    
    public void doiHang(ActionEvent evt) throws IOException {
        if (chonCTDH.size() <= 0)
            Utils.getBox("Vui lòng chọn hàng hóa cần đổi!!!", Alert.AlertType.WARNING).show();
        else {
            VBox vb = new VBox();
            AnchorPane ap = new AnchorPane();
            Button btOK = new Button("OK");
            Button btCancel = new Button("Cancel");
            TableView<ChiTietDonHang> tbCapNhat = new TableView();
            
            TableColumn<ChiTietDonHang, Integer> colMaHangHoa = new TableColumn("Mã Hàng Hóa");
            colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));

            TableColumn<ChiTietDonHang, String> colTenHang = new TableColumn("Tên Hàng Hóa");
            colTenHang.setCellValueFactory(new PropertyValueFactory("tenhang"));

            TableColumn<ChiTietDonHang, String> colSoLuong = new TableColumn("Số Lượng");
            colSoLuong.setCellValueFactory(new PropertyValueFactory("soluong"));
            colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");

            TableColumn<ChiTietDonHang, String> colDonGia = new TableColumn("Đơn Giá");
            colDonGia.setCellValueFactory(new PropertyValueFactory("dongia"));
            colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");

            TableColumn<ChiTietDonHang, Integer> colGiamGia = new TableColumn("Giảm Giá");
            colGiamGia.setCellValueFactory(new PropertyValueFactory("giamgia"));
            colGiamGia.setStyle("-fx-alignment: CENTER-RIGHT;");

            TableColumn<ChiTietDonHang, String> colThanhTien = new TableColumn("Thành Tiền");
            colThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
            colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");
            
            colDonGia.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        BigDecimal dg = new BigDecimal(value);
                        setText(Utils.moneyBigDecimalFormat(dg));
                    }
                }
            });

            colThanhTien.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        BigDecimal tien = new BigDecimal(value);
                        setText(Utils.moneyBigDecimalFormat(tien));
                    }
                }
            });
            
            tbCapNhat.getColumns().addAll(colMaHangHoa, colTenHang, colSoLuong
                , colDonGia, colGiamGia, colThanhTien);
            tbCapNhat.setItems(FXCollections.observableList(chonCTDH));
            
            ap.getChildren().setAll(btOK, btCancel);
            vb.getChildren().setAll(tbCapNhat, ap);
            vb.setPrefSize(700, 500);
            tbCapNhat.setPrefSize(650, 500);
            btOK.setLayoutX(vb.getPrefWidth() / 2 - 50);
            btCancel.setLayoutX(vb.getPrefWidth() / 2 + 50);
            Scene scene = new Scene(vb);
            Stage stage = new Stage();
            
            
            tbCapNhat.setRowFactory(obj -> {
                TableRow r = new TableRow();
                r.setOnMouseClicked(e -> {
                    try {
                        Connection conn = JdbcUtils.getConn();
                        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                        ChiTietDonHang ctdh = tbCapNhat.getSelectionModel().getSelectedItem();
                        ctdh.setDonhang_id(this.tbDH.getSelectionModel().getSelectedItem().getDonhang_id());
                        String slCTDH = ctdhs.getCTDHByIDHHDH(ctdh.getDonhang_id(), ctdh.getHanghoa_id()).getSoluong();
                        int rindex = tbCapNhat.getSelectionModel().getSelectedIndex();
                        TextInputDialog dialog = new TextInputDialog(ctdh.getSoluong());
                        dialog.setTitle("Trả Hàng");
                        dialog.setHeaderText("Hãy nhập số lượng cần trả:");
                        dialog.setContentText("Số lượng:");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                            TextField tf = dialog.getEditor();
                            if (tf.getText().isEmpty())
                                Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                            else if (!tf.getText().matches("\\d+"))
                                Utils.getBox("Vui lòng chỉ nhập số nguyên dương!", Alert.AlertType.WARNING).show();
                            else if (result.get().equals(ctdh.getSoluong()))
                                Utils.getBox("Vui lòng thay đổi số lượng để cập nhật!", Alert.AlertType.WARNING).show();
                            else {
                                if (Integer.parseInt(tf.getText()) > Integer.parseInt(slCTDH))
                                    Utils.getBox("Vui lòng nhập số lượng <= " + Integer.parseInt(slCTDH), Alert.AlertType.WARNING).show();
                                else if (Integer.parseInt(tf.getText()) <= 0)
                                        Utils.getBox("Vui lòng nhập số lượng > 0", Alert.AlertType.WARNING).show();
                                    else {
                                        ctdh.setSoluong(result.get());
                                        Utils.getBox("Chọn số lượng thành công!", Alert.AlertType.INFORMATION).show();
                                        tbCapNhat.getItems().set(rindex, ctdh);
                                    }
                            } 
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                return r;
            });
            
            btOK.setOnAction(e -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    HangHoaService hhs = new HangHoaService(conn);
                    CapNhatHoaDonService cnhds= new CapNhatHoaDonService(conn);
                    ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                    
                    CapNhatHoaDon cnhd = new CapNhatHoaDon();
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String ngay = simpleformat.format(cal.getTime());
                    
                    int idDH = 0;
                    int idHH = 0;
                    ChiTietDonHang ctdh = null;
                    String mess = "";
                    BigDecimal tongTien = BigDecimal.ZERO;
                    for (int i = 0; i < tbCapNhat.getItems().size(); i++) {
                        ctdh = tbCapNhat.getItems().get(i);
                        ctdh.setDonhang_id(this.tbDH.getSelectionModel().getSelectedItem().getDonhang_id());
                        idDH = ctdh.getDonhang_id();
                        idHH = ctdh.getHanghoa_id();
                        int slHH = Integer.parseInt(hhs.getSoLuongByIDHH(idHH)) + Integer.parseInt(ctdh.getSoluong());
                        String slCTDH = ctdhs.getCTDHByIDHHDH(ctdh.getDonhang_id(), ctdh.getHanghoa_id()).getSoluong();
                        hhs.suaSoLuong(idHH, String.valueOf(slHH));
                        mess = mess.concat("Mã: " + idHH + "\t Số lượng: " + ctdh.getSoluong() + "\n");
                        tongTien = tongTien.add((new BigDecimal(ctdh.getDongia())).multiply(new BigDecimal(ctdh.getSoluong())).multiply(BigDecimal.ONE.subtract(new BigDecimal(ctdh.getGiamgia()))));
                        if (ctdh.getSoluong().equals(ctdhs.getCTDHByIDHHDH(idDH, idHH).getSoluong()))
                            ctdhs.xoaCTDH(ctdh);
                        else {
                            ChiTietDonHang n = ctdh;
                            int slM = Integer.parseInt(slCTDH) - Integer.parseInt(ctdh.getSoluong());
                            n.setSoluong(String.valueOf(slM));
                            ctdhs.suaSoLuongCTDH(n);
                        }
                        
                        
                    }
                    chonCTDH.clear();
                    cnhd.setDonhang_id(idDH);
                    cnhd.setNhanvien_id(nd.getUser_id());
                    cnhd.setNgayCapNhat(ngay);
                    cnhd.setGhichu("Danh sách hàng được trả:\n".concat(mess) + "Tổng tiền thu khách: " + Utils.moneyBigDecimalFormat(tongTien));
                    cnhds.themCNHD(cnhd);
                    loadDonHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                    this.tbDH.getSelectionModel().select(indexDH);
                    loadCTDH(idDH);
                    Utils.getBox(cnhd.getGhichu() + "\nBạn có muốn tạo hóa đơn hay không?",
                            Alert.AlertType.CONFIRMATION).showAndWait().ifPresent(act -> {
                                if (act == ButtonType.OK);
                            });
                    stage.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            btCancel.setOnAction(e -> {
                stage.close();
            });
            
            stage.setTitle("Trả Hàng");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(App.stage);
            stage.showAndWait();
        }
    }
    
    public void traHang(ActionEvent evt) throws IOException {
        if (chonCTDH.size() <= 0)
            Utils.getBox("Vui lòng chọn hàng hóa cần trả!!!", Alert.AlertType.WARNING).show();
        else {
            HBox hb = new HBox();
            VBox vb = new VBox();
            AnchorPane ap1 = new AnchorPane();
            AnchorPane ap2 = new AnchorPane();
            Button btOK = new Button("OK");
            Button btCancel = new Button("Cancel");
            TableView<ChiTietDonHang> tbCapNhat = new TableView();
            
            TableColumn<ChiTietDonHang, Integer> colMaHangHoa = new TableColumn("Mã Hàng Hóa");
            colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));

            TableColumn<ChiTietDonHang, String> colTenHang = new TableColumn("Tên Hàng Hóa");
            colTenHang.setCellValueFactory(new PropertyValueFactory("tenhang"));

            TableColumn<ChiTietDonHang, String> colSoLuong = new TableColumn("Số Lượng");
            colSoLuong.setCellValueFactory(new PropertyValueFactory("soluong"));
            colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");

            TableColumn<ChiTietDonHang, String> colDonGia = new TableColumn("Đơn Giá");
            colDonGia.setCellValueFactory(new PropertyValueFactory("dongia"));
            colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");

            TableColumn<ChiTietDonHang, Integer> colGiamGia = new TableColumn("Giảm Giá");
            colGiamGia.setCellValueFactory(new PropertyValueFactory("giamgia"));
            colGiamGia.setStyle("-fx-alignment: CENTER-RIGHT;");

            TableColumn<ChiTietDonHang, String> colThanhTien = new TableColumn("Thành Tiền");
            colThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
            colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");
            
            colDonGia.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        BigDecimal dg = new BigDecimal(value);
                        setText(Utils.moneyBigDecimalFormat(dg));
                    }
                }
            });

            colThanhTien.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        BigDecimal tien = new BigDecimal(value);
                        setText(Utils.moneyBigDecimalFormat(tien));
                    }
                }
            });
            
            tbCapNhat.getColumns().addAll(colMaHangHoa, colTenHang, colSoLuong
                , colDonGia, colGiamGia, colThanhTien);
            tbCapNhat.setItems(FXCollections.observableList(chonCTDH));
            
            ap2.setPrefSize(200, 500);
            hb.setPrefSize(700, 500);
            ap1.getChildren().setAll(btOK, btCancel);
            vb.getChildren().setAll(tbCapNhat, ap1);
            hb.getChildren().setAll(vb, ap2); 
            
            //tbCapNhat.setPrefSize(550, 450);
            btOK.setLayoutX(vb.getPrefWidth() / 2 - 50);
            btCancel.setLayoutX(vb.getPrefWidth() / 2 + 50);
            Scene scene = new Scene(hb);
            Stage stage = new Stage();
            
            
            tbCapNhat.setRowFactory(obj -> {
                TableRow r = new TableRow();
                r.setOnMouseClicked(e -> {
                    try {
                        Connection conn = JdbcUtils.getConn();
                        ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                        ChiTietDonHang ctdh = tbCapNhat.getSelectionModel().getSelectedItem();
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(TraCuuHangHoaQuanLyTruongController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                return r;
            });
            
            btOK.setOnAction(e -> {
                try {
                    Connection conn = JdbcUtils.getConn();
                    HangHoaService hhs = new HangHoaService(conn);
                    CapNhatHoaDonService cnhds= new CapNhatHoaDonService(conn);
                    ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                    
                    CapNhatHoaDon cnhd = new CapNhatHoaDon();
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String ngay = simpleformat.format(cal.getTime());
                    
                    int idDH = 0;
                    int idHH = 0;
                    ChiTietDonHang ctdh = null;
                    String mess = "";
                    BigDecimal tongTien = BigDecimal.ZERO;
                    for (int i = 0; i < tbCapNhat.getItems().size(); i++) {
                        ctdh = tbCapNhat.getItems().get(i);
                        ctdh.setDonhang_id(this.tbDH.getSelectionModel().getSelectedItem().getDonhang_id());
                        idDH = ctdh.getDonhang_id();
                        idHH = ctdh.getHanghoa_id();
                        int slHH = Integer.parseInt(hhs.getSoLuongByIDHH(idHH)) + Integer.parseInt(ctdh.getSoluong());
                        String slCTDH = ctdhs.getCTDHByIDHHDH(ctdh.getDonhang_id(), ctdh.getHanghoa_id()).getSoluong();
                        hhs.suaSoLuong(idHH, String.valueOf(slHH));
                        mess = mess.concat("Mã: " + idHH + "\t Số lượng: " + ctdh.getSoluong() + "\n");
                        tongTien = tongTien.add((new BigDecimal(ctdh.getDongia())).multiply(new BigDecimal(ctdh.getSoluong())).multiply(BigDecimal.ONE.subtract(new BigDecimal(ctdh.getGiamgia()))));
                        if (ctdh.getSoluong().equals(ctdhs.getCTDHByIDHHDH(idDH, idHH).getSoluong()))
                            ctdhs.xoaCTDH(ctdh);
                        else {
                            ChiTietDonHang n = ctdh;
                            int slM = Integer.parseInt(slCTDH) - Integer.parseInt(ctdh.getSoluong());
                            n.setSoluong(String.valueOf(slM));
                            ctdhs.suaSoLuongCTDH(n);
                        }
                        
                        
                    }
                    chonCTDH.clear();
                    cnhd.setDonhang_id(idDH);
                    cnhd.setNhanvien_id(nd.getUser_id());
                    cnhd.setNgayCapNhat(ngay);
                    cnhd.setGhichu("Danh sách hàng được đổi:\n".concat(mess) + "Tổng tiền thu khách: " + Utils.moneyBigDecimalFormat(tongTien));
                    cnhds.themCNHD(cnhd);
                    loadDonHang(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                    this.tbDH.getSelectionModel().select(indexDH);
                    loadCTDH(idDH);
                    Utils.getBox(cnhd.getGhichu() + "\nBạn có muốn tạo hóa đơn hay không?",
                            Alert.AlertType.CONFIRMATION).showAndWait().ifPresent(act -> {
                                if (act == ButtonType.OK);
                            });
                    stage.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            btCancel.setOnAction(e -> {
                stage.close();
            });
            
            stage.setTitle("Trả Hàng");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(App.stage);
            stage.showAndWait();
        }
    }
}
