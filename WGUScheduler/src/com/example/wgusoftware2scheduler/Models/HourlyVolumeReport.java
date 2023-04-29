package com.example.wgusoftware2scheduler.Models;

import javafx.beans.property.*;
/**

 The HourlyVolumeReport class represents a report about the number of appointments and the average appointment duration for a specific hour.

 The properties of the report are hour, total, and average. The hour property is a string representation of the hour. The total property is an integer representation of the number of appointments. The average property is a double representation of the average appointment duration in minutes.
 */
public class HourlyVolumeReport {
    private StringProperty hour;
    private IntegerProperty total;
    private DoubleProperty average;
    /**

     Constructs a HourlyVolumeReport object with the given hour, total, and average values.
     @param hour the hour of the report
     @param total the total number of appointments for the hour
     @param average the average appointment duration in minutes for the hour
     */
    public HourlyVolumeReport(String hour, int total, double average){
        this.hour = new SimpleStringProperty(hour);
        this.total = new SimpleIntegerProperty(total);
        this.average = new SimpleDoubleProperty(average);
    }
    /**

     Returns the hour property of the HourlyVolumeReport object.
     @return the hour property of the HourlyVolumeReport object
     */
    public StringProperty hourProperty(){
        return this.hour;
    }
    /**

     Returns the total property of the HourlyVolumeReport object.
     @return the total property of the HourlyVolumeReport object
     */
    public IntegerProperty totalProperty(){
        return this.total;
    }
    /**

     Returns the average property of the HourlyVolumeReport object.
     @return the average property of the HourlyVolumeReport object
     */
    public DoubleProperty averageProperty(){
        return this.average;
    }
}