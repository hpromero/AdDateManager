package models;


import org.neodatis.odb.OID;

public class Department {
    private int id;
    private String name;
    private String assigned = "";
    private String assignedName ="";
    private String assigned2="";
    private String assignedName2 ="";

    public Department(String name) {
            this.id = 0;
            this.name = name;
    }
    public Department(int id,String name,String assigned,String assignedName,String assigned2,String assignedName2) {
        this.id = id;
        this.name = name;
        this.assigned = assigned;
        this.assignedName = assignedName;
        this.assigned2 = assigned2;
        this.assignedName2 = assignedName2;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAssigned() { return assigned; }
    public String getAssigned2() { return assigned2; }
    public String getAssignedName() { return assignedName; }
    public String getAssignedName2() { return assignedName2; }
    public void setId() { this.id = BBDD.getNextId(Department.class); }


    static public ObjectForList DepartmentToObjectForList(Department department, OID oid){
        return new ObjectForList(department.id,oid, department.name, department.assignedName, department.assignedName2,"","","","","#ecf0f1","Department");

    }

    public void updateDepartment(String name, String assigned, String assigned2) {
        this.name = name;
        this.assigned = assigned;
        this.assigned2 = assigned2;
        this.assignedName = User.getUserFromDni(assigned).getName();
        this.assignedName2 = User.getUserFromDni(assigned2).getName();

    }



    public void copyDepartment(Department departmentCopied) {
        this.name = departmentCopied.getName();
        this.assigned = departmentCopied.getAssigned();
        this.assigned2 = departmentCopied.getAssigned2();
        this.assignedName = departmentCopied.getAssignedName();
        this.assignedName2 = departmentCopied.getAssignedName2();

    }
    public static Department getDepartmentFromId(int id){
        return BBDD.getDepartmentById(id);
    }

}
