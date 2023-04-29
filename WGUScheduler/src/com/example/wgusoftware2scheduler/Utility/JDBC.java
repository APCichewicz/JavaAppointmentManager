package com.example.wgusoftware2scheduler.Utility;

import com.example.wgusoftware2scheduler.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.temporal.ChronoUnit;
/**
 * JDBC class provides a singleton instance of a database connection, making it easier to reuse the connection
 * for multiple purposes. The class uses the MySQL JDBC driver to connect to a database and perform various
 * operations like authentication, fetching customer and appointment information, adding, updating, and deleting
 * customers, etc.
 */
public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost/";
    private static final String dbName = "client_schedule";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone = SERVER";
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    private static Connection conn = null;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    public static JDBC instance = null;
    /**
     * Private constructor to prevent instantiation of the JDBC class
     */
    private JDBC() {

    }
    /**
     * Gets the singleton instance of the JDBC class
     *
     * @return the singleton instance of the JDBC class
     */
    public static JDBC getInstance() {
        if (instance == null) {
            synchronized (JDBC.class) {
                if (instance == null) {
                    instance = new JDBC();
                }
            }
        }
        return instance;
    }
    /**
     * Makes a database connection using the JDBC URL, username, and password
     */
    public static void makeConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
        } catch (ClassNotFoundException e) {
            Popups.error("Error: ", e.getMessage());
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        }
    }
    /**
     * Gets the database connection
     *
     * @return the database connection
     */
    public static Connection getConnection() {
        return conn;
    }
    /**
     * Closes the database connection
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        }
    }

    /**
     * Authenticates a user using the given username and password
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the authentication was successful, false otherwise
     */
    public static boolean authenticate(String username, String password) {
        boolean result = false;
        makeConnection();
        try {
            statement = conn.prepareStatement("SELECT * FROM users WHERE User_Name = ?");
            statement.setString(1, username.strip());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (password.strip().equals(resultSet.getString("Password").strip())) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    /**

     Returns a list of all the customers in the database.
     @return an ObservableList of Customer objects, representing all the customers in the database.
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement("select * from customers\n" +
                    "join first_level_divisions fld\n" +
                    "on fld.Division_ID = customers.Division_ID\n" +
                    "join countries\n" +
                    "on countries.Country_ID = fld.Country_ID");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer c = new Customer(
                        resultSet.getInt("customer_Id"),
                        resultSet.getString("customer_Name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        new Division(resultSet.getInt("division_Id"),
                                resultSet.getString("Division"),
                                resultSet.getInt("country_Id")),
                        resultSet.getString("postal_Code"));
                list.add(c);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }

        return list;
    }
    /**

     Returns a list of all the appointments in the database.
     @return an ObservableList of Appointment objects, representing all the appointments in the database.
     */
    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement("SELECT * FROM appointments");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Appointment a = new Appointment();
                a.setID(resultSet.getInt("appointment_Id"));
                a.setCustomerID(resultSet.getInt("customer_Id"));
                a.setUserID(resultSet.getInt("user_Id"));
                a.setTitle(resultSet.getString("title"));
                a.setDescription(resultSet.getString("description"));
                a.setLocation(resultSet.getString("location"));
                a.setContactID(resultSet.getInt("contact_ID"));
                a.setType(resultSet.getString("type"));
                a.setStart(TimeHandler.convertUTCtoLocal(resultSet.getTimestamp("start").toLocalDateTime()));
                a.setEnd(TimeHandler.convertUTCtoLocal(resultSet.getTimestamp("end").toLocalDateTime()));
                list.add(a);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return list;
    }
    /**

     Adds a new customer to the database.
     @param name the name of the customer to add
     @param address the address of the customer to add
     @param postalCode the postal code of the customer to add
     @param phone the phone number of the customer to add
     @param divisionId the division ID of the customer to add
     @return the number of rows affected by the INSERT statement, typically 1
     */
    public static int addCustomer(String name, String address, String postalCode, String phone, int divisionId){
        int result = 0;
        makeConnection();
        try {
            statement = conn.prepareStatement(
                    "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Created_by, Create_date, Last_Updated_By, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, postalCode);
            statement.setString(4, phone);
            statement.setInt(5, divisionId);
            statement.setInt(6, CurrentUser.getUserID());
            statement.setTimestamp(7, Timestamp.valueOf(TimeHandler.getNowUTC().truncatedTo(ChronoUnit.SECONDS)));
            statement.setInt(8, CurrentUser.getUserID());
            statement.setTimestamp(9, Timestamp.valueOf(TimeHandler.getNowUTC().truncatedTo(ChronoUnit.SECONDS)));
            result = statement.executeUpdate();
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    /**

     Updates the customer with the given id in the database with the new name, address, postal code, phone, and divisionId.
     @param id The id of the customer to be updated.
     @param name The new name of the customer.
     @param address The new address of the customer.
     @param postalCode The new postal code of the customer.
     @param phone The new phone of the customer.
     @param divisionId The new division id of the customer.
     @return The number of rows affected by the update statement.
     */
    public static int updateCustomer(int id, String name, String address, String postalCode, String phone,
            int divisionId) {
        int result = 0;
        makeConnection();
        try {
            statement = conn.prepareStatement(
                    "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_updated_by = ?, Last_update = ? WHERE Customer_ID = ?");
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, postalCode);
            statement.setString(4, phone);
            statement.setInt(5, divisionId);
            statement.setInt(6, CurrentUser.getUserID());
            statement.setTimestamp(7, Timestamp.valueOf(TimeHandler.getNowUTC().truncatedTo(ChronoUnit.SECONDS)));
            statement.setInt(8, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    /**

     Updates an existing customer record in the database with the provided information from the given Customer object.
     @param c Customer object that contains the updated information for the customer record
     @return an integer indicating the number of rows affected by the update operation
     */
    public static int updateCustomer(Customer c) {
        return updateCustomer(c.getID(), c.getName(), c.getAddress(), c.getPostalCode(), c.getPhone(),
                c.getDivision().getID());
    }
    /**

     Deletes a customer from the database using the specified customer ID.
     @param id The ID of the customer to be deleted.
     @return An integer representing the number of rows affected by the deletion operation.
     */
    public static int deleteCustomer(int id) {
        int result = 0;
        makeConnection();
        try {
            statement = conn.prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
            return result;
        } finally {
            closeConnection();
        }
        return result;
    }
    /**

     Adds a new appointment to the database.
     @param title Title of the appointment
     @param description Description of the appointment
     @param location Location of the appointment
     @param type Type of the appointment
     @param start Start time of the appointment
     @param end End time of the appointment
     @param customerId ID of the customer associated with the appointment
     @param userId ID of the user associated with the appointment
     @param contact ID of the contact associated with the appointment
     @return the number of rows affected by the execution of the SQL statement
     */
    public static <LocalDateTime> int addAppointment(String title, String description, String location, String type,
            Timestamp start, Timestamp end, int customerId, int userId, int contact) {
        int result = 0;
        makeConnection();
        try {
            statement = conn.prepareStatement(
                    "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, Create_date, Created_By, Last_Updated_By, Last_Update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setString(4, type);
            statement.setTimestamp(5, start);
            statement.setTimestamp(6, end);
            statement.setInt(7, customerId);
            statement.setInt(8, userId);
            statement.setInt(9, contact);
            statement.setTimestamp(10, Timestamp.valueOf(TimeHandler.getNowUTC().truncatedTo(ChronoUnit.SECONDS)));
            statement.setInt(11, CurrentUser.getUserID());
            statement.setInt(12, CurrentUser.getUserID());
            statement.setTimestamp(13, Timestamp.valueOf(TimeHandler.getNowUTC().truncatedTo(ChronoUnit.SECONDS)));
            result = statement.executeUpdate();
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    /**

     Updates an appointment in the database with the given parameters.
     @param id the id of the appointment to update
     @param title the new title of the appointment
     @param description the new description of the appointment
     @param location the new location of the appointment
     @param type the new type of the appointment
     @param start the new start time of the appointment
     @param end the new end time of the appointment
     @param customerId the id of the customer associated with the appointment
     @param userId the id of the user associated with the appointment
     @param contact the id of the contact associated with the appointment
     @return the number of rows affected by the update statement
     */
    public static int updateAppointment(int id, String title, String description, String location, String type,
            Timestamp start, Timestamp end, int customerId, int userId, int contact) {
        int result = 0;
        makeConnection();
        try {
            statement = conn.prepareStatement(
                    "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, last_updated_by = ?, last_update = ? WHERE Appointment_ID = ?");
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setString(4, type);
            statement.setTimestamp(5, start);
            statement.setTimestamp(6, end);
            statement.setInt(7, customerId);
            statement.setInt(8, userId);
            statement.setInt(9, contact);
            statement.setInt(10, CurrentUser.getUserID());
            statement.setTimestamp(11, Timestamp.valueOf(TimeHandler.getNowUTC().truncatedTo(ChronoUnit.SECONDS)));
            statement.setInt(12, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    /**

     Deletes an appointment from the database based on its unique id.
     @param id the id of the appointment to be deleted
     @return the number of rows affected by the query
     */
    public static int deleteAppointment(int id) {
        int result = 0;
        makeConnection();
        try {
            statement = conn.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
            return result;
        } finally {
            closeConnection();
        }
        return result;
    }
    /**

     Retrieves a list of all divisions from the database.
     @return an ObservableList of Division objects representing the divisions in the database.
     */
    public static ObservableList<Division> getDivisions() {
        ObservableList<Division> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement(
                    "SELECT Division_ID, Division, Country_ID FROM first_level_divisions");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Division div = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("division"),
                        resultSet.getInt("country_id")
                );
                list.add(div);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return list;
    }
    /**

     This method retrieves a list of all the contacts from the database.
     @return an ObservableList of Contact objects representing all the contacts in the database.
     */
    public static ObservableList<Contact> getContacts() {
        ObservableList<Contact> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement("SELECT * FROM contacts");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact c = new Contact();
                c.setID(resultSet.getInt("contact_Id"));
                c.setName(resultSet.getString("contact_Name"));
                c.setEmail(resultSet.getString("email"));
                list.add(c);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return list;
    }
    /**

     This method retrieves a list of all countries from the database.
     @return An ObservableList of Country objects.
     */
    public static ObservableList<Country> getCountries() {
        ObservableList<Country> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement("SELECT country_id, country FROM countries");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Country c = new Country(resultSet.getInt("country_Id"), resultSet.getString("country"));
                list.add(c);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return list;
    }


    /**

     This method returns a list of all the users in the database.
     @return a list of all the users in the database as an ObservableList of User objects.
     */
    public static ObservableList<User> getUsers() {
        ObservableList<User> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement("SELECT * FROM users");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User u = new User();
                u.setID(resultSet.getInt("user_id"));
                u.setName(resultSet.getString("user_Name"));
                u.setPassword(resultSet.getString("password"));
                list.add(u);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return list;
    }
    /**

     Retrieves the list of all users from the database and returns it as an observable list.
     @return An observable list of User objects representing all users in the database.
     */
    public static ObservableList<ByMonthTypeReport> appointmentByMonthTypeReport() {
        ObservableList<ByMonthTypeReport> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement(
                    "Select Monthname(Start) as Month, Type, count(Appointment_ID) as Total from appointments\n" +
                            "group by Month, Type");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ByMonthTypeReport a = new ByMonthTypeReport(
                        resultSet.getString("type"),
                        resultSet.getString("month"),
                        resultSet.getInt("total"));
                list.add(a);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return list;
    }
/**

 Retrieves a list of HourlyVolumeReport objects from the database.
 The HourlyVolumeReport objects contain information about the time of day, the number of appointments, and the average appointment duration (in minutes) for each hour.
 The information is obtained by executing a SQL query that groups the appointments by hour and calculates the number of appointments and average appointment duration.
 @return an ObservableList of HourlyVolumeReport objects, each representing the data for a specific hour.
 */
    public static ObservableList<HourlyVolumeReport> hourlyVolumeReport() {
        ObservableList<HourlyVolumeReport> list = FXCollections.observableArrayList();
        makeConnection();
        try {
            statement = conn.prepareStatement(
                    "SELECT HOUR(a.Start) AS 'Time of Day', COUNT(*) AS 'Number of Appointments', AVG(TIMESTAMPDIFF(MINUTE, a.Start, a.End)) AS 'Average Appointment Duration (in Minutes)' FROM APPOINTMENTS a GROUP BY HOUR(a.Start) ORDER BY HOUR(a.Start);");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                HourlyVolumeReport a = new HourlyVolumeReport(
                        resultSet.getString("Time of Day"),
                        resultSet.getInt("Number of Appointments"),
                        resultSet.getDouble("Average Appointment Duration (in Minutes)"));
                list.add(a);
            }
        } catch (SQLException e) {
            Popups.error("Error: ", e.getMessage());
        } finally {
            closeConnection();
        }
        return list;
    }
}
