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

    public void login_button_click(ActionEvent event) throws IOException
    {
            if ( passwordField.getText().equals( hotel.admin_password) )
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_menu.fxml"));
                Parent parent = loader.load();

                Admin_Menu_Controller admin_menu = loader.getController();

                admin_menu.window = window;
                admin_menu.hotel = this.hotel;
                admin_menu.load();
                Scene scene = new Scene(parent);
                window.setScene(scene);
                window.show();
            }
    }


    public void main_menu_button_click(ActionEvent event) throws IOException
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
}
