package com.example.wgusoftware2scheduler.Models;
/**

 A data object to represent a subdivision of a first-level-division aka country

 It has three instance variables to store the ID, name, and country ID of the division.

 It also provides getter methods to access these instance variables.
 */
public class Division {
    private int ID;
    private String name;
    private int countryID;
    /**

     Constructor to initialize the instance variables.
     @param ID the ID of the division
     @param name the name of the division
     @param countryID the country ID of the division
     */
    public Division(int ID, String name, int countryID) {
        this.ID = ID;
        this.name = name;
        this.countryID = countryID;
    }
    /**

     Getter method to retrieve the ID of the division
     @return the ID of the division
     */
    public int getID() {
        return ID;
    }
    /**

     Getter method to retrieve the name of the division
     @return the name of the division
     */
    public String getName() {
        return name;
    }
    /**

     Getter method to retrieve the country ID of the division
     @return the country ID of the division
     */
    public int getCountryID() {
        return countryID;
    }

}
