package Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import models.Date;
import models.QuickWeek;
import models.Settings;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static java.time.temporal.ChronoUnit.*;


public class DailyViewController {
    @FXML private VBox vbHours;
    @FXML private VBox vbDates;


    private ArrayList<Date> dates;
    private String departmentName;


    public void setData(QuickWeek week,int day) {
        //TODO: traer solo array dates y nombre del departamento y borrar metodo de seleción
            selectDates(week,day);
        //------------


        listDates();
  //      setHours();
    }

    private void selectDates(QuickWeek week,int day) {
        departmentName = week.getDepartment().getName();
        switch (day){
            case 1:
                this.dates=week.getDates1();
                break;
            case 2:
                this.dates=week.getDates2();
                break;
            case 3:
                this.dates=week.getDates3();
                break;
            case 4:
                this.dates=week.getDates4();
                break;
            case 5:
                this.dates=week.getDates5();
                break;
            case 6:
                this.dates=week.getDates6();
                break;
            case 7:
                this.dates=week.getDates7();
                break;

        }
    }

    public void setParentController(QuickViewController quickViewController) { }

    private void listDates(){
        vbHours.getChildren().clear();
        vbDates.getChildren().clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
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
                createBlankDateSpace((int)minutesBlank);
    //            createBlankHourSpace((int)minutesBlank);
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
        createBlankDateSpace((int)minutesBlank);
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
    private void createBlankDateSpace(int height){
        AnchorPane space = new AnchorPane();
        space.setPrefSize(10,height);
        if (height>=30){
 //           space.setStyle("-fx-background-color :  #e67e22;");
        }
        vbDates.getChildren().add(space);


    }

}
