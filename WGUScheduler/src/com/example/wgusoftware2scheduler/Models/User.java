package com.example.wgusoftware2scheduler.Models;

import java.time.LocalDate;
/**
 Class representing a User.
 */
public class User {
    private int ID;
    private String name;
    private String password;
    private LocalDate createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdatedBy;
    /**
     * Constructor for creating a new User object with all the attributes specified.
     *
     * @param ID the ID of the user
     * @param name the name of the user
     * @param password the password of the user
     * @param createDate the date the user was created
     * @param createdBy the user who created this user
     * @param lastUpdate the last update date for this user
     * @param lastUpdatedBy the user who made the last update
     */
    public User(int ID, String name, String password, LocalDate createDate, String createdBy,
            LocalDate lastUpdate, String lastUpdatedBy) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * Default constructor for creating a new User object with default attributes.
     */
    public User() {
        this.ID = 0;
        this.name = "";
        this.password = "";
        this.createDate = LocalDate.now();
        this.createdBy = "";
        this.lastUpdate = LocalDate.now();
        this.lastUpdatedBy = "";
    }
    /**
     * Set the ID of the user.
     *
     * @param ID the ID of the user
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Set the name of the user.
     *
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Set the password of the user.
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Get the ID of the user.
     *
     * @return the ID of the user
     */
    public int getID() {
        return ID;
    }
    /**
     * Get the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }


}
