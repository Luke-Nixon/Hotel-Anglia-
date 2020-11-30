package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin_Menu_Controller
{

    public Stage window;
    public Hotel_Manager hotel;

    public Label password_indicator;
    public PasswordField change_password;
    public PasswordField confirm_password;

    public TableView bookings_table;
    public ObservableList<Booking_Data> table_view_list = FXCollections.observableArrayList();

    public TableView checking_table;
    public ObservableList<Checking_Data> checking_table_list = FXCollections.observableArrayList();

    public TableView customer_select_table;
    public ObservableList<Checking_Data> customer_select_table_list = FXCollections.observableArrayList();

    public TableView service_table;
    public ObservableList<Service_Data> service_table_list = FXCollections.observableArrayList();

    public TableView payment_select_table;
    public ObservableList<Checking_Data> payment_select_table_list = FXCollections.observableArrayList();


    public TextField service_description;
    public TextField service_cost;
    public TextArea receipt_box;
    public Label monthly_revenue;

    public TextField single_cost;
    public TextField double_cost;
    public TextField executive_cost;
    public TextField presidential_cost;


    String receipt_string = "";

    // used to reload the cost of each room and setup the cost.
    public void load()
    {
        // set the text of the monthly revenue label to be of the updated monthly revenue from the hotel.
        monthly_revenue.setText("Monthly Revenue: " + String.valueOf( hotel.monthly_revenue ) );

        // set the cost displayed in each label for the rooms to be of the cost of each room in the hotel.
        single_cost.setText(Float.toString(hotel.single_room_cost));
        double_cost.setText(Float.toString(hotel.double_room_cost));
        executive_cost.setText( Float.toString(hotel.executive_room_cost) );
        presidential_cost.setText(Float.toString( hotel.presidential_room_cost) );

    }

    public void initialize()
    {

    }

    // runs when the change password button is pressed.
    public void change_password_button_click(ActionEvent event)
    {
        // if the value of the two password fields is equal and also not empty.
        if ( change_password.getText().equals(confirm_password.getText()) && change_password.getText().length() > 0)
        {
            // update the stored password with the new password from the password field.
            hotel.admin_password = change_password.getText();

            // change the inicator label to notify the user of a successful password change.
            password_indicator.setText("Password change confirmed.");
            password_indicator.setTextFill(Color.web("#00FF00"));
        }
        else
        {   // if the passwords were not matched or empty, then notify the user by changing the indicator label.
            password_indicator.setText("Passwords did not match!");
            password_indicator.setTextFill(Color.web("#FF0000"));
        }

    }

    // runs when the main menu button is pressed.
    public void main_menu_click(ActionEvent event) throws IOException
    {
        // load the main menu fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent parent = loader.load();
        // get the controller of the main menu
        Main_Menu_Controller main_menu = loader.getController();
        // set the hotel and window of the controller.
        main_menu.window = window;
        main_menu.hotel = this.hotel;
        // set the scene to the new scene.
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();
    }



    // runs when the load bookings button is pressed
    public void load_bookings(ActionEvent event) throws SQLException {
        // this SQL query retrieves all the bookings from the bookings table and also gets the corresponding room they are booked in.
        ResultSet all_bookings = hotel.DB.manipulate("SELECT t2.first_name, t2.last_name, t3.room_type , t.check_in_date,t.check_out_date, t.customer_ID, t.booking_ID, t.room_ID FROM bookings AS t INNER JOIN customers AS t2 ON t.customer_id = t2.customer_id INNER JOIN rooms  As t3 ON t.room_id = t3.room_id");
        // clear the bookings table so new data can be set.
        bookings_table.getItems().clear();
        // loop through the result set
        while(all_bookings.next())
        {
            // each colum of the result set, set to a corresponding variable for the new table.
            String first_name = String.valueOf(all_bookings.getString(1));
            String last_name = String.valueOf(all_bookings.getString(2));
            String room_type = String.valueOf(all_bookings.getString(3));
            String check_in_date =  String.valueOf(all_bookings.getDate(4));
            String check_out_date =  String.valueOf(all_bookings.getDate(5));
            String customer_id = String.valueOf(all_bookings.getInt(6));
            String booking_id = String.valueOf(all_bookings.getInt(7));
            String room_id = String.valueOf(all_bookings.getInt(8));

            // create a new "Booking_Data" class and set each field to be one of the above variables. then add it to the observable list of the table.
            table_view_list.add(new Booking_Data(first_name,last_name,room_type,check_in_date,check_out_date,customer_id,booking_id,room_id));
        }
        // once the iteration is complete, set the table to be the observable list.
        bookings_table.setItems(table_view_list);
    }
    // runs when the remove booking button is pressed.
    public void on_remove_booking(ActionEvent event) throws SQLException {

        // get the current selected row of the bookings talbe, and store it as a variable.
        Object selected_row = bookings_table.getSelectionModel().getSelectedItem();

        // if the user has selected an entry in the bookings table
       if ( selected_row  != null && selected_row instanceof Booking_Data)
       {
           // delete the booking
           hotel.DB.manipulate("DELETE FROM bookings WHERE booking_ID =" + Integer.valueOf(((Booking_Data) selected_row).booking_id));

           // and delete the services
           hotel.DB.manipulate("DELETE FROM services WHERE customer_ID = " + Integer.valueOf(((Booking_Data) selected_row).customer_id));

           // and delete the customer
           hotel.DB.manipulate("DELETE FROM customers WHERE customer_ID =" + Integer.valueOf(((Booking_Data) selected_row).customer_id));

           //update the table
           this.load_bookings(null);

       }


    }

    // runs when the load customers button is pressed
    public void load_customers(ActionEvent event) throws SQLException {

        // this SQL query retrieves all the customers from the bookings table and also gets the corresponding room they are booked in.
        ResultSet checking_data = hotel.DB.manipulate("SELECT t2.first_name, t2.last_name, t3.room_type,t.room_ID ,t3.status, t2.customer_id, t.booking_id FROM bookings AS t INNER JOIN customers AS t2 ON t.customer_id = t2.customer_id INNER JOIN rooms  As t3 ON t.room_id = t3.room_id");

        // clear the checking table so new data can be set
        checking_table.getItems().clear();

        // loop through the result set from the query.
        while(checking_data.next())
        {
            // each colum of the result set, set to a corresponding variable for the new table.
            String first_name = String.valueOf(checking_data.getString(1));
            String last_name = String.valueOf(checking_data.getString(2));
            String room_type = String.valueOf(checking_data.getString(3));
            String room_id = String.valueOf(checking_data.getInt(4));
            String status = String.valueOf(checking_data.getString(5));

            String customer_id = String.valueOf(checking_data.getInt(6));;
            String booking_id = String.valueOf(checking_data.getInt(7));;

            // set the variables of a new checking_class object to be of the variables above. and add them to a observable list
            checking_table_list.add(new Checking_Data(first_name,last_name,room_type,room_id,status,customer_id,booking_id));
        }
        // set the items of the checking table to be of the observable list.
        checking_table.setItems(checking_table_list);
    }

    // runs when the check_in button is pressed
    public void check_in(ActionEvent event) throws SQLException {

        // get the selected from the checking table
        Object selected_row = checking_table.getSelectionModel().getSelectedItem();

        // if the selected row is not null and it is checking data
        if ( selected_row  != null && selected_row instanceof Checking_Data)

        {
            // set the status of the room to occupied
            hotel.DB.manipulate("UPDATE rooms SET status = 'occupied' WHERE room_id = '" + ((Checking_Data) selected_row).check_in_room_id + "'");
        }

        // run the load_customers method, to update the table
        load_customers(null);

    }

    // runs when the check_out button is pressed
    public void check_out(ActionEvent event) throws SQLException {

        // get the selected row of the checking_table.
        Object selected_row = checking_table.getSelectionModel().getSelectedItem();

        // if the selected row is not null and it is Checking_Data
        if ( selected_row  != null && selected_row instanceof Checking_Data)

        {
            // set the status of the room to occupied
            hotel.DB.manipulate("UPDATE rooms SET status = 'empty' WHERE room_id = '" + ((Checking_Data) selected_row).check_in_room_id + "'");
        }
        // reload the customers so that the table is updated.
        load_customers(null);

    }

    // runs when the load customers button is pressed in the services tab
    public void service_load_customers(ActionEvent event) throws SQLException {

        // get all of the customers in the customers database.
        ResultSet customer_data = hotel.DB.manipulate("SELECT customer_id , first_name , last_name FROM customers");

        // clear the customer_select_table
        customer_select_table.getItems().clear();

        // iterate through the result set
        while(customer_data.next())
        {
            // each colum in the result set, save to a corresponding variable.
            String customer_id = String.valueOf(customer_data.getInt(1));;
            String first_name = String.valueOf(customer_data.getString(2));
            String last_name = String.valueOf(customer_data.getString(3));

            // set the observable list to be of the corresponding variables using a new checking data class, but any missing fields set to empty strings.
            customer_select_table_list.add(new Checking_Data(first_name,last_name,"","","",customer_id,""));
        }
        // set the table to be the contents of the observable list.
        customer_select_table.setItems(customer_select_table_list);


    }

    // runs when the add service button is pressed
    public void add_service(ActionEvent event) throws SQLException {

        // if the description field has data and so does the service cost field.
        if (service_description.getText().length() > 0 && service_cost.getText().length() > 0) {
            // get the selected row of the customers table
            Object selected_row = customer_select_table.getSelectionModel().getSelectedItem();
            // insert the new service data from the fields into the services SQL table
            hotel.DB.manipulate("INSERT INTO services (service_ID,customer_ID,service_type,cost) VALUES(DEFAULT, " + ((Checking_Data) selected_row).check_in_customer_id + ",'" + service_description.getText() + "'," + service_cost.getText() + ")");

            // update the services table to display the new insert

            // clear the service table
            service_table.getItems().clear();

            // if the selected row is not null and is checking_data
            if (selected_row != null && selected_row instanceof Checking_Data) // if the user has selected an entry in customers table
            {
                // get all services of that user
                ResultSet service_data = hotel.DB.manipulate("SELECT * FROM services WHERE customer_id = " + ((Checking_Data) selected_row).check_in_customer_id);

                // iterate through the result set
                while (service_data.next()) {

                    // set the corresponding colums to variables.
                    String customer_id = String.valueOf(service_data.getInt(2));
                    String service_type = String.valueOf(service_data.getString(3));
                    String cost = String.valueOf(service_data.getString(4));

                    // add each variable to a new Service data class and add it to the observable list.
                    service_table_list.add(new Service_Data(customer_id, service_type, cost));
                }

                // set the service_table to the observablelist data
                service_table.setItems(service_table_list);


            }
        }
    }

    // runs when the customers table is clicked
    public void customers_clicked(MouseEvent mouseEvent) throws SQLException {

        // get the selected row that was clicked.
        Object selected_row = customer_select_table.getSelectionModel().getSelectedItem();

        // clear service table
        service_table.getItems().clear();

        // if the selected row is not null and is an instance of checking_data
        if ( selected_row  != null && selected_row instanceof Checking_Data) // if the user has selected an entry in customers table
        {
            // get all services of that user
            ResultSet service_data = hotel.DB.manipulate("SELECT * FROM services WHERE customer_id = " + ((Checking_Data) selected_row).check_in_customer_id);

            // iterate through the result set
            while(service_data.next())
            {
                // set the corresponding colums in the result set to variables.
                String customer_id = String.valueOf(service_data.getInt(2));;
                String service_type = String.valueOf(service_data.getString(3));
                String cost = String.valueOf(service_data.getString(4));

                // add the variables to an observable list with a New service_Data class
                service_table_list.add(new Service_Data(customer_id,service_type,cost));
            }
            // set the contents of the table to be that of the observable list
            service_table.setItems(service_table_list);
        }
    }

    // runs when the load customers button is pressed in the payment tab
    public void load_payment_customers(ActionEvent event) throws SQLException {

        // get all customers in the DB
        ResultSet customer_data = hotel.DB.manipulate("SELECT customer_id , first_name , last_name FROM customers");

        // clear the payments table
        payment_select_table.getItems().clear();

        // iterate through the result set
        while(customer_data.next())
        {
            // each colum in the result set save to a variable.
            String customer_id = String.valueOf(customer_data.getInt(1));;
            String first_name = String.valueOf(customer_data.getString(2));
            String last_name = String.valueOf(customer_data.getString(3));

            // add the variables to a observable list using a new Checking_Data class, but where missing variables are set to empty strings.
            payment_select_table_list.add(new Checking_Data(first_name,last_name,"","","",customer_id,""));
        }

        // set the table to be the contents of the observable list.
        payment_select_table.setItems(payment_select_table_list);

    }

    // runs when the check out and pay button is pressed
    public void check_out_and_pay(ActionEvent event) throws SQLException {

        // get the selected row in the payments table
        Object selected_row = payment_select_table.getSelectionModel().getSelectedItem();

        // if the selected row is not null
        if (selected_row != null)
        {
            // get all services of that user
            ResultSet service_data = hotel.DB.manipulate("SELECT * FROM services WHERE customer_id = " + ((Checking_Data) selected_row).check_in_customer_id);

            // set the receipt string to be an empty string
            receipt_string = "";
            // set the total to be 0
            float total = 0;

            // iterate through each service in the result set
            while(service_data.next())
            {
                // add each service to the total
               total += service_data.getFloat(4);
               //get the description of the service
               String desc =  service_data.getString(3);
               // get the cost of the service
               String cost = String.valueOf(service_data.getFloat(4));

               // add the cost and description onto the receipt_string
               receipt_string += desc + ":      Cost: " + cost + "\n";

            }

            // add onto the receipt string the total amount of all the services
            receipt_string += "\n TOTAL : " + Float.toString( total );

            // set the text of the receipt box to the receipt string to show the user the total cost
            receipt_box.setText(receipt_string);

            // now that the payment has been processed. we can safely remove all of the customers information from the DB

            // delete the booking
            hotel.DB.manipulate("DELETE FROM bookings WHERE booking_ID =" + Integer.valueOf(((Checking_Data) selected_row).check_in_customer_id));

            // and delete the services
            hotel.DB.manipulate("DELETE FROM services WHERE customer_ID = " + Integer.valueOf(((Checking_Data) selected_row).check_in_customer_id));

            // and delete the customer
            hotel.DB.manipulate("DELETE FROM customers WHERE customer_ID =" + Integer.valueOf(((Checking_Data) selected_row).check_in_customer_id));

            // add the total amount onto the hotels monthly revenue
            hotel.monthly_revenue += total;
            // run the load method to update the costs and prices
            load();

            // show changes in the customer table;
            load_payment_customers(event);

        }

    }

    // runs when the print receipt button is pressed.
    public void print_receipt(ActionEvent event)
    {
        // if the receipt string isnt empty, we can assume that all data has been loaded.
        if (receipt_string != "")
        {
            // print the receipt string to the console.
            System.out.println(receipt_string);
        }
    }

    // runs when the adjust prices button is pressed.
    public void adjust_prices(ActionEvent event)
    {
        // set the cost of each type of room in the hotel to be that of the value inputted into the price fields
        hotel.single_room_cost = Float.parseFloat(single_cost.getText());
        hotel.double_room_cost = Float.parseFloat(double_cost.getText());
        hotel.executive_room_cost = Float.parseFloat(executive_cost.getText());
        hotel.presidential_room_cost = Float.parseFloat(presidential_cost.getText());

    }
}
