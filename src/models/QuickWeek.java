package models;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import static java.time.temporal.ChronoUnit.MINUTES;

public class QuickWeek {
    private Department department;
    private ArrayList<Date> dates1;
    private ArrayList<Date> dates2;
    private ArrayList<Date> dates3;
    private ArrayList<Date> dates4;
    private ArrayList<Date> dates5;
    private ArrayList<Date> dates6;
    private ArrayList<Date> dates7;
    private LocalDate today = LocalDate.now();
    private LocalDate firstDay;
    private int startDay = LocalDate.now().getDayOfWeek().getValue();
    private int daysOffset = 0;

    public QuickWeek(Department department, int daysOffset) {
        this.department = department;
        this.daysOffset = daysOffset;
        this.firstDay = LocalDate.now().plusDays(daysOffset);
        dates1 = getDatesPerDay(department, this.firstDay);
        dates2 = getDatesPerDay(department, this.firstDay.plusDays(1));
        dates3 = getDatesPerDay(department, this.firstDay.plusDays(2));
        dates4 = getDatesPerDay(department, this.firstDay.plusDays(3));
        dates5 = getDatesPerDay(department, this.firstDay.plusDays(4));
        dates6 = getDatesPerDay(department, this.firstDay.plusDays(5));
        dates7 = getDatesPerDay(department, this.firstDay.plusDays(6));
    }

    public void refresh(){
        dates1 = getDatesPerDay(department, this.firstDay);
        dates2 = getDatesPerDay(department, this.firstDay.plusDays(1));
        dates3 = getDatesPerDay(department, this.firstDay.plusDays(2));
        dates4 = getDatesPerDay(department, this.firstDay.plusDays(3));
        dates5 = getDatesPerDay(department, this.firstDay.plusDays(4));
        dates6 = getDatesPerDay(department, this.firstDay.plusDays(5));
        dates7 = getDatesPerDay(department, this.firstDay.plusDays(6));
    }

    public Department getDepartment() { return department; }
    public ArrayList<Date> getDates1() { return dates1; }
    public ArrayList<Date> getDates2() { return dates2; }
    public ArrayList<Date> getDates3() { return dates3; }
    public ArrayList<Date> getDates4() { return dates4; }
    public ArrayList<Date> getDates5() { return dates5; }
    public ArrayList<Date> getDates6() { return dates6; }
    public ArrayList<Date> getDates7() { return dates7; }
    public int getStartDay() { return startDay; }

    public LocalDate getStartDate() {
        return this.firstDay;
    }


 private ArrayList<Date> getDatesPerDay (Department department, LocalDate dateDay){
     return  BBDD.getDaysDates2(department.getId(),dateDay,dateDay);
 }


    public static ArrayList<QuickWeek> getQuickWeekList(int daysOffset){
        ArrayList<QuickWeek> quickWeeks = new ArrayList<>();
        ObservableList<Department> departments = BBDD.getDepartmentObservableList();
        for (Department item: departments){
            QuickWeek quickWeek = new QuickWeek(item,daysOffset);
            quickWeeks.add(quickWeek);
        }

    return quickWeeks;
    }


    public static double[] filterByHours(ArrayList<Date> dates){
        Iterator<Date> day = dates.iterator();
        double[] timeBars = {
                MINUTES.between(Settings.getStartday(),Settings.getStartBar2()),
                MINUTES.between(Settings.getStartBar2(),Settings.getStartBar3()),
                MINUTES.between(Settings.getStartBar3(),Settings.getStartBar4()),
                MINUTES.between(Settings.getStartBar4(),Settings.getStartBar5()),
                MINUTES.between(Settings.getStartBar5(),Settings.getEndday()),
                0};
        double[] totalTimeBars = (double[])timeBars.clone();

        while (day.hasNext()){
            Date date = day.next();
            timeBars = checkTimeBars(timeBars,date, Settings.getStartday(), Settings.getStartBar2(),0);
            timeBars = checkTimeBars(timeBars,date, Settings.getStartBar2(), Settings.getStartBar3(),1);
            timeBars = checkTimeBars(timeBars,date, Settings.getStartBar3(), Settings.getStartBar4(),2);
            timeBars = checkTimeBars(timeBars,date, Settings.getStartBar4(), Settings.getStartBar5(),3);
            timeBars = checkTimeBars(timeBars,date, Settings.getStartBar5(), Settings.getEndday(),4);

        }
        double[] bars = {
                (100 - timeBars[0] * 100 / totalTimeBars[0]),
                (100 - timeBars[1] * 100 / totalTimeBars[1]),
                (100 - timeBars[2] * 100 / totalTimeBars[2]),
                (100 - timeBars[3] * 100 / totalTimeBars[3]),
                (100 - timeBars[4] * 100 / totalTimeBars[4])};

        return bars;
    }

    private static double[] checkTimeBars(double[] timeBars, Date date, LocalTime start, LocalTime finish,int position){
        if (date.getStartTime().isAfter(start.minusMinutes(1)) && date.getStartTime().isBefore(finish)) {
            if (date.getFinishTime().isAfter(finish)) {
                timeBars[position] = timeBars[position] - MINUTES.between(date.getStartTime(), finish);
                timeBars[position + 1] = timeBars[position + 1] - 5 - MINUTES.between(finish, date.getFinishTime());
            } else {
                timeBars[position] = timeBars[position] - 5 - MINUTES.between(date.getStartTime(), date.getFinishTime());
            }
        }
        return timeBars;
    }



}
