package ru.intervale.course.beans;

import java.util.Locale;

public class Customer extends AbstractEntity {

    private String name;
    private String surname;
    private String telephoneNumber;
    private Address address;

    public Customer(int id, String name, String surname, String telephoneNumber) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
    }

    public Customer(int id, String name, String surname, String telephoneNumber,
                    Address address) {
        this(id, name, surname, telephoneNumber);
        this.address = address;
    }

    private class Address {

        private String country;
        private String city;
        private String street;
        private String homeNumber;
        private String flatNumber;

        public Address () {}

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

        public void setCity(String city) {
            this.city = city;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setFlatNumber(String flatNumber) {
            this.flatNumber = flatNumber;
        }

        public void setHomeNumber(String homeNumber) {
            this.homeNumber = homeNumber;
        }

        @Override
        public String toString() {
            return String.format(Locale.ENGLISH, "%s;%s;%s;%s", country, street, homeNumber,flatNumber);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setAddress(String country, String city, String street,
                           String homeNumber, String flatNumber) {
        this.address = new Address(country, city, street, homeNumber, flatNumber);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%d;%s;%s;%s;%s",
                getId(), name, surname, telephoneNumber,address);
    }
}