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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.MenuController;
import models.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static javafx.scene.control.Alert.AlertType.ERROR;


public class DetailDateController {
    @FXML private Button btnDate;
    @FXML private Button btnDate2;
    @FXML private Label lTitle;
    @FXML private Label lMsg;

    @FXML private ComboBox<String> cbWeekDay = new ComboBox<>();
    @FXML private ComboBox<Customer> chbCustomer = new ComboBox<>();
    @FXML private ComboBox<Department> chbdepartment = new ComboBox<>();
    @FXML private JFXDatePicker dpDate;
    @FXML private JFXDatePicker dpDateEnd;
    @FXML private JFXTimePicker tpstartTime;
    @FXML private JFXTimePicker tpfinishTime;
    @FXML private JFXToggleButton tgWeekly;
    @FXML private HBox hbWeekDay;
    @FXML private Label lbdpDate;
    @FXML private Label lbdpDateEnd;

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
        dpDateEnd.setValue(newDate.plusMonths(1));
        tpstartTime.setValue(start);
        tpfinishTime.setValue(end);
        tgWeekly.setSelected(false);
        hbWeekDay.setVisible(false);
        hbWeekDay.setManaged(false);
        chbdepartment.setValue(department);
        dpDateEnd.setVisible(false);
        lbdpDate.setVisible(false);
        lbdpDateEnd.setVisible(false);
        lbdpDate.setManaged(false);

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
            this.date = BBDD.getDateById(id);
            lTitle.setText("Visualizar Cita "+date.getId());
            cbWeekDay.setDisable(true);
            chbCustomer.setDisable(true);
            chbdepartment.setDisable(true);
            dpDate.setDisable(true);
            dpDateEnd.setDisable(true);
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
            dpDateEnd.setDisable(false);
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
        dpDateEnd.setValue(date.getDateEnd());
        if (this.date.getWeekly()){
            hbWeekDay.setVisible(true);
            hbWeekDay.setManaged(true);
            dpDateEnd.setVisible(true);
            lbdpDate.setVisible(true);
            lbdpDateEnd.setVisible(true);
            lbdpDate.setManaged(true);
        }else{
            hbWeekDay.setVisible(false);
            hbWeekDay.setManaged(false);
            dpDateEnd.setVisible(false);
            lbdpDate.setVisible(false);
            lbdpDateEnd.setVisible(false);
            lbdpDate.setManaged(false);
        }
    }
    private boolean saveDate (){
        if (!tgWeekly.isSelected()){
            dpDateEnd.setValue(dpDate.getValue());
            cbWeekDay.setValue(Date.getWeekDayName(dpDate.getValue().getDayOfWeek().getValue()));
        }
        if (chbCustomer.getValue()!=null && chbdepartment.getValue()!=null) {
            date.updateDate(cbWeekDay.getValue(), chbCustomer.getValue().getDni(), chbdepartment.getValue().getId(),dpDate.getValue(),tpstartTime.getValue(),tpfinishTime.getValue(),tgWeekly.isSelected(),dpDateEnd.getValue());

           if (checkFreeDate()){
               if (date.getStartTime().isBefore(date.getFinishTime()) && (date.getDate().isBefore(date.getDateEnd())||date.getDate().equals(date.getDateEnd()))){
                       int id = BBDD.saveDate(date);
                       if (id != 0) {
                           open(id, this.fromPopUp);
                           return true;
                       }
               }else{
                   lMsg.setText("Las fechas no son válidas");
               }
           }
        }else{lMsg.setText("Todos los campos son obliagatorios"); }
     return false;
    }

    private boolean checkFreeDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ArrayList<Date> datesFound = BBDD.checkDatesCoincidences(date);
        if (datesFound.size()==0){
            return true;
        }else if((datesFound.size()==1)){
            infoBox("Cita dia: "+datesFound.get(0).getDate().format(formatter), "Error", "Existe una cita coincidente", ERROR);
        }else{

            infoBox("Citas: "+datesFound.toString(), "Error", "Existen "+datesFound.size()+" citas coincidentes", ERROR);
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
            hbWeekDay.setVisible(true);
            hbWeekDay.setManaged(true);
            dpDateEnd.setVisible(true);
            lbdpDate.setVisible(true);
            lbdpDateEnd.setVisible(true);
            lbdpDate.setManaged(true);
        }else{
            hbWeekDay.setVisible(false);
            hbWeekDay.setManaged(false);
            dpDateEnd.setVisible(true);
            lbdpDate.setVisible(false);
            lbdpDateEnd.setVisible(false);
            lbdpDate.setManaged(false);
        }
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage, Alert.AlertType tipoAlerta)
    {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
        // NONE, INFORMATION, WARNING, CONFIRMATION, ERROR
    }

}
