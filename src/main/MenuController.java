package main;


import Controllers.QuickViewController;
import Controllers.SectionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.BBDD;
import models.Date;
import models.InitialData;
import models.ObjectForList;
import org.neodatis.odb.OID;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class MenuController implements Initializable {

    @FXML private Button btnCitas;
    @FXML private Button btnTeam;
    @FXML private Button btnCustomers;
    @FXML private Button btnDates;
    @FXML private Button btnCategories;
    @FXML private Button btnSettings;
    @FXML private Button btnNewDate;
    @FXML private Label sessionLabel = null;
    @FXML private ScrollPane mainPane;
    @FXML private Label lMsg;
    @FXML private TextField tfName;
    @FXML private PasswordField pfPassword;

    private static boolean session = false;
    private static String sessionRol;
    private static String sessionName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Carga incial de datos----------------
 //    InitialData.loadExampleData();
/*
        Date date1 = new Date("Lunes");
        date1.setId();
        BBDD.saveDateByOID(null, date1);
        Date date2 = new Date("MArtes");
        date2.setId();
        BBDD.saveDateByOID(null, date2);
*/
        //-------------Eliminar----------------



        //Bloqueador de loging---------------------
        session = true;
        sessionRol = "Admin";
        sessionName = "Hector";
       // openSectionList("../QuickView/QuickWeek.fxml");
        // ---------------Eliminar------------------



    }



    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            ArrayList<ObjectForList> objectList = BBDD.getCustomerList();
            openSectionList("Clientes",objectList,"Customer");
        }
        if (actionEvent.getSource() == btnTeam) {
            ArrayList<ObjectForList> objectList = BBDD.getUserList();
            openSectionList("Equipo",objectList,"User");
        }
        if (actionEvent.getSource() == btnCitas) {
            openQuickView();
        }
        if(actionEvent.getSource()== btnDates)
        {
            ArrayList<ObjectForList> objectList = BBDD.getDateList();
            openSectionList("Citas",objectList,"Date");

        }
        if(actionEvent.getSource()==btnCategories)
        {
            ArrayList<ObjectForList> objectList = BBDD.getDepartmentList();
            openSectionList("Departamentos",objectList,"Department");

        }
        if(actionEvent.getSource()==btnSettings){

        }
        if(actionEvent.getSource()==btnNewDate)
        {
            try {
                showsearch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void openSectionList(String title, ArrayList<ObjectForList> objectList, String model){
        try {
            if (session==false){
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Section.fxml"));
                Node node = loader.load();
                SectionController controller = loader.getController();
                controller.openList(title,objectList,model);
                mainPane.setContent(node);
            }
        }
        catch(IOException iex)
        {
            System.out.println("!Error al cargar Ventana!");
            //TODO: z Delete Flag
        }
    }

    public void openQuickView(){
        try {
            if (session==false){
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/QuickView.fxml"));
                Node node = loader.load();
                QuickViewController controller = loader.getController();
                controller.openList();
                mainPane.setContent(node);
            }
        }
        catch(IOException iex)
        {
            System.out.println("!Error al cargar Ventana!");
            //TODO: z Delete Flag
        }
    }

    public  void sessionStart(){

       if (BBDD.checkUserPasword(tfName.getText(),pfPassword.getText())){
            session = true;
            sessionLabel.setText("Usuario Logeado");
        //    openSectionList("../QuickView/QuickWeek.fxml","",null);
        }else{
           lMsg.setText("Ususario o contraseña erroneos");
       }

    }

    public static boolean isAdmin(){
        return (sessionRol.equals("Admin"));
    }
    public static boolean isSameUser(String name){
        return (sessionName.equals(name));
    }



   public void showsearch() throws IOException {
       final Stage secondStage = new Stage();
       secondStage.initModality(Modality.APPLICATION_MODAL);
       secondStage.setResizable(false);
       Parent root = FXMLLoader.load(getClass().getResource("../Views/SearchUser.fxml"));
       secondStage.setTitle("Buscar Usuario");
       Scene scene = new Scene(root,500,600);
       secondStage.setScene(scene);
       scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());
       secondStage.show();
   }



 /*

    public void popup() {
        Stage secondStage = new Stage();
        secondStage.setTitle("Popup Example");
        final Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);
        popup.getContent().addAll(new Circle(25, 25, 50, Color.AQUAMARINE));

        Button show = new Button("Show");
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.show(secondStage);
            }
        });

        Button hide = new Button("Hide");
        hide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();
            }
        });

        HBox layout = new HBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        layout.getChildren().addAll(show, hide);
        secondStage.setScene(new Scene(layout));
        secondStage.show();
    }

  */

}