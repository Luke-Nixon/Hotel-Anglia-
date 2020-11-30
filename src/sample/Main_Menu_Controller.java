package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_Menu_Controller
{
    public Button admin;
    public Hotel_Manager hotel;
    public Stage window;
    public DB_Manager db_manager;


    // runs when the admin button is clicked
    public void admin_button_click(ActionEvent event) throws IOException
    {
        // loads the admin_login_page.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_login_page.fxml"));
        Parent parent = loader.load();
        // gets the controller for the login page
        Admin_Login_Page_Controller admin_login_page = loader.getController();
        // sets the hotel and window for the login page
        admin_login_page.hotel = hotel;
        admin_login_page.window = window;
        // show the new scene
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();
    }
    // runs when the new booking button is pressed
    public void new_booking_click(ActionEvent event) throws IOException
    {
        // load the new_booking_page.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new_booking_page.fxml"));
        Parent parent = loader.load();
        // load the controller
        NewBookingPage new_booking_page = loader.getController();
        // set the hotel and window fields of the controller
        new_booking_page.hotel = hotel;
        new_booking_page.window = window;
        // run the set_combobox method
        new_booking_page.set_combobox();
        // show the scene
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();
    }
    // runs when the cancel booking button is pressed.
    public void cancel_booking_click(ActionEvent event) throws IOException
    {
        // load the cancel_booking.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cancel_booking.fxml"));
        Parent parent = loader.load();
        // get the cancel booking controller
         Cancel_Booking cancel_booking = loader.getController();
        // set the hotel and window for the controller
        cancel_booking.hotel = hotel;
        cancel_booking.window = window;
        // show the new scene
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();

    }

}
