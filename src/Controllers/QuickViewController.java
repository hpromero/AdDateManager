package Controllers;


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
import models.ObjectForList;
import models.QuickWeek;
import org.neodatis.odb.OID;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class QuickViewController {

    @FXML private VBox vbContent = null;
    @FXML private Label lbSubTitle;
    @FXML private Label lbTitle;
    @FXML private Button btnAdd;
    private String model;
    private ArrayList<QuickWeek> quickWeeks;


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnAdd) {
      //      openDetail(null,this.model,"Nuevo");
        }
    }




    public void openList(){
        this.quickWeeks = QuickWeek.getQuickWeekList();
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
        controller.setHeader(object.getStartDay(),object.getStartDate());
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

    public void openDaily(int day){
        ArrayList<QuickWeek> quickWeeks = QuickWeek.getQuickWeekList();
        vbContent.getChildren().clear();
        HBox hb = new HBox();
        hb.setPadding(new Insets(30, 0, 0, 0));
        hb.setSpacing(30);
        vbContent.getChildren().add(hb);
        lbSubTitle.setText("Prueba DailyView");
        lbTitle.setText("Dia de la semana");


        Node[] nodes = new Node[quickWeeks.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DailyView.fxml"));
                nodes[i] = loader.load();
                DailyViewController controller = loader.getController();
                QuickWeek object = (QuickWeek) quickWeeks.get(i);
                controller.setData(object,day);
                controller.setParentController(this);
                hb.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void openDailyView(QuickWeek quickWeekSelected, int day){
        vbContent.getChildren().clear();
        HBox hb = new HBox();
        hb.setPadding(new Insets(30, 0, 0, 0));
        hb.setSpacing(30);
        vbContent.getChildren().add(hb);
        lbSubTitle.setText("Prueba DailyView");
        lbTitle.setText("Dia de la semana");

        if (quickWeekSelected==null && day!=0) {
            Node[] nodes = new Node[quickWeeks.size()];
            for (int i = 0; i < nodes.length; i++) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DailyView.fxml"));
                    nodes[i] = loader.load();
                    DailyViewController controller = loader.getController();
                    QuickWeek object = (QuickWeek) quickWeeks.get(i);
                    controller.setData(object, day);
                    controller.setParentController(this);
                    hb.getChildren().add(nodes[i]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (quickWeekSelected!=null && day!=0){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DailyView.fxml"));
                Node node = loader.load();
                DailyViewController controller = loader.getController();
                controller.setData(quickWeekSelected, day);
                controller.setParentController(this);
                hb.getChildren().add(node);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (quickWeekSelected!=null && day==0){
            for (int i=1;i<=7;i++){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DailyView.fxml"));
                    Node node = loader.load();
                    DailyViewController controller = loader.getController();
                    controller.setData(quickWeekSelected, i);
                    controller.setParentController(this);
                    hb.getChildren().add(node);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


/*
    public void openUserDetail(OID oid){
        vbContent.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/DetailUser.fxml"));
            Node node = loader.load();
            DetailUserController controller = loader.getController();
            controller.open(oid);
            controller.setParentController(this);
            vbContent.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/

}