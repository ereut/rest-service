package ru.intervale.course.beans;

public class Customer {

    private final int id;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String address;

    public Customer(int id, String name, String surname, String telephoneNumber,
                    String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
