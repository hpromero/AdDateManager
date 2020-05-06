package Controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import main.MenuController;
import models.*;
import org.neodatis.odb.OID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class DetailDateController {
    @FXML private Button btnDate;
    @FXML private Button btnDate2;
    @FXML private Label lTitle;
    @FXML private Label lMsg;

    @FXML private ComboBox<String> cbWeekDay = new ComboBox<>();
    @FXML private ComboBox<Customer> chbCustomer = new ComboBox<>();
    @FXML private ComboBox<Department> chbdepartment = new ComboBox<>();
    @FXML private JFXDatePicker dpDate;
    @FXML private JFXTimePicker tpstartTime;
    @FXML private JFXTimePicker tpfinishTime;
    @FXML private JFXToggleButton tgWeekly;

    private Date date;
    private static boolean editmode;
    private SectionController mController;
    private OID oid;

    ObservableList<Department> departments = FXCollections.observableArrayList();
    ObservableList<String> weekDays = FXCollections.observableArrayList("Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo");
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    public void initialize(){
        departments = BBDD.getDepartmentObservableList();
        customers = BBDD.getCustomerObservableList();
        setCustomerCombo(chbCustomer);
        setDepartmentCombo(chbdepartment);
        cbWeekDay.setItems(weekDays);

    }
    public void setParentController(SectionController controller) {
        this.mController = controller;
    }

    public void addDateFromDaily(String weekDay, LocalDate newDate, LocalTime start, LocalTime end,Department department){
        date = new Date("");
        editmode = true;
        btnDate.setText("Guardar");
        btnDate2.setVisible(false);
        btnDate2.setManaged(false);
        lTitle.setText("");
        cbWeekDay.setValue(weekDay);
        dpDate.setValue(newDate);
        tpstartTime.setValue(start);
        tpfinishTime.setValue(end);
        tgWeekly.setSelected(false);
        cbWeekDay.setVisible(false);
        dpDate.setVisible(true);
        cbWeekDay.setManaged(false);
        dpDate.setManaged(true);
        chbdepartment.setValue(department);

    }



    public void open(OID oid){
        if (oid==null){
            date = new Date("");
            editmode = true;
            btnDate.setText("Guardar");
            btnDate2.setText("Cancelar");
            lTitle.setText("");
        }else{
            editmode = false;
            btnDate.setText("Editar");
            btnDate2.setText("Eliminar");
            this.oid = oid;
            Object object= BBDD.getObjectByOid(oid);
            this.date = (Date)object;
            lTitle.setText("Visualizar Cita "+date.getId());
            cbWeekDay.setDisable(true);
            chbCustomer.setDisable(true);
            chbdepartment.setDisable(true);
            dpDate.setDisable(true);
            tpstartTime.setDisable(true);
            tpfinishTime.setDisable(true);
            lMsg.setText("");
            tgWeekly.setVisible(false);
            tgWeekly.setManaged(false);

        }

        updateViewDate();


    }
    public void setEditable(){
        editmode = true;
        btnDate.setText("Guardar");
        btnDate2.setText("Cancelar");
        lTitle.setText("Editar Cita "+date.getId());
        if (MenuController.isAdmin()){


            cbWeekDay.setDisable(false);
            chbCustomer.setDisable(false);
            chbdepartment.setDisable(false);
            lMsg.setText("");
            dpDate.setDisable(false);
            tpstartTime.setDisable(false);
            tpfinishTime.setDisable(false);
        }

    }

    private void updateViewDate (){

        cbWeekDay.setValue(date.getWeekDay());
        chbCustomer.setValue(Customer.getCustomerFromDni(date.getCustomer()));
        chbdepartment.setValue(Department.getDepartmentFromId(date.getDepartment()));
        dpDate.setValue(date.getDate());
        tpstartTime.setValue(date.getStartTime());
        tpfinishTime.setValue(date.getFinishTime());
        tgWeekly.setSelected(date.getWeekly());
        if (this.date.getWeekly()){
            cbWeekDay.setVisible(true);
            dpDate.setVisible(false);
            cbWeekDay.setManaged(true);
            dpDate.setManaged(false);
        }else{
            cbWeekDay.setVisible(false);
            dpDate.setVisible(true);
            cbWeekDay.setManaged(false);
            dpDate.setManaged(true);
        }
    }
    private void saveDate (){
        if (date.getId()==0){
            date.setId();
        }
        date.updateDate(cbWeekDay.getValue(), chbCustomer.getValue().getDni(), chbdepartment.getValue().getId(),dpDate.getValue(),tpstartTime.getValue(),tpfinishTime.getValue(),tgWeekly.isSelected());
        this.oid=BBDD.saveDateByOID(this.oid,date);
        if (this.oid!=null){
                open(this.oid);
            }

    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDate) {
            if(this.editmode){
                saveDate();
            }else{
                if (MenuController.isAdmin())
                    setEditable();
            }

        }
        if (actionEvent.getSource() == btnDate2) {
           if(this.editmode){
               if(this.date.getId()==0){
                   ArrayList<ObjectForList> objectList = BBDD.getDateList();
                   mController.openList("Equipo",objectList,"Date");
               }else{
                   open(this.oid);
               }
            }else{
                if (MenuController.isAdmin())
                    if (BBDD.deleteObjectByOid(this.oid,this.date.getWeekDay())){
                        ArrayList<ObjectForList> objectList = BBDD.getDateList();
                        mController.openList("Equipo",objectList,"Date");
                }
            }


        }
    }

    private void setDepartmentCombo(ComboBox<Department> combo){
        combo.setItems(departments);

        combo.setConverter(new StringConverter<Department>() {

            @Override
            public String toString(Department object) {
                return object.getName();
            }

            @Override
            public Department fromString(String string) {
                return combo.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }
    private void setCustomerCombo(ComboBox<Customer> combo){
        combo.setItems(customers);

        combo.setConverter(new StringConverter<Customer>() {

            @Override
            public String toString(Customer object) {
                return object.getName();
            }

            @Override
            public Customer fromString(String string) {
                return combo.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void weeklyChange(){
        if (tgWeekly.isSelected()){
            cbWeekDay.setVisible(true);
            dpDate.setVisible(false);
            cbWeekDay.setManaged(true);
            dpDate.setManaged(false);
        }else{
            cbWeekDay.setVisible(false);
            dpDate.setVisible(true);
            cbWeekDay.setManaged(false);
            dpDate.setManaged(true);
        }
    }


}
