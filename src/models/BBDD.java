package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.neodatis.odb.*;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;


import javax.security.auth.callback.ConfirmationCallback;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static javafx.scene.control.Alert.AlertType.*;
import static sun.rmi.transport.TransportConstants.Return;

public class BBDD {
    static ODB odb = null;

    public static void openConection(){
        if(odb == null) {
            odb = ODBFactory.open("neodatis.bbdd");
            System.out.println("Conexion Open");
        }
    }
    public static void closeConection(){
        if(odb != null) {
            odb.close();
            odb=null;
            System.out.println("Conexion close");
        }
    }

    //------------------------------- General Methods------------------------------------

    public static Object  getObjectByOid(OID oid){
        try{
            openConection();
            return  odb.getObjectFromId(oid);
        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return null;
    }

    public static boolean deleteObjectByOid(OID oid,String name){
        try{
            openConection();
            if(confirmationBox("Eliminar","Desea elimiar "+name+"?")){
                odb.deleteObjectWithId(oid);
                infoBox("","Mensaje", name+" elimiando correctamente",INFORMATION);
                return true;
            }
        }catch(Exception e){
            infoBox(e.toString(),"Error",name+" no ha podido ser eliminado",ERROR);
        }finally{
            closeConection();
        }
        return false;
    }

    public static int getNextId(Class clazz) {
        try {
            openConection();

            Values val3 = odb.getValues(new ValuesCriteriaQuery(clazz).max("id"));
            System.out.println(val3);
                ObjectValues ov3 = val3.nextValues();
            System.out.println(ov3);
            BigDecimal value = (BigDecimal) ov3.getByAlias("id");
           //     return ((BigDecimal) ov3.getByAlias("id")).intValueExact()+1;
            if (value.compareTo(new BigDecimal("0"))>=0){
                return value.intValueExact()+1;
            }

        } catch (Exception e) {
            infoBox(e.toString(), "Error", "Error de conexión a Base de datos", ERROR);
        } finally {
            closeConection();
        }
        return 1;
    }

// --------------------- Users methods -------------------------------

        public static OID saveUserByOID(OID oid, User user){
            OID newOid=null;
            try {
                openConection();
                if (oid==null){
                    newOid = odb.store(user);
                }else{
                    Object object = odb.getObjectFromId(oid);
                    User userToUpdate = (User)object;
                    userToUpdate.copyUser(user);
                    newOid = odb.store(userToUpdate);
                }
                infoBox("","Mensaje", "Usuario guardado correctamente",INFORMATION);
            } catch(Exception e) {
                infoBox(e.toString(),"Error","Usuario no guardado",ERROR);
            }finally{ closeConection(); }

            return newOid;
        }



    public static ArrayList<ObjectForList> getUserList() {

        ArrayList<ObjectForList> userList = new ArrayList<>();
        try {
            openConection();
            Objects<User> usersodb = odb.getObjects(User.class);
            while(usersodb.hasNext()){
                User user = usersodb.next();
                ObjectForList userObj = User.UserToObjectForList(user,odb.getObjectId(user));
                userList.add(userObj);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return userList;
    }
    public static ObservableList<User> getUserObservableList() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            openConection();
            Objects<User> usersodb = odb.getObjects(User.class);
            while(usersodb.hasNext()){
                User user = usersodb.next();
                userList.add(user);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return userList;
    }

    public static boolean checkUserDni(String id) {
        try{
            openConection();
            ICriterion criterio = Where.equal("dni", id);
            IQuery query = new CriteriaQuery(User.class,criterio);
            Objects<User> userodb = odb.getObjects(query);
            if (userodb.size()==0){
                return true;
            }
        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return false;
    }
    public static User getUserByDni(String id) {
        try{
            openConection();
            ICriterion criterio = Where.equal("dni", id);
            IQuery query = new CriteriaQuery(User.class,criterio);
            Objects<User> userodb = odb.getObjects(query);
        //    return (User) userodb.getFirst();
            if (userodb.size()>0){
                return (User) userodb.getFirst();
            }

        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return null;
    }
    public static boolean checkUserPasword(String name, String password) {
        try {
            openConection();
            ICriterion criterio = Where.equal("name",name);
            IQuery query = new CriteriaQuery(User.class,criterio);
            Objects<User> users = odb.getObjects(query);
            if (users.size()>0){
                User user = (User) users.getFirst();
                return user.checkPasword(password);
            }
        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally {
            closeConection();
        }
        return false;
    }



// --------------------- Customers methods -------------------------------


    public static OID saveCustomerByOID(OID oid, Customer customer){
        OID newOid=null;
        try {
            openConection();
            if (oid==null){
                newOid = odb.store(customer);
            }else{
                Object object = odb.getObjectFromId(oid);
                Customer customerToUpdate = (Customer)object;
                customerToUpdate.copyCustomer(customer);
                newOid = odb.store(customerToUpdate);
            }
            infoBox("","Mensaje", "Cliente guardado correctamente",INFORMATION);
        } catch(Exception e) {
            infoBox(e.toString(),"Error","Cliente no guardado",ERROR);
        }finally{ closeConection(); }

        return newOid;
    }

    public static ArrayList<ObjectForList> getCustomerList() {

        ArrayList<ObjectForList> customerList = new ArrayList<>();
        try {
            openConection();
            Objects<Customer> customersodb = odb.getObjects(Customer.class);
            while(customersodb.hasNext()){
                Customer customer = customersodb.next();
                ObjectForList customerObj = Customer.CustomerToObjectForList(customer,odb.getObjectId(customer));
                customerList.add(customerObj);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return customerList;
    }

    public static boolean checkCustomerDni(String id) {
        try{
            openConection();
            ICriterion criterio = Where.equal("dni", id);
            IQuery query = new CriteriaQuery(Customer.class,criterio);
            Objects<Customer> customerodb = odb.getObjects(query);
            if (customerodb.size()==0){
                return true;
            }
        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return false;

    }
    public static ObservableList<Customer> getCustomerObservableList() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            openConection();
            Objects<Customer> customersodb = odb.getObjects(Customer.class);
            while(customersodb.hasNext()){
                Customer user = customersodb.next();
                customerList.add(user);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return customerList;
    }
    public static Customer getCustomerByDni(String id) {
        try{
            openConection();
            ICriterion criterio = Where.equal("dni", id);
            IQuery query = new CriteriaQuery(Customer.class,criterio);
            Objects<Customer> customerodb = odb.getObjects(query);
            //    return (User) userodb.getFirst();
            if (customerodb.size()>0){
                return (Customer) customerodb.getFirst();
            }

        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return null;
    }
    // --------------------- Departments methods -------------------------------


    public static OID saveDepartmentByOID(OID oid, Department department){
        OID newOid=null;
        try {
            openConection();
            if (oid==null){
                newOid = odb.store(department);
            }else{
                Object object = odb.getObjectFromId(oid);
                Department departmentToUpdate = (Department)object;
                departmentToUpdate.copyDepartment(department);
                newOid = odb.store(departmentToUpdate);
            }
            infoBox("","Mensaje", "Departamento guardado correctamente",INFORMATION);
        } catch(Exception e) {
            infoBox(e.toString(),"Error","Departamento no guardado",ERROR);
        }finally{ closeConection(); }

        return newOid;
    }

    public static ArrayList<ObjectForList> getDepartmentList() {

        ArrayList<ObjectForList> departmentList = new ArrayList<>();
        try {
            openConection();
            Objects<Department> departmentsodb = odb.getObjects(Department.class);
            while(departmentsodb.hasNext()){
                Department department = departmentsodb.next();
                ObjectForList departmentObj = Department.DepartmentToObjectForList(department,odb.getObjectId(department));
                departmentList.add(departmentObj);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return departmentList;
    }

    public static boolean checkDepartmentDni(String id) {
        try{
            openConection();
            ICriterion criterio = Where.equal("id", id);
            //TODO: comprobar ¿id?
            IQuery query = new CriteriaQuery(Department.class,criterio);
            Objects<Department> departmentodb = odb.getObjects(query);
            if (departmentodb.size()==0){
                return true;
            }
        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return false;

    }

    public static Department getDepartmentById(int id) {
        try{
            openConection();
            ICriterion criterio = Where.equal("id", id);
            IQuery query = new CriteriaQuery(Department.class,criterio);
            Objects<Department> departmentodb = odb.getObjects(query);
            if (departmentodb.size()>0){
                return (Department) departmentodb.getFirst();
            }

        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return null;
    }
    public static ObservableList<Department> getDepartmentObservableList() {
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        try {
            openConection();
            Objects<Department> departmentsodb = odb.getObjects(Department.class);
            while(departmentsodb.hasNext()){
                Department department = departmentsodb.next();
                departmentList.add(department);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return departmentList;
    }
// --------------------- Dates methods -------------------------------



    public static int saveDate(Date date){
        boolean isSave = false;
        if (date.getId() == 0){
            date.setId();
            isSave = saveNewDate(date);
        }else{
            isSave = updateDate(date);
        }

        if (isSave){
            infoBox("","Mensaje", "Cita guardada correctamente",INFORMATION);
            return date.getId();
        }else{
            infoBox("","Error","Cita no guardada",ERROR);
        }
        return 0;
    }

    private static boolean saveNewDate(Date date){
        openConection();
        if(odb.store(date) != null){
            return true;
        }
        closeConection();
        return false;
    }

    private static boolean updateDate(Date date){
        try{
            openConection();
            ICriterion criterio = Where.equal("id", date.getId());
            IQuery query = new CriteriaQuery(Date.class,criterio);
            Objects<Date> datesodb = odb.getObjects(query);
            Date dateOdb = datesodb.getFirst();
            dateOdb.copyDate(date);
            if(odb.store(dateOdb) != null){
                return true;
            }
        }catch(Exception e){
        }finally{
            closeConection();
        }
        return false;
    }
    public static boolean deleteDate(Date date){
        try{
            openConection();
            ICriterion criterio = Where.equal("id", date.getId());
            IQuery query = new CriteriaQuery(Date.class,criterio);
            Objects<Date> datesodb = odb.getObjects(query);
            Date dateOdb = datesodb.getFirst();

            if(confirmationBox("Eliminar","Desea elimiar la cita?")) {
                odb.delete(dateOdb);
                infoBox("", "Mensaje", "Cita elimiando correctamente", INFORMATION);
                return true;
            }

        }catch(Exception e){
            infoBox(e.toString(),"Error","La cita no ha podido ser eliminada",ERROR);
        }finally{
            closeConection();
        }
        return false;
    }


    public static ArrayList<ObjectForList> getDateList() {

        ArrayList<ObjectForList> dateList = new ArrayList<>();
        try {
            openConection();
            Objects<Date> datesodb = odb.getObjects(Date.class);
            while(datesodb.hasNext()){
                Date date = datesodb.next();
                ObjectForList dateObj = Date.DateToObjectForList(date,odb.getObjectId(date));
                dateList.add(dateObj);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return dateList;
    }

    public static ArrayList<Date> getDaysDates(ArrayList<Date> dateList,int department, LocalDate dateDay) {
        try {
            openConection();
            ICriterion criterio = Where.and()
                    .add(Where.equal("department", department))
                    .add(Where.equal("weekly", false));
            //TODO: Añadir filtro citas pasadas
            IQuery query = new CriteriaQuery(Date.class,criterio);
            Objects<Date> datesodb = odb.getObjects(query);
            while(datesodb.hasNext()){
                Date date = datesodb.next();
                if (date.getDate().isEqual(dateDay)) {
                    dateList.add(date);
                }
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return dateList;
    }
    /*
     ICriterion criterio = Where.and()
                    .add(Where.equal("department", department))
                    .add(Where.equal("date", dateDay))
                    .add(Where.equal("weekly", false));
     */



    public static ArrayList<Date> getWeekDayDates(ArrayList<Date> dateList,int department, String weekDay) {
        try {
            openConection();
            ICriterion criterio = Where.and()
                    .add(Where.equal("department", department))
                    .add(Where.equal("weekDay", weekDay))
                    .add(Where.equal("weekly", true));
            IQuery query = new CriteriaQuery(Date.class,criterio);
            Objects<Date> datesodb = odb.getObjects(query);
            while(datesodb.hasNext()){
                Date date = datesodb.next();
                dateList.add(date);
            }

        } catch(Exception e) {
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return dateList;
    }

    public static Date getDateById(int id) {
        try{
            openConection();
            ICriterion criterio = Where.equal("id", id);
            IQuery query = new CriteriaQuery(Date.class,criterio);
            Objects<Date> dateodb = odb.getObjects(query);
            if (dateodb.size()>0){
                return (Date) dateodb.getFirst();
            }

        }catch(Exception e){
            infoBox(e.toString(),"Error","Error de conexión a Base de datos",ERROR);
        }finally{
            closeConection();
        }
        return null;
    }

    //--------------------- Others methods ----------------------------


    public static void infoBox(String infoMessage, String titleBar, String headerMessage, Alert.AlertType tipoAlerta)
    {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
        // NONE, INFORMATION, WARNING, CONFIRMATION, ERROR
    }

    public static boolean confirmationBox(String titleBar, String headerMessage) {
        Alert alert = new Alert(CONFIRMATION,headerMessage,ButtonType.YES,ButtonType.CANCEL);
        alert.setTitle(titleBar);
   //     alert.setHeaderText(headerMessage);
    //    alert.setContentText(infoMessage);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return true;
        }
    return false;
    }



}
