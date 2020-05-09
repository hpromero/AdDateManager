package Controllers;


import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Date;
import models.QuickWeek;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;


public class QuickViewController {

    @FXML private VBox vbContent = null;
    @FXML private Label lbSubTitle;
    @FXML private Label lbTitle;
    @FXML private Label lbWeekNumber;
    @FXML private Button btnLeft;
    @FXML private Button btnRight;
    @FXML private VBox quickWeekControls;
    @FXML private JFXDatePicker dpStartDate;
    private ArrayList<QuickWeek> quickWeeks;
    private LocalDate startDate;
    private int dayOffSet = 0;


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnLeft) {
            int dayOfWeek = startDate.getDayOfWeek().getValue();
            if (dayOfWeek > 1){
                dayOffSet = dayOffSet - (dayOfWeek-1);
            }else{
                dayOffSet = dayOffSet - 7;
            }
            openList();
        }
        if (actionEvent.getSource() == btnRight) {
            int dayOfWeek = startDate.getDayOfWeek().getValue();
            if (dayOfWeek > 1){
                dayOffSet = dayOffSet + (8-dayOfWeek);
            }else{
                dayOffSet = dayOffSet + 7;
            }
            openList();
        }
        if (actionEvent.getSource() == dpStartDate) {
            dayOffSet = (int)DAYS.between(LocalDate.now(),dpStartDate.getValue());
            openList();
        }

    }






    public void openList(){
        quickWeekControls.setVisible(true);
        quickWeekControls.setManaged(true);
        this.quickWeeks = QuickWeek.getQuickWeekList(dayOffSet);

        if (quickWeeks.size()>0) {

            startDate = quickWeeks.get(0).getStartDate();
            dpStartDate.setValue(startDate);
            WeekFields weekFields = WeekFields.ISO.of(Locale.getDefault());
            int weekNumber = startDate.get(weekFields.weekOfWeekBasedYear());
            lbWeekNumber.setText("Semana nº " + weekNumber);

            vbContent.getChildren().clear();
            VBox vb = new VBox();
            vb.setPadding(new Insets(30, 0, 0, 0));
            vbContent.getChildren().add(vb);
            lbSubTitle.setText("Vista semana");
            lbTitle.setText("Planificador");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ItemWeek.fxml"));
                Node header = loader.load();
                ItemWeekController controller = loader.getController();
                QuickWeek object = (QuickWeek) quickWeeks.get(0);
                controller.setHeader(object.getStartDay(), object.getStartDate());
                controller.setParentController(this);
                vbContent.getChildren().add(header);
            } catch (IOException e) {
                e.printStackTrace();
            }


            Node[] nodes = new Node[quickWeeks.size()];
            for (int i = 0; i < nodes.length; i++) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ItemWeek.fxml"));
                    nodes[i] = loader.load();
                    ItemWeekController controller = loader.getController();
                    QuickWeek object = (QuickWeek) quickWeeks.get(i);
                    controller.setData(object);
                    controller.setParentController(this);
                    vbContent.getChildren().add(nodes[i]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    public void openDailyView(QuickWeek quickWeekSelected, int day){
        quickWeekControls.setVisible(false);
        quickWeekControls.setManaged(false);
        vbContent.getChildren().clear();
        HBox hb = new HBox();
        hb.setPadding(new Insets(30, 0, 0, 0));
        hb.setSpacing(30);
        vbContent.getChildren().add(hb);

        if (quickWeekSelected==null && day!=0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            LocalDate dayChoosed = startDate.plusDays(day-1);
            String dayNameChoosed= Date.getWeekDayName(dayChoosed.getDayOfWeek().getValue());
            lbTitle.setText(dayNameChoosed+" "+dayChoosed.format(formatter));
            lbSubTitle.setText("Agenda por departamentos");
            Node[] nodes = new Node[quickWeeks.size()];
            for (int i = 0; i < nodes.length; i++) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DailyView.fxml"));
                    nodes[i] = loader.load();
                    DailyViewController controller = loader.getController();
                    QuickWeek object = (QuickWeek) quickWeeks.get(i);
                    controller.setData(object, day,false);
                    controller.setParentController(this);
                    hb.getChildren().add(nodes[i]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (quickWeekSelected!=null && day!=0){
            String department = quickWeekSelected.getDepartment().getName();
            lbTitle.setText(department);
            lbSubTitle.setText("Agenda del día");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DailyView.fxml"));
                Node node = loader.load();
                DailyViewController controller = loader.getController();
                controller.setData(quickWeekSelected, day,true);
                controller.setParentController(this);
                hb.getChildren().add(node);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (quickWeekSelected!=null && day==0){
            String department = quickWeekSelected.getDepartment().getName();
            lbTitle.setText(department);
            lbSubTitle.setText("Agenda semanal");
            for (int i=1;i<=7;i++){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DailyView.fxml"));
                    Node node = loader.load();
                    DailyViewController controller = loader.getController();
                    controller.setData(quickWeekSelected, i,true);
                    controller.setParentController(this);
                    hb.getChildren().add(node);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}