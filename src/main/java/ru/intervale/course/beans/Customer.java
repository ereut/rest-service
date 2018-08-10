package ru.intervale.course.beans;

import java.util.Locale;

public class Customer extends AbstractEntity {

    private static final String CUSTOMER_PRINT_FORMAT = "|%-6d|%-15s|%-15s|%-15s|%-20s|%-40s|";

    private String login;
    private String password;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String address;

    public Customer() {
        super();
    }

    public Customer(Integer id, String login, String password, String name, String surname,
                    String telephoneNumber, String address) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
        this.address = address;

    }

    public Customer(String login, String password, String name, String surname,
                    String telephoneNumber, String address) {
        this(null, login, password, name, surname, telephoneNumber, address);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, CUSTOMER_PRINT_FORMAT, getId(), login, name , surname,
                telephoneNumber, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!login.equals(customer.login)) return false;
        if (!password.equals(customer.password)) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (surname != null ? !surname.equals(customer.surname) : customer.surname != null) return false;
        if (telephoneNumber != null ? !telephoneNumber.equals(customer.telephoneNumber) : customer.telephoneNumber != null)
            return false;
        return address != null ? address.equals(customer.address) : customer.address == null;
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (telephoneNumber != null ? telephoneNumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

}