package models;


import org.neodatis.odb.OID;

import java.time.LocalDate;

public class Customer {
    private String dni;
    private String name;
    private LocalDate birthDate;
    private String gender = "";
    private String guardian = "";
    private String phone ="";
    private String email ="";
    private String altContact ="";
    private String altPhone ="";
    private boolean rgpd= false;
    private String school="";
    private String course="";
    private String derivedFrom="";
    private String knowUsFor="";
    private String active="Activo";

    public Customer(String dni, String name) {
            this.dni = dni;
            this.name = name;
    }
    public Customer(String dni, String name, LocalDate birthDate, String gender, String guardian, String phone, String email, String altContact, String altPhone, boolean rgpd, String school, String course, String derivedFrom, String knowUsFor) {
        this.dni = dni;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.guardian = guardian;
        this.phone = phone;
        this.email = email;
        this.altContact = altContact;
        this.altPhone = altPhone;
        this.rgpd = rgpd;
        this.school = school;
        this.course = course;
        this.derivedFrom = derivedFrom;
        this.knowUsFor = knowUsFor;
    }

    public String getDni() { return dni; }
    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getGender() { return gender; }
    public String getGuardian() { return guardian; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAltContact() { return altContact; }
    public String getAltPhone() { return altPhone; }
    public boolean isRgpd() { return rgpd; }
    public String getSchool() { return school; }
    public String getCourse() { return course; }
    public String getDerivedFrom() { return derivedFrom; }
    public String getKnowUsFor() { return knowUsFor; }


    static public ObjectForList CustomerToObjectForList(Customer customer, OID oid){
        return new ObjectForList(oid, customer.name, customer.dni, customer.email,customer.phone,customer.active,"","","#27ae60","Customer");
    }

    public void updateCustomer(String dni, String name, LocalDate birthDate, String gender, String guardian, String phone, String email, String altContact, String altPhone, boolean rgpd, String school, String course, String derivedFrom, String knowUsFor) {
        this.dni = dni;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.guardian = guardian;
        this.phone = phone;
        this.email = email;
        this.altContact = altContact;
        this.altPhone = altPhone;
        this.rgpd = rgpd;
        this.school = school;
        this.course = course;
        this.derivedFrom = derivedFrom;
        this.knowUsFor = knowUsFor;
    }



    public void copyCustomer(Customer customerCopied) {
        this.dni = customerCopied.getDni();
        this.name = customerCopied.getName();
        this.birthDate = customerCopied.getBirthDate();
        this.gender = customerCopied.getGender();
        this.guardian = customerCopied.getGuardian();
        this.phone = customerCopied.getPhone();
        this.email = customerCopied.getEmail();
        this.altContact = customerCopied.getAltContact();
        this.altPhone = customerCopied.getAltPhone();
        this.rgpd = customerCopied.isRgpd();
        this.school = customerCopied.getSchool();
        this.course = customerCopied.getCourse();
        this.derivedFrom = customerCopied.getDerivedFrom();
        this.knowUsFor = customerCopied.getKnowUsFor();

    }
    public static Customer getCustomerFromDni(String dni){
        return BBDD.getCustomerByDni(dni);
    }

}
