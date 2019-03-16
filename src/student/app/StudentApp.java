/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sithu
 */
public class StudentApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        

        System.out.println("Before FXML Loading...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/app/views/main.fxml"));
//      FXMLLoader.load(getClass().getResource("/student/app/main.fxml"));
        Parent root = loader.load(); // Contoller
        System.out.println("After FXML Loading...");
        Scene scene = new Scene(root);
//      MainController controller = loader.getController();
       
        stage.setScene(scene);
        stage.setTitle("Student App");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
