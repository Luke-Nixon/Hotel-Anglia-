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

    public void load()
    {
        monthly_revenue.setText("Monthly Revenue: " + String.valueOf( hotel.monthly_revenue ) );

        single_cost.setText(Float.toString(hotel.single_room_cost));
        double_cost.setText(Float.toString(hotel.double_room_cost));
        executive_cost.setText( Float.toString(hotel.executive_room_cost) );
        presidential_cost.setText(Float.toString( hotel.presidential_room_cost) );

    }

    public void initialize()
    {

    }
    public void change_password_button_click(ActionEvent event)
    {
        if ( change_password.getText().equals(confirm_password.getText()) && change_password.getText().length() > 0)
        {
            hotel.admin_password = change_password.getText();

            password_indicator.setText("Password change confirmed.");
            password_indicator.setTextFill(Color.web("#00FF00"));
        }
        else
        {
            password_indicator.setText("Passwords did not match!");
            password_indicator.setTextFill(Color.web("#FF0000"));
        }

    }

    public void main_menu_click(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent parent = loader.load();

        Main_Menu_Controller main_menu = loader.getController();

        main_menu.window = window;
        main_menu.hotel = this.hotel;

        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();
    }




    public void load_bookings(ActionEvent event) throws SQLException {
        ResultSet all_bookings = hotel.DB.manipulate("SELECT t2.first_name, t2.last_name, t3.room_type , t.check_in_date,t.check_out_date, t.customer_ID, t.booking_ID, t.room_ID FROM bookings AS t INNER JOIN customers AS t2 ON t.customer_id = t2.customer_id INNER JOIN rooms  As t3 ON t.room_id = t3.room_id");

        bookings_table.getItems().clear();

        while(all_bookings.next())
        {

            String first_name = String.valueOf(all_bookings.getString(1));
            String last_name = String.valueOf(all_bookings.getString(2));
            String room_type = String.valueOf(all_bookings.getString(3));
            String check_in_date =  String.valueOf(all_bookings.getDate(4));
            String check_out_date =  String.valueOf(all_bookings.getDate(5));
            String customer_id = String.valueOf(all_bookings.getInt(6));
            String booking_id = String.valueOf(all_bookings.getInt(7));
            String room_id = String.valueOf(all_bookings.getInt(8));


            table_view_list.add(new Booking_Data(first_name,last_name,room_type,check_in_date,check_out_date,customer_id,booking_id,room_id));
        }

        bookings_table.setItems(table_view_list);
    }

    public void on_remove_booking(ActionEvent event) throws SQLException {

        Object selected_row = bookings_table.getSelectionModel().getSelectedItem();

       if ( selected_row  != null && selected_row instanceof Booking_Data) // if the user has selected an entry in the bookings table
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

    public void load_customers(ActionEvent event) throws SQLException {
        // load all customers

        ResultSet checking_data = hotel.DB.manipulate("SELECT t2.first_name, t2.last_name, t3.room_type,t.room_ID ,t3.status, t2.customer_id, t.booking_id FROM bookings AS t INNER JOIN customers AS t2 ON t.customer_id = t2.customer_id INNER JOIN rooms  As t3 ON t.room_id = t3.room_id");

        checking_table.getItems().clear();

        while(checking_data.next())
        {
            String first_name = String.valueOf(checking_data.getString(1));
            String last_name = String.valueOf(checking_data.getString(2));
            String room_type = String.valueOf(checking_data.getString(3));
            String room_id = String.valueOf(checking_data.getInt(4));
            String status = String.valueOf(checking_data.getString(5));

            String customer_id = String.valueOf(checking_data.getInt(6));;
            String booking_id = String.valueOf(checking_data.getInt(7));;


            checking_table_list.add(new Checking_Data(first_name,last_name,room_type,room_id,status,customer_id,booking_id));
        }

        checking_table.setItems(checking_table_list);
    }

    public void check_in(ActionEvent event) throws SQLException {
        Object selected_row = checking_table.getSelectionModel().getSelectedItem();

        if ( selected_row  != null && selected_row instanceof Checking_Data) // if the user has selected an entry in the bookings table

        {
            // set the status of the room to occupied
            hotel.DB.manipulate("UPDATE rooms SET status = 'occupied' WHERE room_id = '" + ((Checking_Data) selected_row).check_in_room_id + "'");
        }

        load_customers(null);

    }

    public void check_out(ActionEvent event) throws SQLException {

        Object selected_row = checking_table.getSelectionModel().getSelectedItem();

        if ( selected_row  != null && selected_row instanceof Checking_Data) // if the user has selected an entry in the bookings table

        {
            // set the status of the room to occupied
            hotel.DB.manipulate("UPDATE rooms SET status = 'empty' WHERE room_id = '" + ((Checking_Data) selected_row).check_in_room_id + "'");
        }

        load_customers(null);

    }

    public void service_load_customers(ActionEvent event) throws SQLException {
        // load all customers for services

        ResultSet customer_data = hotel.DB.manipulate("SELECT customer_id , first_name , last_name FROM customers");

        customer_select_table.getItems().clear();

        while(customer_data.next())
        {
            String customer_id = String.valueOf(customer_data.getInt(1));;
            String first_name = String.valueOf(customer_data.getString(2));
            String last_name = String.valueOf(customer_data.getString(3));

            customer_select_table_list.add(new Checking_Data(first_name,last_name,"","","",customer_id,""));
        }

        customer_select_table.setItems(customer_select_table_list);


    }

    public void add_service(ActionEvent event) throws SQLException {
        if (service_description.getText().length() > 0 && service_cost.getText().length() > 0) {
            Object selected_row = customer_select_table.getSelectionModel().getSelectedItem();

            hotel.DB.manipulate("INSERT INTO services (service_ID,customer_ID,service_type,cost) VALUES(DEFAULT, " + ((Checking_Data) selected_row).check_in_customer_id + ",'" + service_description.getText() + "'," + service_cost.getText() + ")");

            // update the services table to display the new insert

            service_table.getItems().clear();

            if (selected_row != null && selected_row instanceof Checking_Data) // if the user has selected an entry in customers table
            {
                // get all services of that user
                ResultSet service_data = hotel.DB.manipulate("SELECT * FROM services WHERE customer_id = " + ((Checking_Data) selected_row).check_in_customer_id);

                while (service_data.next()) {
                    String customer_id = String.valueOf(service_data.getInt(2));
                    ;
                    String service_type = String.valueOf(service_data.getString(3));
                    String cost = String.valueOf(service_data.getString(4));

                    service_table_list.add(new Service_Data(customer_id, service_type, cost));
                }

                service_table.setItems(service_table_list);


            }
        }
    }

    public void customers_clicked(MouseEvent mouseEvent) throws SQLException {


        Object selected_row = customer_select_table.getSelectionModel().getSelectedItem();

        service_table.getItems().clear();

        if ( selected_row  != null && selected_row instanceof Checking_Data) // if the user has selected an entry in customers table
        {
            // get all services of that user
            ResultSet service_data = hotel.DB.manipulate("SELECT * FROM services WHERE customer_id = " + ((Checking_Data) selected_row).check_in_customer_id);

            while(service_data.next())
            {
                String customer_id = String.valueOf(service_data.getInt(2));;
                String service_type = String.valueOf(service_data.getString(3));
                String cost = String.valueOf(service_data.getString(4));

                service_table_list.add(new Service_Data(customer_id,service_type,cost));
            }

            service_table.setItems(service_table_list);




        }
    }

    public void load_payment_customers(ActionEvent event) throws SQLException {
        // load all customers for services

        ResultSet customer_data = hotel.DB.manipulate("SELECT customer_id , first_name , last_name FROM customers");

        payment_select_table.getItems().clear();

        while(customer_data.next())
        {
            String customer_id = String.valueOf(customer_data.getInt(1));;
            String first_name = String.valueOf(customer_data.getString(2));
            String last_name = String.valueOf(customer_data.getString(3));

            payment_select_table_list.add(new Checking_Data(first_name,last_name,"","","",customer_id,""));
        }

        payment_select_table.setItems(payment_select_table_list);

    }


    public void check_out_and_pay(ActionEvent event) throws SQLException {

        Object selected_row = payment_select_table.getSelectionModel().getSelectedItem();

        if (selected_row != null)
        {
            // get all services of that user
            ResultSet service_data = hotel.DB.manipulate("SELECT * FROM services WHERE customer_id = " + ((Checking_Data) selected_row).check_in_customer_id);

            receipt_string = "";
            float total = 0;


            while(service_data.next())
            {
               total += service_data.getFloat(4);
               String desc =  service_data.getString(3);
               String cost = String.valueOf(service_data.getFloat(4));

               receipt_string += desc + ":      Cost: " + cost + "\n";

            }

            receipt_string += "\n TOTAL : " + Float.toString( total );

            receipt_box.setText(receipt_string);

            // now that the payment has been processed. we can safely remove all of the customers information from the DB

            // delete the booking
            hotel.DB.manipulate("DELETE FROM bookings WHERE booking_ID =" + Integer.valueOf(((Checking_Data) selected_row).check_in_customer_id));

            // and delete the services
            hotel.DB.manipulate("DELETE FROM services WHERE customer_ID = " + Integer.valueOf(((Checking_Data) selected_row).check_in_customer_id));

            // and delete the customer
            hotel.DB.manipulate("DELETE FROM customers WHERE customer_ID =" + Integer.valueOf(((Checking_Data) selected_row).check_in_customer_id));

            hotel.monthly_revenue += total;
            load();

            // show changes in the customer table;
            load_payment_customers(event);

        }

    }

    public void print_receipt(ActionEvent event)
    {
        // if the recipt string isnt empty, we can assume that all data has been loaded
        if (receipt_string != "")
        {
            System.out.println(receipt_string);
        }
    }


    public void adjust_prices(ActionEvent event)
    {
        hotel.single_room_cost = Float.parseFloat(single_cost.getText());
        hotel.double_room_cost = Float.parseFloat(double_cost.getText());
        hotel.executive_room_cost = Float.parseFloat(executive_cost.getText());
        hotel.presidential_room_cost = Float.parseFloat(presidential_cost.getText());

    }
}
