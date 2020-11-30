package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application
{

    @Override
    public void start(Stage window) throws Exception
    {
        // creating the hotel
        Hotel_Manager hotel = new Hotel_Manager();
        //


        // creating the main menu
        //Set up instance instead of using static load() method
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent root = loader.load();

        // get the controller of the main menu
        Main_Menu_Controller main_menu_controller = loader.getController();
        // set the window and the hotel of the controller to be the window and the hotel created
        main_menu_controller.window = window;
        main_menu_controller.hotel = hotel;
        // set the scene
        Scene scene = new Scene(root);
        window.setScene(scene);
        // show the scene
        window.show();

    }

/**
 * The main() method is ignored in correctly deployed JavaFX application.
 * main() serves only as fallback in case the application can not be
 * launched through deployment artifacts, e.g., in IDEs with limited FX
 * support.
*/
    public static void main(String[] args)
    {
        launch(args);
    }
}
