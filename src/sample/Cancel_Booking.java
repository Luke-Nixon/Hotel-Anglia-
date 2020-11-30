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

    // runs when the main menu is clicked
    public void main_menu_click(ActionEvent event) throws IOException
    {
        // load the main menu.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent parent = loader.load();
        // get the main menu controller
        Main_Menu_Controller main_menu = loader.getController();
        // set the window and hotel fields of the controller
        main_menu.window = window;
        main_menu.hotel = this.hotel;
        // show the new scene.
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();
    }

    // runs when the cancellation button is clicked
    public void on_cancellation_button(ActionEvent event)
    {
        // if the cancellation_ID text field is not blank
        if (cancellation_ID.getText().length() > 0)
        {
            // delete from the DB the cancellation ID in the field.
            hotel.DB.manipulate("Delete FROM bookings WHERE customer_ID = " + cancellation_ID.getText());
            hotel.DB.manipulate("Delete FROM services WHERE customer_ID = " + cancellation_ID.getText());
            hotel.DB.manipulate("Delete FROM customers WHERE customer_ID = " + cancellation_ID.getText());
            // print the cancellation was successful by changing the label.
            cancellation_status.setText("Cancellation successful");

        }
        else
        {
            // if the user did not enter a valid id, inform them by setting the label.
            cancellation_status.setText("please enter a valid ID");
        }

    }
}
