/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import com.doannganh.pojo.ChiTietDonHang;
import com.doannganh.pojo.DonHang;
import com.doannganh.pojo.HangHoa;
import com.doannganh.pojo.User;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
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
public class TaoHoaDonController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private VBox vb;
    @FXML private TableView<HangHoa> tbHangHoa;
    @FXML private TableView<ChiTietDonHang> tbDonHang;
    @FXML private TextField txtTraCuu;
    @FXML private ComboBox<String> cbTraCuu;
    @FXML private ImageView ivHangHoa;
    @FXML private TextField txtTongTien;
    
    User nd = DangnhapController.nd;
    ObservableList<String> list = FXCollections.observableArrayList
        ("Mã hàng", "Tên hàng", "Thương hiệu", "Loại hàng");
    ObservableList listTH;
    ObservableList<ChiTietDonHang> h = FXCollections.observableArrayList();
    int index = -1;
    BigDecimal tongTien = BigDecimal.ZERO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTraCuu.setItems(list);
        this.cbTraCuu.getSelectionModel().selectFirst();
        this.txtTongTien.setText(Utils.moneyBigDecimalFormat(tongTien));
        
        loadTable();
        loadHangHoa("", this.cbTraCuu.getSelectionModel().getSelectedItem());
        loadTableCTDH();
        
        this.txtTraCuu.textProperty().addListener((obj) -> {
            if (this.txtTraCuu.getText().isEmpty()) {
                loadHangHoa("", this.cbTraCuu.getSelectionModel().getSelectedItem());
            }
            else
                loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
        });
        
        /*try {
            Connection conn = JdbcUtils.getConn();
            HangHoaService s = new HangHoaService(conn);
            //String path = s.getHangHoas().get(0).getHinhanh();
            //ImageView imageView = Utils.setImageView(path);
            //vb.getChildren().add(imageView);
            for (int i = 0; i < s.getHangHoas().size();i++) {
                String path = s.getHangHoas().get(i).getHinhanh();
                Image img = new Image(getClass().getResourceAsStream(path));
                ImageView imageView = new ImageView(img);
                imageView.setFitHeight(442);
                vb.getChildren().add(i, imageView);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaoHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        this.tbHangHoa.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(evt -> {
                HangHoa hh = this.tbHangHoa.getSelectionModel().getSelectedItem();
                int rindex = this.tbHangHoa.getSelectionModel().getSelectedIndex();
                boolean trung = false;
                for (int i = 0; i < this.tbDonHang.getItems().size(); i++) {
                    if (hh.getHanghoa_id() == this.tbDonHang.getItems().get(i).getHanghoa_id()) {
                        trung = true;
                        break;
                    }
                    else
                        trung = false;
                }
                if (!trung){
                    try {
                        Connection conn;
                        conn = JdbcUtils.getConn();
                        HangHoaService s = new HangHoaService(conn);
                        if (Integer.parseInt(hh.getSoluongtrongkho()) > 0) {
                            hh.setGianiemyet(Utils.moneyStringFormat(hh.getGianiemyet()));
                            loadCTDH(hh, "1");
                        }
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(TaoHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                Image img = new Image(getClass().getResourceAsStream(hh.getHinhanh()));

                ivHangHoa.setImage(img);
                ivHangHoa.setFitHeight(515);
                ivHangHoa.setFitWidth(315);
                
            });
            return r;
        });
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
            Callback<TableColumn<HangHoa, String>, TableCell<HangHoa, String>> cellFactory;
            
            TableColumn<HangHoa, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
            colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));
            
            TableColumn<HangHoa, String> colTenHangHoa = new TableColumn("Tên Hàng");
            colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhanghoa"));
            
            TableColumn<HangHoa, String> colThuongHieu = new TableColumn("Thương Hiệu");
            colThuongHieu.setCellValueFactory(new PropertyValueFactory("thuonghieu"));
            
            TableColumn<HangHoa, String> colSoLuong = new TableColumn("Số Lượng");
            colSoLuong.setCellValueFactory(new PropertyValueFactory("soluongtrongkho"));
            colSoLuong.setStyle( "-fx-alignment: CENTER-RIGHT;");
            
            TableColumn<HangHoa, String> colGiaNiemYet = new TableColumn("Giá Niêm Yết");
            colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("gianiemyet"));
            colGiaNiemYet.setStyle( "-fx-alignment: CENTER-RIGHT;");
            
            TableColumn<HangHoa, String> colNgaySanXuat = new TableColumn("Ngày Sản Xuất");
            colNgaySanXuat.setCellValueFactory(new PropertyValueFactory("ngaysanxuat"));
            
            TableColumn<HangHoa, String> colNgayHetHan = new TableColumn("Ngày Hết Hạn");
            colNgayHetHan.setCellValueFactory(new PropertyValueFactory("ngayhethan"));
            
            TableColumn<HangHoa, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
            colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("tenloaihang"));
            
            colGiaNiemYet.setCellFactory(tc -> new TableCell<HangHoa, String>() {
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
            
            /*cellFactory = (TableColumn<HangHoa, String> param) -> {
                HBox box= new HBox();
                ImageView imageview = new ImageView();
                return new TableCell<HangHoa, String> () {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        //  super.updateItem(item, empty);
                        if (item != null) {
                            Image img = null;
                            HangHoa hh = getTableView().getItems().get(getIndex());
                            img = new Image(getClass().getResourceAsStream(hh.getHinhanh()));
                            
                            imageview.setImage(img);
                            imageview.setFitHeight(150.0);
                            imageview.setFitWidth(75.0);
                            
                        }
                        if(!box.getChildren().contains(imageview)) {
                            box.getChildren().add(imageview);
                            setGraphic(box);
                        }
                    }
                };
            };
            colHinhAnh.setCellFactory(cellFactory);*/
            
            this.tbHangHoa.getColumns().addAll(colMaHangHoa, colTenHangHoa
                    , colThuongHieu, colSoLuong, colGiaNiemYet
                    , colNgaySanXuat, colNgayHetHan, colLoaiHangHoa);
        } catch (SQLException ex) {
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadCTDH(HangHoa hh, String sl){
        try {
            Connection conn = JdbcUtils.getConn();
            //LoaiHangHoaService s = new LoaiHangHoaService(conn);
            ChiTietDonHang ctdh = new ChiTietDonHang();
            ctdh.setHanghoa_id(hh.getHanghoa_id());
            ctdh.setTenhang(hh.getTenhanghoa());
            ctdh.setLoaihang(hh.getTenloaihang());
            ctdh.setDongia(hh.getGianiemyet());
            ctdh.setSoluong(sl);
            ctdh.setHinhanh(hh.getHinhanh());
            ctdh.setGiamgia(String.valueOf(0));
            BigDecimal dg = new BigDecimal(hh.getGianiemyet());
            BigDecimal slg = new BigDecimal(sl);
            BigDecimal gg = new BigDecimal(0);
            BigDecimal tt = dg.multiply(slg).multiply(BigDecimal.ONE.subtract(gg));
            BigDecimal tong = tongTien;
            tongTien = tong.add(tt);
            ctdh.setThanhtien(tt.toString());
            h.add(ctdh);
            this.txtTongTien.setText(Utils.moneyBigDecimalFormat(tongTien));
            //ObservableList<ObservableList> h = FXCollections.observableArrayList();
            //(new Object(hh.getHanghoa_id(), hh.getTenhanghoa(), s.getLoaiHHByid(hh.getLoaihanghoa_id()), "", "0", hh.getGianiemyet(), "0"));
            /*ObservableList row = FXCollections.observableArrayList();
            row.add(hh.getHanghoa_id());
            row.add(hh.getTenhanghoa());
            row.add(s.getLoaiHHByid(hh.getLoaihanghoa_id()));
            row.add("");
            row.add(sl);
            row.add(hh.getGianiemyet());
            row.add("0");
            h.add(row);*/
           
            this.tbDonHang.setItems(h);
            //this.tbDonHang.getItems().add(h);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TraCuuHangHoaThuKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadTableCTDH(){
        this.tbDonHang.setEditable(true);
        Callback<TableColumn<ChiTietDonHang, String>, TableCell<ChiTietDonHang, String>> cellFactory;

        TableColumn<ChiTietDonHang, Integer> colMaHangHoa = new TableColumn("Mã Hàng");
        colMaHangHoa.setCellValueFactory(new PropertyValueFactory("hanghoa_id"));

        TableColumn<ChiTietDonHang, String> colTenHangHoa = new TableColumn("Tên Hàng");
        colTenHangHoa.setCellValueFactory(new PropertyValueFactory("tenhang"));

        TableColumn<ChiTietDonHang, String> colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soluong"));
        colSoLuong.setStyle( "-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, String> colGiaNiemYet = new TableColumn("Đơn Giá");
        colGiaNiemYet.setCellValueFactory(new PropertyValueFactory("dongia"));
        colGiaNiemYet.setStyle( "-fx-alignment: CENTER-RIGHT;");

        TableColumn<ChiTietDonHang, String> colHinhAnh = new TableColumn("Hình Ảnh");
        colHinhAnh.setCellValueFactory(new PropertyValueFactory("hinhanh"));

        TableColumn<ChiTietDonHang, String> colLoaiHangHoa = new TableColumn("Loại Hàng Hóa");
        colLoaiHangHoa.setCellValueFactory(new PropertyValueFactory("loaihang"));
        
        TableColumn<ChiTietDonHang, String> colGiamGia = new TableColumn("Giảm Giá");
        colGiamGia.setCellValueFactory(new PropertyValueFactory("giamgia"));
        colGiamGia.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        TableColumn<ChiTietDonHang, String> colThanhTien = new TableColumn("Thành Tiền");
        colThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        colThanhTien.setStyle( "-fx-alignment: CENTER-RIGHT;");

        TableColumn colAction = new TableColumn();
        colAction.setCellFactory((obj) -> {
            Button btn = new Button("Xóa");
            btn.setOnAction(evt -> {
                TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                ChiTietDonHang ctdh = (ChiTietDonHang) cell.getTableRow().getItem();
                index = cell.getTableRow().getIndex();
                if (!cell.isEmpty()) {
                    BigDecimal dg = new BigDecimal(ctdh.getDongia());
                    BigDecimal slg = new BigDecimal(ctdh.getSoluong());
                    BigDecimal gg = new BigDecimal(ctdh.getGiamgia());
                    BigDecimal tt = dg.multiply(slg).multiply(BigDecimal.ONE.subtract(gg));
                    BigDecimal tong = tongTien;
                    tongTien = tong.subtract(tt);
                    this.txtTongTien.setText(Utils.moneyBigDecimalFormat(tongTien));
                    this.tbDonHang.getItems().remove(index);
                }
                //int sl = Integer.parseInt(ctdh.getSoluong());
                //int slKho = Integer.parseInt(s.getSoLuongByIDHH(ctdh.getHanghoa_id()));
                //s.suaSoLuong(ctdh.getHanghoa_id(), String.valueOf(sl + slKho));
                //this.tbHangHoa.getRowFactory().
                //loadHangHoa(txtTraCuu.getText(), cbTraCuu.getSelectionModel().getSelectedItem());
            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        colSoLuong.setCellFactory(TextFieldTableCell.forTableColumn());
        colSoLuong.setOnEditCommit((var evt) -> {
            try {
                Connection conn;
                conn = JdbcUtils.getConn();
                HangHoaService s = new HangHoaService(conn);
                ChiTietDonHang ctdh = evt.getRowValue();
                int rindex = this.tbDonHang.getSelectionModel().getSelectedIndex();
                HangHoa hh;
                String c = ctdh.getSoluong();
                String m = "";
                String sl = s.getSoLuongByIDHH(ctdh.getHanghoa_id());
                if ("".equals(evt.getNewValue())) {
                    ctdh.setSoluong(c);
                    Utils.getBox("Vui lòng không để trống!", Alert.AlertType.WARNING).show();
                } else {
                    m = evt.getNewValue();
                    ctdh.setSoluong(m);
                    if (!m.matches("-?\\d+")) {
                        ctdh.setSoluong(c);
                        Utils.getBox("Vui lòng chỉ nhập số!", Alert.AlertType.WARNING).show();
                    } else if (m.equals(c)) {
                        ctdh.setSoluong(c);
                        Utils.getBox("Vui lòng thay đổi số lượng để cập nhật!", Alert.AlertType.WARNING).show();
                    } else {
                        if (Integer.parseInt(m) > Integer.parseInt(sl)) {
                            ctdh.setSoluong(c);
                            Utils.getBox("Vui lòng nhập số lượng <= " + sl, Alert.AlertType.WARNING).show();
                        } else if (Integer.parseInt(m) <= 0) {
                            ctdh.setSoluong(c);
                            Utils.getBox("Vui lòng nhập số lượng > 0", Alert.AlertType.WARNING).show();
                        } else {
                            //int n = (Integer.parseInt(c) - Integer.parseInt(m)) + Integer.parseInt(sl);
                            //s.suaSoLuong(ctdh.getHanghoa_id(), String.valueOf(n));
                            //hh = s.getHangHoaByID(ctdh.getHanghoa_id());
                            BigDecimal dg = new BigDecimal(ctdh.getDongia());
                            BigDecimal slc = new BigDecimal(c);
                            BigDecimal slm = new BigDecimal(m);
                            BigDecimal gg = new BigDecimal(ctdh.getGiamgia());
                            BigDecimal ttc = dg.multiply(slc).multiply(BigDecimal.ONE.subtract(gg));
                            BigDecimal tong = tongTien;
                            tongTien = tong.subtract(ttc);
                            BigDecimal ttm = dg.multiply(slm).multiply(BigDecimal.ONE.subtract(gg));
                            tong = tongTien;
                            tongTien = tong.add(ttm);
                            ctdh.setThanhtien(ttm.toString());
                            this.txtTongTien.setText(Utils.moneyBigDecimalFormat(tongTien));
                            this.tbDonHang.getItems().set(rindex, ctdh);
                            Utils.getBox("Cập nhật số lượng thành công!", Alert.AlertType.INFORMATION).show();
                            //loadHangHoa(txtTraCuu.getText(), cbTraCuu.getSelecctionModel().getSelectedItem());
                            //loadCTDH(hh, String.valueOf(n));
                        }
                    }
                }
                this.tbDonHang.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(TaoHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        colGiaNiemYet.setCellFactory(tc -> new TableCell<ChiTietDonHang, String>() {
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        setText(Utils.moneyBigDecimalFormat(new BigDecimal(value)));
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
                        setText(Utils.moneyBigDecimalFormat(new BigDecimal(value)));
                    }
                }
            });

        /*colSoLuong.setCellFactory((TableColumn<ChiTietDonHang, String> param) -> {
            TableCell<ChiTietDonHang, String> cell = new TableCell<ChiTietDonHang, String>() {

                private final Spinner<Integer> count;

                private final SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory;
                private final ChangeListener<Number> valueChangeListener;

                {
                    valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
                    count = new Spinner<>(valueFactory);
                    count.setVisible(false);
                    setGraphic(count);
                    valueChangeListener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                        valueFactory.setValue(newValue.intValue());
                    };
                    count.valueProperty().addListener((obs, oldValue, newValue) -> {
                        if (getItem() != null) {
                            // write new value to table item
                            getItem().se(newValue);
                        }
                    });
                }

                @Override
                public void updateItem(ChiTietDonHang item, boolean empty) {

                    // unbind old values
                    valueFactory.maxProperty().unbind();
                    if (getItem() != null) {
                        getItem().itemCountProperty().removeListener(valueChangeListener);
                    }

                    super.updateItem(item, empty);

                    // update according to new item
                    if (empty || item == null) {
                        count.setVisible(false);
                    } else {
                        valueFactory.maxProperty().bind(item.itemMaxCountProperty());
                        valueFactory.setValue(item.getItemCount());
                        item.itemCountProperty().addListener(valueChangeListener);
                        count.setVisible(true);
                    }

                }
            };

            return cell;
        });*/

        cellFactory = (TableColumn<ChiTietDonHang, String> param) -> {
            HBox hbox= new HBox();
            ImageView imageview = new ImageView();
            return new TableCell<ChiTietDonHang, String> () {
                @Override
                public void updateItem(String item, boolean empty) {
                    //  super.updateItem(item, empty);
                    if (item != null) {
                        Image img = null;
                        ChiTietDonHang ctdh = getTableView().getItems().get(getIndex());
                        img = new Image(getClass().getResourceAsStream(ctdh.getHinhanh()));

                        imageview.setImage(img);
                        imageview.setFitHeight(100);
                        imageview.setFitWidth(100);

                    }
                    if(!hbox.getChildren().contains(imageview)) {
                        hbox.getChildren().add(imageview);
                        setGraphic(hbox);
                    }
                }
            };
        };
        colHinhAnh.setCellFactory(cellFactory);

        this.tbDonHang.getColumns().addAll(colMaHangHoa, colTenHangHoa
                , colLoaiHangHoa, colGiaNiemYet
                , colSoLuong, colGiamGia, colThanhTien, colAction);
    }
    
    public void taoHoaDon(ActionEvent evt) throws IOException {
        if (this.tbDonHang.getItems().isEmpty())
            Utils.getBox("Vui lòng chọn các hàng hóa để tạo đơn hàng!!!", Alert.AlertType.WARNING).show();
        else {
            try {
                Connection conn = JdbcUtils.getConn();
                DonHangService dhs = new DonHangService(conn);
                ChiTietDonHangService ctdhs = new ChiTietDonHangService(conn);
                HangHoaService hhs = new HangHoaService(conn);
                DonHang dh = new DonHang();
                
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String ngay = simpleformat.format(cal.getTime());
                dh.setNgaytaodh(ngay);
                dh.setNhanvien_id(nd.getUser_id());
                int id = dhs.tongDH() + 1;
                dh.setDonhang_id(id);
                dh.setKhachhang_id(1);
                if(dhs.themDH(dh)) {
                    for (int i = 0; i < this.tbDonHang.getItems().size(); i++) {
                        ChiTietDonHang ctdh = this.tbDonHang.getItems().get(i);
                        ctdh.setDonhang_id(id);
                        int sltk = Integer.parseInt(hhs.getSoLuongByIDHH(ctdh.getHanghoa_id()));
                        if (sltk > 0) {
                            ctdh.setDongia(Utils.moneyStringFormat(ctdh.getDongia()));
                            ctdhs.themCTDH(ctdh);
                            int sl = sltk - Integer.parseInt(ctdh.getSoluong());
                            hhs.suaSoLuong(ctdh.getHanghoa_id(), String.valueOf(sl));
                        }
                    }
                    Utils.getBox("Tạo đơn hàng thành công!\nBạn có muốn tạo hóa đơn hay không?",
                            Alert.AlertType.CONFIRMATION).showAndWait().ifPresent(act -> {
                                if (act == ButtonType.OK)
                                    inHoaDon(dh);
                            });
                    loadHangHoa(this.txtTraCuu.getText(), this.cbTraCuu.getSelectionModel().getSelectedItem());
                    this.tbDonHang.getItems().clear();
                    tongTien = BigDecimal.ZERO;
                    this.txtTongTien.setText(Utils.moneyBigDecimalFormat(tongTien));
                }
            } catch (SQLException ex) {
                Logger.getLogger(TaoHoaDonController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
            
    }
    
    public void clearHandler(ActionEvent evt) throws IOException {
        if (!this.txtTraCuu.getText().isEmpty())
            this.txtTraCuu.setText("");
        
    }
    
    public void inHoaDon(DonHang dh){
        try {
            Connection conn = JdbcUtils.getConn();
            UserService s = new UserService(conn);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/qldvvpkcm", "root", "123456");
            String reportPath = "/report/HoaDon.jrxml";
            Map<String, Object> params = new HashMap<>();  
            params.put("donhang_id", dh.getDonhang_id());  
            params.put("ngayTaoDH", dh.getNgaytaodh());
            params.put("hoten", s.getUserByID(dh.getNhanvien_id()).getHoten());
            JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(reportPath));
            JasperPrint jp = JasperFillManager.fillReport(jr,params, con);
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
}