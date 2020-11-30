package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class Admin_Login_Page_Controller
{
    public Hotel_Manager hotel;

    public PasswordField passwordField;



    public Stage window;

    // activated when the login button is pressed
    public void login_button_click(ActionEvent event) throws IOException
    {
            // if the login password field is the same as the hotels admin password field
            if ( passwordField.getText().equals( hotel.admin_password) )
            {
                // load the admin menu
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_menu.fxml"));
                Parent parent = loader.load();
                // get the controller of the admin menu
                Admin_Menu_Controller admin_menu = loader.getController();
                // set the window and hotel of the controller
                admin_menu.window = window;
                admin_menu.hotel = this.hotel;
                // load the admin menu
                admin_menu.load();
                // show the new scene
                Scene scene = new Scene(parent);
                window.setScene(scene);
                window.show();
            }
    }

    // activated when the the main menu button is pressed
    public void main_menu_button_click(ActionEvent event) throws IOException
    {
        // load the main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent parent = loader.load();
        // get the main menu controller
        Main_Menu_Controller main_menu = loader.getController();
        // set the window and hotel of the main menu
        main_menu.window = window;
        main_menu.hotel = this.hotel;
        // show the new scene
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.show();

    }
}
