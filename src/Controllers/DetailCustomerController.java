package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MenuController;
import models.*;
import org.neodatis.odb.OID;

import java.time.LocalDate;
import java.util.ArrayList;


public class DetailCustomerController {
    @FXML private Button btnCustomer;
    @FXML private Button btnCustomer2;
    @FXML private Button btPDF;

    @FXML private Label lTitle;
    @FXML private Label lMsg;

    @FXML private TextField tfDni;
    @FXML private TextField tfName;
    @FXML private DatePicker dpBithday;
    @FXML private TextField tfGuardian;
    @FXML private TextField tfPhone;
    @FXML private TextField tfEmail;
    @FXML private TextField tfAltContact;
    @FXML private TextField tfAltPhone;
    @FXML private TextField tfSchool;
    @FXML private TextField tfCourse;
    @FXML private TextField tfDerivedFrom;
    @FXML private TextField tfKnowUsFor;
    @FXML private CheckBox cbRgpd;
    @FXML private ChoiceBox chbGender;

    private Customer customer;
    private static boolean editmode;
    private SectionController mController;
    private OID oid;

    public void initialize(){
        chbGender.getItems().add("Masculino");
        chbGender.getItems().add("Femenino");

    }


    public void setParentController(SectionController controller) {
        this.mController = controller;
    }

    public void open(OID oid){
        if (oid==null){
            customer = new Customer("","");
            editmode = true;
            btnCustomer.setText("Guardar");
            btnCustomer2.setText("Cancelar");
            lTitle.setText("Nuevo Cliente");
        }else{
            editmode = false;
            btnCustomer.setText("Editar");
            btnCustomer2.setText("Eliminar");
            this.oid = oid;
            Object object= BBDD.getObjectByOid(oid);
            this.customer = (Customer)object;
            lTitle.setText("Visualizar Cliente");
            tfDni.setEditable(false);
            tfName.setEditable(false);
            dpBithday.setDisable(true);
            chbGender.setDisable(true);
            tfGuardian.setEditable(false);
            tfPhone.setEditable(false);
            tfEmail.setEditable(false);
            tfAltContact.setEditable(false);
            tfAltPhone.setEditable(false);
            tfSchool.setEditable(false);
            tfCourse.setEditable(false);
            tfDerivedFrom.setEditable(false);
            tfKnowUsFor.setEditable(false);
            cbRgpd.setDisable(true);
            lMsg.setText("");
        }

        updateViewCustomer();

    }
    public void setEditable(){
        editmode = true;
        btnCustomer.setText("Guardar");
        btnCustomer2.setText("Cancelar");
        lTitle.setText("Editar Cliente");
        if (MenuController.isAdmin()){

            tfDni.setEditable(true);
            tfName.setEditable(true);
            dpBithday.setDisable(false);
            chbGender.setDisable(false);
            tfGuardian.setEditable(true);
            tfPhone.setEditable(true);
            tfEmail.setEditable(true);
            tfAltContact.setEditable(true);
            tfAltPhone.setEditable(true);
            tfSchool.setEditable(true);
            tfCourse.setEditable(true);
            tfDerivedFrom.setEditable(true);
            tfKnowUsFor.setEditable(true);
            cbRgpd.setDisable(false);
            lMsg.setText("");
        }

    }

    private void updateViewCustomer (){

        tfDni.setText(customer.getDni());
        tfName.setText(customer.getName());
        dpBithday.setValue(customer.getBirthDate());
        chbGender.setValue(customer.getGender());
        tfGuardian.setText(customer.getGuardian());
        tfPhone.setText(customer.getPhone());
        tfEmail.setText(customer.getEmail());
        tfAltContact.setText(customer.getAltContact());
        tfAltPhone.setText(customer.getAltPhone());
        tfSchool.setText(customer.getSchool());
        tfCourse.setText(customer.getCourse());
        tfDerivedFrom.setText(customer.getDerivedFrom());
        tfKnowUsFor.setText(customer.getKnowUsFor());
        cbRgpd.setSelected(customer.isRgpd());

    }
    private void saveCustomer (){
        if (checkDni()){
            customer.updateCustomer(tfDni.getText(), tfName.getText(), dpBithday.getValue(),chbGender.getValue().toString(),tfGuardian.getText(),tfPhone.getText(), tfEmail.getText(), tfAltContact.getText(), tfAltPhone.getText(), cbRgpd.isSelected(),tfSchool.getText(), tfCourse.getText(), tfDerivedFrom.getText(), tfKnowUsFor.getText());
            this.oid=BBDD.saveCustomerByOID(this.oid,customer);
            if (this.oid!=null){
                open(this.oid);
            }
        }
    }


    private boolean checkDni() {
        boolean noError = false;
        if (!tfDni.getText().equals("") && !tfName.getText().equals("")) {
            if (this.customer.getDni().equals(tfDni.getText()) || BBDD.checkCustomerDni(tfDni.getText())) {
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
        if (actionEvent.getSource() == btnCustomer) {
            if(this.editmode){
                saveCustomer();
            }else{
                if (MenuController.isAdmin())
                    setEditable();
            }

        }
        if (actionEvent.getSource() == btnCustomer2) {
           if(this.editmode){
                open(this.oid);
            }else{
                if (MenuController.isAdmin())
                    if (BBDD.deleteObjectByOid(this.oid,this.customer.getName())){
                        ArrayList<ObjectForList> objectList = BBDD.getCustomerList();
                        mController.setInitialData("Equipo",objectList,"Customer");
                }
            }


        }
        if (actionEvent.getSource() == btPDF) {
            if(!this.editmode){
                CustomerReport.createPdf(customer);
            }


        }
    }



}
