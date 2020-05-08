package models;


import org.neodatis.odb.OID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

public class Date implements Comparable<Date> {
    private int id;
    private String weekDay;
    private String customer = "";
    private String customerName ="";
    private int department = 0;
    private String departmentName ="";
    private LocalDate date = LocalDate.now();
    private LocalDate dateEnd = LocalDate.now().plusMonths(1);
    private LocalTime startTime = LocalTime.now();
    private LocalTime finishTime = LocalTime.now().plusMinutes(60);
    private boolean weekly = true;

    public Date(String weekDay) {
            this.id = 0;
            this.weekDay = weekDay;
    }

    public Date(String weekDay, String customer, String customerName, int department, String departmentName, LocalDate date, LocalTime startTime, LocalTime finishTime, boolean weekly) {
        setId();
        this.weekDay = weekDay;
        if(!weekly){
            this.dateEnd=date;
            this.weekDay= Date.getWeekDayName(date.getDayOfWeek().getValue());
        }
        this.customer = customer;
        this.customerName = customerName;
        this.department = department;
        this.departmentName = departmentName;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.weekly = weekly;
    }

    public int getId() { return id; }
    public String getWeekDay() { return weekDay; }
    public String getCustomer() { return customer; }
    public int getDepartment() { return department; }
    public String getCustomerName() { return customerName; }
    public String getDepartmentName() { return departmentName; }
    public LocalDate getDate() { return date; }
    public LocalDate getDateEnd() { return dateEnd; }
    public boolean getWeekly() { return weekly; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getFinishTime() { return finishTime; }
    public void setId() { this.id = BBDD.getNextId(Date.class); }



    static public ObjectForList DateToObjectForList(Date date, OID oid){
        String formattedDate = date.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String dateDay;
        String text4;
        String color;
        if(date.getWeekly()){
            dateDay=date.weekDay;
            text4=date.date+"//"+date.dateEnd;
            color="#fdcb6e";
        }else{
            dateDay=formattedDate;
            text4=date.weekDay;
            color="#0984e3";
        }
        return new ObjectForList(date.id, oid, dateDay, date.customerName, date.departmentName,text4,date.startTime+"//"+date.finishTime,"","",color,"Date");
    }
    /*"#ecf0f1"*/

    public void updateDate(String weekDay, String customer, int department, LocalDate date, LocalTime startTime, LocalTime finishTime, boolean weekly, LocalDate dateEnd) {
        this.weekDay = weekDay;
        this.customer = customer;
        this.department = department;
        this.customerName = Customer.getCustomerFromDni(customer).getName();
        this.departmentName = Department.getDepartmentFromId(department).getName();
        this.date = date;
        this.dateEnd = dateEnd;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.weekly = weekly;

    }



    public void copyDate(Date dateCopied) {
        this.weekDay = dateCopied.getWeekDay();
        this.customer = dateCopied.getCustomer();
        this.department = dateCopied.getDepartment();
        this.customerName = dateCopied.getCustomerName();
        this.departmentName = dateCopied.getDepartmentName();
        this.date = dateCopied.getDate();
        this.dateEnd = dateCopied.getDateEnd();
        this.startTime = dateCopied.startTime;
        this.finishTime = dateCopied.finishTime;
        this.weekly = dateCopied.weekly;


    }

    public static String getWeekDayName (int key){
        if(key>14){key=key-14;}
        if(key>7){key=key-7;}
        Hashtable<Integer, String> weekdays = new Hashtable<Integer, String>();
        weekdays.put(1,"Lunes");
        weekdays.put(2,"Martes");
        weekdays.put(3,"Miércoles");
        weekdays.put(4,"Jueves");
        weekdays.put(5,"Viernes");
        weekdays.put(6,"Sábado");
        weekdays.put(7,"Domingo");

        return weekdays.get(key);
    }

    @Override
    public int compareTo(Date date) {
        return (this.getStartTime().isBefore(date.getStartTime())  ? -1 :
                (this.getStartTime().equals(date.getStartTime()) ? 0 : 1));
    }


    public boolean dateIsInsideDays(LocalDate dateDay, LocalDate endDateDay){
        if ((this.date.isBefore(dateDay)&&this.dateEnd.isBefore(dateDay))
                ||(this.date.isAfter(endDateDay)&&this.dateEnd.isAfter(endDateDay))){
            return false;
        }
        return true;
    }

    public boolean dateIsCoincidence(Date dateToCkeck){
        if ((this.date.isBefore(dateToCkeck.date)&&this.dateEnd.isBefore(dateToCkeck.date))
                ||(this.date.isAfter(dateToCkeck.dateEnd)&&this.dateEnd.isAfter(dateToCkeck.dateEnd))){
            return false;
        }else{
            if ((this.startTime.isBefore(dateToCkeck.startTime)&&(this.finishTime.isBefore(dateToCkeck.startTime)||this.finishTime.equals(dateToCkeck.startTime)))
                    ||(this.startTime.equals(dateToCkeck.finishTime)||(this.startTime.isAfter(dateToCkeck.finishTime))&&this.finishTime.isAfter(dateToCkeck.finishTime))){
                return false;
            }else{
                return (this.id!=dateToCkeck.id);
            }
        }

    }


}
