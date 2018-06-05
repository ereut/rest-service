package ru.intervale.course.beans;

import java.util.Locale;

public class Customer extends AbstractEntity {

    private String name;
    private String surname;
    private String telephoneNumber;
    private Address address;

    public Customer() {
        super();
        this.address = new Customer.Address();
    }

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
        public Address() {};

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
            return String.format(Locale.ENGLISH, "%s;%s;%s;%s;%s", country, city,street,
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
        return String.format(Locale.ENGLISH, "|%-5d|%-10s|%-15s|%-12s|%-45s|",
                getId(), name, surname, telephoneNumber,address);
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