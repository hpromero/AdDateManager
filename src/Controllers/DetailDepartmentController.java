package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import main.MenuController;
import models.BBDD;
import models.Department;
import models.ObjectForList;
import models.User;
import org.neodatis.odb.OID;

import java.util.ArrayList;


public class DetailDepartmentController {
    @FXML private Button btnDepartment;
    @FXML private Button btnDepartment2;
    @FXML private Label lTitle;
    @FXML private Label lMsg;

    @FXML private TextField tfName;
    @FXML private ComboBox<User> chbAssigned = new ComboBox<>();
    @FXML private ComboBox<User> chbAssigned2 = new ComboBox<>();

    private Department department;
    private static boolean editmode;
    private SectionController mController;
    private OID oid;
    ObservableList<User> users = FXCollections.observableArrayList();

    public void initialize(){
        users = BBDD.getUserObservableList();
        setUserCombo(chbAssigned);
        setUserCombo(chbAssigned2);

    }
    public void setParentController(SectionController controller) {
        this.mController = controller;
    }

    public void open(OID oid){
        if (oid==null){
            department = new Department("");
            editmode = true;
            btnDepartment.setText("Guardar");
            btnDepartment2.setText("Cancelar");
            lTitle.setText("Nuevo Departamento");
        }else{
            editmode = false;
            btnDepartment.setText("Editar");
            btnDepartment2.setText("Eliminar");
            this.oid = oid;
            Object object= BBDD.getObjectByOid(oid);
            this.department = (Department)object;
            lTitle.setText("Visualizar Departamento"+department.getId());
            tfName.setEditable(false);
            chbAssigned.setDisable(true);
            chbAssigned2.setDisable(true);
            lMsg.setText("");
        }

        updateViewDepartment();

    }
    public void setEditable(){
        editmode = true;
        btnDepartment.setText("Guardar");
        btnDepartment2.setText("Cancelar");
        lTitle.setText("Editar Departamento"+department.getId());
        if (MenuController.isAdmin()){

            tfName.setEditable(true);
            chbAssigned.setDisable(false);
            chbAssigned2.setDisable(false);
            lMsg.setText("");
        }

    }

    private void updateViewDepartment (){


        tfName.setText(department.getName());
        chbAssigned.setValue(User.getUserFromDni(department.getAssigned()));
        chbAssigned2.setValue(User.getUserFromDni(department.getAssigned2()));
    }
    private void saveDepartment (){
        if (department.getId()==0){
            department.setId();
        }
            department.updateDepartment( tfName.getText(), chbAssigned.getValue().getDni(),chbAssigned2.getValue().getDni());
            this.oid=BBDD.saveDepartmentByOID(this.oid,department);
            if (this.oid!=null){
                open(this.oid);
            }

    }




    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDepartment) {
            if(this.editmode){
                saveDepartment();
            }else{
                if (MenuController.isAdmin())
                    setEditable();
            }

        }
        if (actionEvent.getSource() == btnDepartment2) {
           if(this.editmode){
                open(this.oid);
            }else{
                if (MenuController.isAdmin())
                    if (BBDD.deleteObjectByOid(this.oid,this.department.getName())){
                        ArrayList<ObjectForList> objectList = BBDD.getDepartmentList();
                        mController.setInitialData("Equipo",objectList,"Department");
                }
            }


        }
    }

    private void setUserCombo(ComboBox<User> combo){
        combo.setItems(users);

        combo.setConverter(new StringConverter<User>() {

            @Override
            public String toString(User object) {
                return object.getName();
            }

            @Override
            public User fromString(String string) {
                return combo.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }



}
