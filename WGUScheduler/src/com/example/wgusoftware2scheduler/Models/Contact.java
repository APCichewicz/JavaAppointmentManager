package com.example.wgusoftware2scheduler.Models;
/**
 * A model class that represents a contact with an ID, name, and email.
 */
public class Contact {
    private int ID;
    private String name;
    private String email;
    /**
     * Creates a new Contact object with the specified ID, name, and email.
     *
     * @param ID the ID of the contact
     * @param name the name of the contact
     * @param email the email of the contact
     */
    public Contact(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }
    /**
     * Creates a new empty Contact object.
     */
    public Contact() {
        this.ID = 0;
        this.name = "";
        this.email = "";
    }
    /**
     * Sets the ID of the contact.
     *
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Sets the name of the contact.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the email of the contact.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Gets the ID of the contact.
     *
     * @return the ID of the contact
     */
    public int getID() {
        return ID;
    }
    /**
     * Gets the name of the contact.
     *
     * @return the name of the contact
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the email of the contact.
     *
     * @return the email of the contact
     */
    public String getEmail() {
        return email;
    }
}
