package com.example.wgusoftware2scheduler.Models;

import javafx.beans.property.*;
/**
 * A model class that represents a customer with an ID, name, address, phone, division, and postal code.
 */
public class Customer {

    private IntegerProperty ID;
    private StringProperty name;
    private StringProperty address;
    private StringProperty phone;
    private ObjectProperty<Division> division;
    private StringProperty postalCode;
    /**
     * Creates a new Customer object with the specified ID, name, address, phone, division, and postal code.
     *
     * @param ID the ID of the customer
     * @param name the name of the customer
     * @param address the address of the customer
     * @param phone the phone of the customer
     * @param division the division of the customer
     * @param postalCode the postal code of the customer
     */
    public Customer(int ID, String name, String address, String phone, Division division,
            String postalCode) {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.division = new SimpleObjectProperty<>(division);
        this.postalCode = new SimpleStringProperty(postalCode);
    }
    /**
     * Sets the ID of the customer.
     *
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID.set(ID);
    }
    /**
     * Sets the name of the customer.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * Sets the address of the customer.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }
    /**
     * Sets the phone of the customer.
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    /**
     * Sets the division of the customer.
     *
     * @param division the division to set
     */
    public void setDivision(Division division) {
        this.division.set(division);
    }
    /**
     * Sets the postal code of the customer.
     *
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }
    /**
     * Gets the ID of the customer.
     *
     * @return the ID of the customer
     */
    public int getID() {
        return ID.get();
    }
    /**
     * Gets the name of the customer.
     *
     * @return the name of the customer
     */
    public String getName() {
        return name.get();
    }
    /**
     * Gets the address of the customer.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address.get();
    }
    /**
     * Gets the phone of the customer.
     *
     * @return the phone of the customer
     */
    public String getPhone() {
        return phone.get();
    }
    /**
     * Gets the division of the customer.
     *
     * @return the division of the customer
     */
    public Division getDivision() {
        return division.get();
    }
    /**
     * Gets the postal code of the customer.
     *
     * @return the postal code of the customer
     */
    public String getPostalCode() {
        return postalCode.get();
    }
    /**
     * Gets the ID property of the customer.
     *
     * @return the ID property of the customer
     */
    public IntegerProperty IDProperty() {
        return ID;
    }
    /**
     * Gets the name property of the customer.
     *
     * @return the name property of the customer
     */
    public StringProperty nameProperty() {
        return name;
    }
    /**
     * Gets the address property of the customer.
     *
     * @return the address property of the customer
     */
    public StringProperty addressProperty() {
        return address;
    }
    /**
     * Gets the phone property of the customer.
     *
     * @return the phone property of the customer
     */
    public StringProperty phoneProperty() {
        return phone;
    }
    /**
     * Gets the division property of the customer.
     *
     * @return the division property of the customer
     */
    public ObjectProperty<Division> divisionProperty() {
        return division;
    }
    /**
     * Gets the postal code property of the customer.
     *
     * @return the postal code property of the customer
     */
    public StringProperty postalCodeProperty() {
        return postalCode;
    }
    /**
     * Returns a string representation of the customer.
     *
     * @return a string representation of the customer
     */
    @Override
    public String toString() {
        return "Customer [ID=" + ID.get() + ", \naddress=" + address.get()  + ", \nname=" + name.get()  + ", \nphone="
                + phone.get()  + ", \npostalCode=" + postalCode.get()  + ", \nstateProvince=" + division.get().getName()  + "]";
    }

    /**
     * Validates the customer fields. Throws an {@link IllegalArgumentException} if any of the fields are invalid.
     *
     * @param name the name of the customer
     * @param address the address of the customer
     * @param phone the phone of the customer
     * @param stateProvince the state/province of the customer
     * @param postalCode the postal code of the customer
     *
     * @throws IllegalArgumentException if the name is empty, or if it contains characters other than letters or spaces
     * @throws IllegalArgumentException if the address is empty
     * @throws IllegalArgumentException if the phone is empty or if it is not in the format (xxx) xxx-xxxx
     * @throws IllegalArgumentException if the state/province is empty
     * @throws IllegalArgumentException if the postal code is empty or if it is not in the format xxxxx or xxxxx-xxxx
     */
    public static void isCustomerValid(String name, String address, String phone,
                                       String stateProvince, String postalCode) throws IllegalArgumentException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Name must only contain letters");
        }
        if (address == null || address.length() == 0) {
            throw new IllegalArgumentException("Address must not be empty");
        }
        if (phone == null || phone.length() == 0) {
            throw new IllegalArgumentException("Phone must not be empty");
        }
        if (!phone.matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")) {
            throw new IllegalArgumentException("Phone must be in the format (xxx) xxx-xxxx");
        }
        if (stateProvince == null || stateProvince.length() == 0) {
            throw new IllegalArgumentException("State/Province must not be empty");
        }
        if (postalCode == null || postalCode.length() == 0) {
            throw new IllegalArgumentException("Postal Code must not be empty");
        }
        if (!postalCode.matches("^[0-9]{5}(?:-[0-9]{4})?$")) {
            throw new IllegalArgumentException("Postal Code must be in the format xxxxx or xxxxx-xxxx");
        }
    }
}
