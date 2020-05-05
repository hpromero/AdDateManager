package Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Date;
import models.QuickWeek;
import models.Settings;


import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static java.time.temporal.ChronoUnit.*;


public class DailyViewController {
    @FXML private VBox vbHours;
    @FXML private VBox vbDates;
    @FXML private Label lbTitle;


    private ArrayList<Date> dates;
    private String departmentName;
    private String dayOfWeekName;


    public void setData(QuickWeek week,int day, boolean showDayName) {
        selectDates(week,day);
        listDates();
        if (!showDayName){
            departmentName = week.getDepartment().getName();
            lbTitle.setText(departmentName);
        }

    }

    private void selectDates(QuickWeek week,int day) {

        switch (day){
            case 1:
                this.dates=week.getDates1();
                dayOfWeekName = Date.getWeekDayName(week.getStartDay());
                break;
            case 2:
                this.dates=week.getDates2();
                dayOfWeekName = Date.getWeekDayName(week.getStartDay()+1);
                break;
            case 3:
                this.dates=week.getDates3();
                dayOfWeekName = Date.getWeekDayName(week.getStartDay()+2);
                break;
            case 4:
                this.dates=week.getDates4();
                dayOfWeekName = Date.getWeekDayName(week.getStartDay()+3);
                break;
            case 5:
                this.dates=week.getDates5();
                dayOfWeekName = Date.getWeekDayName(week.getStartDay()+4);
                break;
            case 6:
                this.dates=week.getDates6();
                dayOfWeekName = Date.getWeekDayName(week.getStartDay()+5);
                break;
            case 7:
                this.dates=week.getDates7();
                dayOfWeekName = Date.getWeekDayName(week.getStartDay()+6);
                break;

        }
        lbTitle.setText(dayOfWeekName);
    }

    public void setParentController(QuickViewController quickViewController) { }

    private void listDates(){
        vbHours.getChildren().clear();
        vbDates.getChildren().clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        //TODO: FORMATO 24h
        Collections.sort(this.dates);


        int labelHour = Settings.getStartday().getHour();
        createHourSpace(labelHour,60);
        LocalTime lastFinish = Settings.getStartday();
        for (Date date:this.dates){
             if(date.getStartTime().isBefore(Settings.getStartday())){
                vbHours.getChildren().clear();
                labelHour = date.getStartTime().getHour();
                lastFinish = LocalTime.of(labelHour,00,00);
                createHourSpace(labelHour,60);
            }
             int hour = date.getStartTime().getHour();
             if(hour-labelHour>0){
                 for (int i=1;i<=hour-labelHour;i++){
                     createHourSpace(labelHour+i,60);
                 }
                 labelHour=hour;
             }



            double minutesBlank =MINUTES.between(lastFinish,date.getStartTime());
            int height = (int) MINUTES.between(date.getStartTime(),date.getFinishTime());
            if (height<60){
                createBlankHourSpace(60-height);
                height =60;
            }
            if (minutesBlank>0){
                createBlankDateSpace((int)minutesBlank,date.getWeekDay(),date.getDate(),lastFinish.plusMinutes(5),date.getStartTime().minusMinutes(5));
                //TODO: -----------------HORA----TEST?--------------------
            }
            createDateSpace(date.getStartTime().format(formatter),date.getFinishTime().format(formatter),date.getCustomerName(),height);
     //       createBlankHourSpace(height);
            lastFinish = date.getFinishTime();
        }

 //       int hour = lastFinish.getHour();
        int hour = Settings.getEndday().getHour();
        if(hour-labelHour>0){
            for (int i=1;i<=hour-labelHour;i++){
                createHourSpace(labelHour+i,60);
                ;
            }
        }
        double minutesBlank =MINUTES.between(lastFinish,Settings.getEndday());
  //      createBlankDateSpace((int)minutesBlank,dayOfWeekName,newDate,start,end);
        //TODO: -----------------HORA------------------------
    }

    private void setHours(){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        vbHours.getChildren().clear();
        int horaIncio = Settings.getStartday().getHour();
        int horas = (int) HOURS.between(Settings.getStartday(),Settings.getEndday());
        System.out.println(horas+"numero de horas del dia");
        for (int i=0; i<=horas; i++){
            createHourSpace(horaIncio+i,60);
        }
    }
    private void createHourSpace(int hour,int height){
        Label label = new Label(String.valueOf(hour)+":00");
        label.setStyle("-fx-text-fill : #95a5a6;");
        Pane space = new Pane(label);
   //     AnchorPane.setTopAnchor(label, 0.0);
   //     AnchorPane.setLeftAnchor(label, 5.0);
        space.setPrefSize(40,height);
        space.setStyle("-fx-border-width:1 0 0 0 ;\n"+
                "    -fx-border-color: #bdc3c7;");
        vbHours.getChildren().add(space);
    }
 /*   private void createHourSpace(String hour){
        Label label = new Label(hour);
        AnchorPane space = new AnchorPane(label);
        AnchorPane.setTopAnchor(label, 0.0);
        AnchorPane.setLeftAnchor(label, 5.0);
        space.setPrefSize(25,60);
        space.setStyle("-fx-border-width: 0 1 1 ;\n" +
                "    -fx-border-color: #ABB2B9;");
        vbHours.getChildren().add(space);
    }


  */


    private void createBlankHourSpace(int minutes){
        AnchorPane space = new AnchorPane();
        space.setMinSize(25,minutes);
//        space.setStyle("-fx-border-width: 0 1 1 ;\n" +
//                "    -fx-border-color: #ABB2B9;");
        vbHours.getChildren().add(space);
    }

    private void createDateSpace(String startHour,String endHour,String customerName,int height){
        Label label = new Label("("+startHour+"/");
        Label label2 = new Label(endHour+")");
        Label label3 = new Label(customerName);
        HBox hb = new HBox(label,label2);
        VBox vb= new VBox(label3,hb);
        AnchorPane space = new AnchorPane(vb);
        AnchorPane.setTopAnchor(label, 0.0);
        AnchorPane.setLeftAnchor(label, 5.0);
        space.setPrefSize(250,height);
        space.getStyleClass().add("dailyPane");
  //      space.setStyle("-fx-border-width: 1 1 1 1;\n" +
  //              "    -fx-border-color: #ABB2B9;");
        vbDates.getChildren().add(space);


    }
    private void createBlankDateSpace(int height,String weekDay, LocalDate newDate, LocalTime start, LocalTime end){
        AnchorPane space= new AnchorPane();

        if (height>=30){
            Image img = new Image(getClass().getResourceAsStream("../resources/images/calendarAdd64.png"));
            ImageView imageView= new ImageView(img);
            imageView.setFitHeight(15);
            imageView.setPreserveRatio(true);
            Button addButton = new Button();
            addButton.setGraphic(imageView);
            addButton.getStyleClass().add("addDateButton");
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    newDate(weekDay,newDate,start,end);
                }
            });
            VBox vb= new VBox(addButton);
            vb.setAlignment(Pos.CENTER);
            space.getChildren().addAll(vb);
            AnchorPane.setLeftAnchor(vb, 0.0);
            AnchorPane.setRightAnchor(vb, 0.0);
            AnchorPane.setTopAnchor(vb, 0.0);
            AnchorPane.setBottomAnchor(vb, 0.0);
            if (height<40){
                createBlankHourSpace(40-height);
                height=40;
            }
        }
        space.setPrefSize(250,height);
        vbDates.getChildren().add(space);
    }


    public void newDate(String weekDay, LocalDate newDate, LocalTime start, LocalTime end) {
        try {
            final Stage secondStage = new Stage();
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DetailDate.fxml"));
            Parent root =  loader.load();
            DetailDateController controller = loader.getController();
            controller.addDateFromDaily(weekDay,newDate,start,end);
            //TODO: meter fecha y hora
            secondStage.setTitle("Añadir cita");
            Scene scene = new Scene(root,450,300);
   //         Scene scene = new Scene(root);
            secondStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());
            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
