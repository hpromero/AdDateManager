package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import models.Date;
import models.ObjectForList;
import models.QuickWeek;
import models.Settings;
import org.neodatis.odb.OID;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ItemWeekController {
    @FXML private Label l_text1;
    @FXML private Label l_text2;
    @FXML private HBox mainHbox;
    @FXML private HBox day1;
    @FXML private HBox day2;
    @FXML private HBox day3;
    @FXML private HBox day4;
    @FXML private HBox day5;
    @FXML private HBox day6;
    @FXML private HBox day7;
    @FXML private Label lbday1;
    @FXML private Label lbday2;
    @FXML private Label lbday3;
    @FXML private Label lbday4;
    @FXML private Label lbday5;
    @FXML private Label lbday6;
    @FXML private Label lbday7;
    @FXML private Label lbday12;
    @FXML private Label lbday22;
    @FXML private Label lbday32;
    @FXML private Label lbday42;
    @FXML private Label lbday52;
    @FXML private Label lbday62;
    @FXML private Label lbday72;

    private QuickViewController mController;
    private QuickWeek quickWeek = null;

    public void setHeader(int startweekDay, LocalDate startDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        mainHbox.setMargin(l_text1, new Insets(0, 5, 0, 10));
        lbday1.setText(Date.getWeekDayName(startDate.getDayOfWeek().getValue()));
        lbday2.setText(Date.getWeekDayName(startDate.plusDays(1).getDayOfWeek().getValue()));
        lbday3.setText(Date.getWeekDayName(startDate.plusDays(2).getDayOfWeek().getValue()));
        lbday4.setText(Date.getWeekDayName(startDate.plusDays(3).getDayOfWeek().getValue()));
        lbday5.setText(Date.getWeekDayName(startDate.plusDays(4).getDayOfWeek().getValue()));
        lbday6.setText(Date.getWeekDayName(startDate.plusDays(5).getDayOfWeek().getValue()));
        lbday7.setText(Date.getWeekDayName(startDate.plusDays(6).getDayOfWeek().getValue()));
        lbday12.setText(startDate.format(formatter));
        lbday22.setText(startDate.plusDays(1).format(formatter));
        lbday32.setText(startDate.plusDays(2).format(formatter));
        lbday42.setText(startDate.plusDays(3).format(formatter));
        lbday52.setText(startDate.plusDays(4).format(formatter));
        lbday62.setText(startDate.plusDays(5).format(formatter));
        lbday72.setText(startDate.plusDays(6).format(formatter));
    }


    public void setParentController(QuickViewController controller) {
        this.mController = controller;
    }

    public void setData(QuickWeek item) {

        mainHbox.getStyleClass().add("item1");
        mainHbox.setMinHeight(70);

        l_text1.setText(item.getDepartment().getName());
        l_text2.setText(item.getDepartment().getAssignedName());
 //       l_text1.getStyleClass().add("titleDark");
        this.quickWeek = item;

        insertBars(day1,this.quickWeek.getDates1());
        insertBars(day2,this.quickWeek.getDates2());
        insertBars(day3,this.quickWeek.getDates3());
        insertBars(day4,this.quickWeek.getDates4());
        insertBars(day5,this.quickWeek.getDates5());
        insertBars(day6,this.quickWeek.getDates6());
        insertBars(day7,this.quickWeek.getDates7());

 //       vColorBox.setStyle("-fx-background-color: black");
    }

    public void viewDetail() {
 //           mController.openDetail(oid,model,l_text1.getText());
    }


    private void insertBars(HBox day,ArrayList<Date> dates){

        day.getChildren().clear();
        double[] bars2 = QuickWeek.filterByHours(dates);
        for (double bar: bars2){
            double heigth = 60.0*bar/100;
            if (heigth<1){heigth=1;}
            if (heigth>60){heigth=60;}
            String color = selectcolor(heigth);
            Rectangle barImage = new Rectangle(15, heigth);
            barImage.getStyleClass().add("bars");
            barImage.setStyle("-fx-fill: "+color+"; ");
            day.getChildren().add(barImage);
        }
    }

    private String selectcolor(double heigth) {
        int limit = (int)(heigth/10);
        if (limit>5){
            return Settings.getColorBarfull();
        }else if(limit>2){
            return Settings.getColorBarMedium();
        }else if(limit>0){
            return Settings.getColorBarEmpty();
        }
        return Settings.getColorBarNull();
    }


    public void clickday1(){ mController.openDailyView(quickWeek,1); }
    public void clickday2(){ mController.openDailyView(quickWeek,2); }
    public void clickday3(){ mController.openDailyView(quickWeek,3); }
    public void clickday4(){ mController.openDailyView(quickWeek,4); }
    public void clickday5(){ mController.openDailyView(quickWeek,5); }
    public void clickday6(){ mController.openDailyView(quickWeek,6); }
    public void clickday7(){ mController.openDailyView(quickWeek,7); }
    public void clickDepartment(){ mController.openDailyView(quickWeek,0); }





}

