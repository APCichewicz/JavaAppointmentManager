package com.example.wgusoftware2scheduler.Models;
/**
 * A model class that represents a country with an ID and name.
 */
public class Country {
    private int ID;
    private String name;
    /**
     * Creates a new Country object with the specified ID and name.
     *
     * @param ID the ID of the country
     * @param name the name of the country
     */
    public Country(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    /**
     * Gets the ID of the country.
     *
     * @return the ID of the country
     */
    public int getID() {
        return ID;
    }
    /**
     * Gets the name of the country.
     *
     * @return the name of the country
     */
    public String getName() {
        return name;
    }
}
