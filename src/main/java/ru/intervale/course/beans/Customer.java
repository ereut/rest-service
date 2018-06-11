package ru.intervale.course.beans;

import java.util.Locale;

public class Customer extends AbstractEntity {

    private static final String CUSTOMER_PRINT_FORMAT = "|%-5d|%-10s|%-15s|%-12s|%-45s|%-15s|%-15s|";
    private static final String CUSTOMER_ADDRESS_PRINT_FORMAT = "%s;%s;%s;%s;%s";
    private static final String DASH = "-";

    private String name;
    private String surname;
    private String telephoneNumber;
    private Address address;
    private String login;
    private String password;


    public Customer(int id, String name, String surname, String telephoneNumber,
                    String country, String city, String street, String homeNumber,
                    String flatNumber) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
        address = new Address(country, city, street, homeNumber, flatNumber);
    }

    public Customer(String name, String surname, String telephoneNumber,
                    String country, String city, String street, String homeNumber,
                    String flatNumber) {
        this(0, name, surname, telephoneNumber, country, city, street, homeNumber,
                flatNumber);
    }

    public class Address {

        private String country;
        private String city;
        private String street;
        private String homeNumber;
        private String flatNumber;

        public Address(String country, String city, String street,
                       String homeNumber, String flatNumber) {
            this.country = country;
            this.city = city;
            this.street = street;
            this.homeNumber = homeNumber;
            this.flatNumber = flatNumber;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        public String getHomeNumber() {
            return homeNumber;
        }

        public String getFlatNumber() {
            return flatNumber;
        }

        @Override
        public String toString() {
            return String.format(Locale.ENGLISH, CUSTOMER_ADDRESS_PRINT_FORMAT, country, city,street,
                    homeNumber,flatNumber);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Address address = (Address) o;

            if (country != null ? !country.equals(address.country) : address.country != null) return false;
            if (city != null ? !city.equals(address.city) : address.city != null) return false;
            if (street != null ? !street.equals(address.street) : address.street != null) return false;
            if (homeNumber != null ? !homeNumber.equals(address.homeNumber) : address.homeNumber != null) return false;
            return flatNumber != null ? flatNumber.equals(address.flatNumber) : address.flatNumber == null;
        }

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

    public Address getAddress() {
        return address;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, CUSTOMER_PRINT_FORMAT,
                getId(), name, surname, telephoneNumber, address, login == null ? DASH : login,
                password == null ? DASH : login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!name.equals(customer.name)) return false;
        if (!surname.equals(customer.surname)) return false;
        if (!telephoneNumber.equals(customer.telephoneNumber)) return false;
        return address.equals(customer.address);
    }

}