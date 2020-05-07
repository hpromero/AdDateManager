package Controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.MenuController;
import models.*;

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
    private SectionController sectionController;
    private DailyViewController dailyViewController;
    private boolean fromPopUp = false;

    ObservableList<Department> departments = FXCollections.observableArrayList();
    ObservableList<String> weekDays = FXCollections.observableArrayList("Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo");
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    public void initialize(){
        departments = BBDD.getDepartmentObservableList();
        customers = BBDD.getCustomerObservableList();
        setCustomerCombo(chbCustomer);
        setDepartmentCombo(chbdepartment);
        cbWeekDay.setItems(weekDays);
        tpstartTime.set24HourView(true);
        tpfinishTime.set24HourView(true);
    }
    public void setSectionController(SectionController controller) {
        this.sectionController = controller;
    }
    public void setDailyController(DailyViewController controller) {
        this.dailyViewController = controller;
    }

    public void addDateFromDaily(String weekDay, LocalDate newDate, LocalTime start, LocalTime end,Department department){
        this.fromPopUp = true;
        date = new Date("");
        editmode = true;
        btnDate.setText("Guardar");
        btnDate2.setText("Cancelar");
        lTitle.setText("Nueva Cita");
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



    public void open(int id,boolean fromPopUp){
        this.fromPopUp = fromPopUp;
        if (id==0){
            date = new Date("");
            editmode = true;
            btnDate.setText("Guardar");
            btnDate2.setText("Cancelar");
            lTitle.setText("");
        }else{
            editmode = false;
            btnDate.setText("Editar");
            btnDate2.setText("Eliminar");
            this.date = BBDD.getDateById(id);                   //----------Acabo de cambiar de OID a id
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
    private boolean saveDate (){
        date.updateDate(cbWeekDay.getValue(), chbCustomer.getValue().getDni(), chbdepartment.getValue().getId(),dpDate.getValue(),tpstartTime.getValue(),tpfinishTime.getValue(),tgWeekly.isSelected());
       if (checkFreeDate()){
           int id = BBDD.saveDate(date);
           if (id!=0){
               open(id,this.fromPopUp);
               return true;
           }
       }

     return false;
    }

    private boolean checkFreeDate() {
        //TODO:
        ArrayList<Date> datesFound = new ArrayList<>();
        if (date.getWeekly()){
            datesFound = BBDD.checkDaysDates(datesFound,date.getDepartment(),date.getDate(),date.getStartTime(),date.getFinishTime(),date.getId(),date.getWeekDay());
            datesFound = BBDD.checkWeekDayDates(datesFound,date.getDepartment(),date.getWeekDay(),date.getStartTime(), date.getFinishTime(),date.getId());

        }else{
            datesFound = BBDD.checkDaysDates(datesFound,date.getDepartment(),date.getDate(),date.getStartTime(),date.getFinishTime(),date.getId(),"");
            datesFound = BBDD.checkWeekDayDates(datesFound,date.getDepartment(),Date.getWeekDayName(date.getDate().getDayOfWeek().getValue()),date.getStartTime(), date.getFinishTime(),date.getId());
        }
        if (datesFound.size()==0){
            return true;
        }else{
            System.out.println("Existen citas en esa fecha y hora");
        }
        return false;
    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDate) {
            if(this.editmode){
                if (saveDate() && this.fromPopUp){
                    dailyViewController.refresh();
                    Node source = (Node)  actionEvent.getSource();
                    Stage stage  = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }else{
                if (MenuController.isAdmin())
                    setEditable();
            }

        }
        if (actionEvent.getSource() == btnDate2) {
           if(this.editmode){
               if(this.date.getId()==0){
                   if (this.fromPopUp){
                       Node source = (Node)  actionEvent.getSource();
                       Stage stage  = (Stage) source.getScene().getWindow();
                       stage.close();
                   }else{
                       ArrayList<ObjectForList> objectList = BBDD.getDateList();
                       sectionController.openList("Citas",objectList,"Date");
                   }
               }else{
                   open(date.getId(),this.fromPopUp);
               }
            }else{
                if (MenuController.isAdmin())
                    if (BBDD.deleteDate(this.date)){
                        if (this.fromPopUp){
                            //TODO: Cambiar ación al borrar proa Actualizar padre y salir si es Popup
                            dailyViewController.refresh();
                            Node source = (Node)  actionEvent.getSource();
                            Stage stage  = (Stage) source.getScene().getWindow();
                            stage.close();
                        }else{
                            ArrayList<ObjectForList> objectList = BBDD.getDateList();
                            sectionController.openList("Citas",objectList,"Date");
                        }

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
