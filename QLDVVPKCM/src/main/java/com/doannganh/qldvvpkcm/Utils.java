/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doannganh.qldvvpkcm;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class Utils {
    public static Alert getBox(String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(content);

        return alert;
    }
    
    public static ImageView setImageView(String path) {
        InputStream is = Utils.class.getResourceAsStream(path);
        ImageView iv = new ImageView(new Image(is));
        iv.setFitWidth(100);
        iv.setFitHeight(100);
        return iv;
    }
    
    public static String moneyBigDecimalFormat(BigDecimal money){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return(formatter.format(money)+" VNĐ");
    }
    
    public static String moneyStringFormat(String money){
        money.replace(",", "").replace(" VNĐ", "");
        return money;
    }
        
}
