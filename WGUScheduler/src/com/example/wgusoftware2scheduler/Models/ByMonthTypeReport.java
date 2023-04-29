package com.example.wgusoftware2scheduler.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * A model class that represents a report of the total number of appointments
 * of a certain type in a certain month.
 */
public class ByMonthTypeReport {

    private StringProperty type;
    private StringProperty month;
    private IntegerProperty total;
    /**
     * Creates a new ByMonthTypeReport object with the specified type, month, and total.
     *
     * @param type the type of the appointments
     * @param month the month of the appointments
     * @param total the total number of appointments of the given type in the given month
     */
    public ByMonthTypeReport( String type, String month, int total) {

        this.type = new SimpleStringProperty(type);
        this.month = new SimpleStringProperty(month);
        this.total = new SimpleIntegerProperty(total);
    }
    /**
     * Gets the type property of the ByMonthTypeReport.
     *
     * @return the type property
     */
    public StringProperty typeProperty() {
        return type;
    }
    /**
     * Gets the month property of the ByMonthTypeReport.
     *
     * @return the month property
     */
    public StringProperty monthProperty() {
        return month;
    }
    /**
     * Gets the total property of the ByMonthTypeReport.
     *
     * @return the total property
     */
    public IntegerProperty totalProperty() {
        return total;
    }
}
