package main;


import Controllers.QuickViewController;
import Controllers.SectionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import models.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;


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
    private static String sessionRol = "User";
    private static String sessionName = "";
    Path bbddPath = Paths.get("neodatis.bbdd");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

 /*

        //Bloqueador de loging---------------------
        session = true;
        sessionRol = "Admin";
        // ---------------Eliminar------------------

*/

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

            if(confirmationBox("Cargar Datos de prueba","¿Desea cargar nuevos datos de prueba?\nesto borrá toda la base de datos")){

                try {
                    Files.delete(bbddPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InitialData.loadExampleData();

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
                controller.setInitialData(title,objectList,model);
                mainPane.setContent(node);
            }
        }
        catch(IOException iex)
        { }
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
        { }
    }

    public  void sessionStart(){
        User userLoged = BBDD.checkUserPasword(tfName.getText(),pfPassword.getText());
       if (userLoged!= null){
           session = true;
           sessionRol = userLoged.getRol();
           sessionName = userLoged.getName();
           sessionLabel.setText("Usuario Logeado");
           openQuickView();
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


    public static boolean confirmationBox(String titleBar, String headerMessage) {
        Alert alert = new Alert(CONFIRMATION,headerMessage,ButtonType.YES,ButtonType.CANCEL);
        alert.setTitle(titleBar);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return true;
        }
        return false;
    }

}