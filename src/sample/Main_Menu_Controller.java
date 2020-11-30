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

    public void admin_button_click(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_login_page.fxml"));
        Parent parent = loader.load();

        Admin_Login_Page_Controller admin_login_page = loader.getController();

        admin_login_page.hotel = hotel;
        admin_login_page.window = window;
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();
    }

    public void new_booking_click(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new_booking_page.fxml"));
        Parent parent = loader.load();


        NewBookingPage new_booking_page = loader.getController();

        new_booking_page.hotel = hotel;
        new_booking_page.window = window;

        new_booking_page.set_combobox();

        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();
    }

    public void cancel_booking_click(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cancel_booking.fxml"));
        Parent parent = loader.load();


         Cancel_Booking cancel_booking = loader.getController();

        cancel_booking.hotel = hotel;
        cancel_booking.window = window;

        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();

    }

    public void edit_booking_click(ActionEvent event)
    {


    }
}
