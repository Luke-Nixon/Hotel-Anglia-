package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Cancel_Booking
{
    public Stage window;
    public Hotel_Manager hotel;
    public Label cancellation_status;
    public TextField cancellation_ID;

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

    public void on_cancellation_button(ActionEvent event)
    {

        if (cancellation_ID.getText().length() > 0)
        {
            hotel.DB.manipulate("Delete FROM bookings WHERE customer_ID = " + cancellation_ID.getText());
            hotel.DB.manipulate("Delete FROM services WHERE customer_ID = " + cancellation_ID.getText());
            hotel.DB.manipulate("Delete FROM customers WHERE customer_ID = " + cancellation_ID.getText());

            cancellation_status.setText("Cancellation successful");

        }
        else
        {
            cancellation_status.setText("please enter a valid ID");
        }

    }
}
