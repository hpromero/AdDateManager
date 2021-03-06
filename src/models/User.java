package models;


import org.neodatis.odb.OID;


public class User {
    private String dni;
    private String name;
    private String email ="";
    private String password = "";
    private String rol = "";
    private String phone ="";
    private String state ="";

    public User(String dni, String name) {
            this.dni = dni;
            this.name = name;
    }
    public User(String dni, String name, String email, String rol, String phone, String state, String password) {
        this.dni = dni;
        this.name = name;
        this.email = email;
        this.rol = rol;
        this.phone = phone;
        this.state = state;
        this.password = password;
    }

    public String getDni() { return dni; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public String getPhone() { return phone; }
    public String getState() { return state; }
    public String getPassword() { return password; }


    public boolean checkPasword(String password) {
        return (this.password.equals(password));
    }


    static public ObjectForList UserToObjectForList(User user, OID oid){
        String color = "#1abc9c";
        if(user.getRol().equals("Admin")){
        color= "#d35400";
        }
        return new ObjectForList(0,oid, user.name, user.dni, user.email,user.phone,user.getRol(),"","",color,"User");
    }


    public void updateUser(String dni, String name, String email, String rol, String phone, String state, String password) {
            this.dni = dni;
            this.name = name;
            this.email = email;
            this.rol = rol;
            this.phone = phone;
            this.state = state;
            this.password = password;

    }
    public void copyUser(User userCopied) {
        this.dni = userCopied.getDni();
        this.name = userCopied.getName();
        this.email = userCopied.getEmail();
        this.rol = userCopied.getRol();
        this.phone = userCopied.getPhone();
        this.state = userCopied.getState();
        this.password = userCopied.getPassword();

    }

    public static User getUserFromDni(String dni){
        return BBDD.getUserByDni(dni);
    }

}
