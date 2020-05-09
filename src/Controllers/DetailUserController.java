package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MenuController;
import models.BBDD;
import models.ObjectForList;
import models.User;
import org.neodatis.odb.OID;

import java.util.ArrayList;


public class DetailUserController {
    @FXML private Button btnUser;
    @FXML private Button btnUser2;
    @FXML private TextField tfName;
    @FXML private TextField tfDni;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfState;
    @FXML private Label lTileUser;
    @FXML private ChoiceBox tfRol;
    @FXML private Label lTitlePass;
    @FXML private PasswordField pfPassword;
    @FXML private PasswordField pfNewPassword;
    @FXML private Label lpfPassword;
    @FXML private Label lTitleNewPass;
    @FXML private Label lMsg;

    private User user;
    private static boolean editmode;
    private SectionController mController;
    private OID oid;

    public void initialize(){
        tfRol.getItems().add("User");
        tfRol.getItems().add("Admin");

    }
    public void setParentController(SectionController controller) {
        this.mController = controller;
    }

    public void open(OID oid){
        pfPassword.setText("");
        pfNewPassword.setText("");
        editmode = false;
        btnUser.setText("Editar");
        btnUser2.setText("Eliminar");
        if (oid==null){
            user = new User("","");
            editmode = true;
            btnUser.setText("Guardar");
            btnUser2.setText("Cancelar");
            lTileUser.setText("Nuevo Usuario");
            pfPassword.setVisible(false);
            lpfPassword.setVisible(false);
            lTitlePass.setVisible(false);
        }else{
            this.oid = oid;
            Object object= BBDD.getObjectByOid(oid);
            this.user = (User)object;
            lTileUser.setText("Visualizar Usuario");
            tfName.setEditable(false);
            tfEmail.setEditable(false);
            tfPhone.setEditable(false);
            tfDni.setEditable(false);
            tfRol.setDisable(true);
            tfState.setEditable(false);
            pfPassword.setVisible(false);
            lTitlePass.setVisible(false);
            pfNewPassword.setVisible(false);
            lTitlePass.setVisible(false);
            lTitleNewPass.setVisible(false);
            lpfPassword.setVisible(false);
            lMsg.setText("");

        }
        updateViewUser();

    }
    public void setEditable(){
        editmode = true;
        btnUser.setText("Guardar");
        btnUser2.setText("Cancelar");
        lTileUser.setText("Editar Usuario");
        pfPassword.setVisible(true);
        lTitlePass.setVisible(true);
        pfNewPassword.setVisible(true);
        lTitlePass.setVisible(true);
        lTitleNewPass.setVisible(true);
        lpfPassword.setVisible(true);
        if (MenuController.isAdmin()){
            tfName.setEditable(true);
            tfEmail.setEditable(true);
            tfPhone.setEditable(true);
            tfDni.setEditable(true);
            tfRol.setDisable(false);
            tfState.setEditable(true);
            pfPassword.setText(user.getPassword());
            pfNewPassword.setText(user.getPassword());
            pfPassword.setVisible(false);
            lpfPassword.setVisible(false);
        }

    }

    private void updateViewUser (){

        tfDni.setText(user.getDni());
        tfName.setText(user.getName());
        tfEmail.setText(user.getEmail());
        tfRol.setValue(user.getRol());
        tfPhone.setText(user.getPhone());
        tfState.setText(user.getState());
    }
    private void saveUser (){
        if (checkDni() && checkPasword()){
            user.updateUser(tfDni.getText(),tfName.getText(),tfEmail.getText(),tfRol.getValue().toString(),tfPhone.getText(),tfState.getText(),pfNewPassword.getText());
            this.oid=BBDD.saveUserByOID(this.oid,user);
            if (this.oid!=null){
                open(this.oid);
            }
        }
    }

    private boolean checkPasword() {
        boolean noError = false;
        if (this.user.checkPasword(pfPassword.getText())){
            noError= true;
        }else{
            lMsg.setText("Contraseña incorrecta");
        }
        return noError;
    }
    private boolean checkDni() {
        boolean noError = false;
        if (!tfDni.getText().equals("") && !tfName.getText().equals("")) {
            if (this.user.getDni().equals(tfDni.getText()) || BBDD.checkUserDni(tfDni.getText())) {
                noError = true;
            } else {
                lMsg.setText("El DNI ya exisite");
            }
        }else{
            lMsg.setText("El DNI y el nombre son obligatorios");
        }
        return noError;
    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUser) {
            if(this.editmode){
                saveUser();
            }else{
                if (MenuController.isAdmin() || MenuController.isSameUser(user.getName()))
                    setEditable();
            }

        }
        if (actionEvent.getSource() == btnUser2) {
            if(this.editmode){
                open(this.oid);
            }else{
                if (MenuController.isAdmin())
                    if (BBDD.deleteObjectByOid(this.oid,this.user.getName())){
                        ArrayList<ObjectForList> objectList = BBDD.getUserList();
                        mController.setInitialData("Equipo",objectList,"User");
                }
            }
        }
    }



}
