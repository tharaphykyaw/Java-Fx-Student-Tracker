/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import student.app.dao.StudentDAO;
import student.app.model.Student;

/**
 *
 * @author Sithu
 */
public class MainController implements Initializable {

    @FXML
    private MenuItem aboutItem;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ToggleGroup gender;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private Button saveBtn;

    private StudentDAO studentDAO;

    @FXML
    private TableColumn<Student, Integer> idCol;
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> emailCol;
    @FXML
    private TableColumn<Student, String> genderCol;
    @FXML
    private TableColumn<Student, Date> dobCol;
    @FXML
    private ContextMenu menuItem;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private TableView<Student> studentTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  System.out.println("Inside initilize method.");
        maleRadio.setUserData("Male");
        femaleRadio.setUserData("Female");
        studentDAO = new StudentDAO();
        initColumns();
        loadTableData();

    }

    @FXML
    private void showAboutWindow(ActionEvent event) throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/student/app/views/about.fxml"));
        Scene scene = new Scene(layout);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(menuBar.getScene().getWindow());
        stage.show();
    }

    @FXML
    private void saveStudent(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String userdata = (String) gender.getSelectedToggle().getUserData();
        LocalDate dob = dobPicker.getValue();

        if (name.isEmpty() || email.isEmpty() || dob == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all required fields ..");
            alert.show();
            return;
        }
        Date sqldob = Date.valueOf(dob);
        Student sd = new Student(name, email, userdata, sqldob);
        try {
            studentDAO.saveStudent(sd);
            loadTableData();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Student Successfully addd..");
            alert.show();
            clearForm();
        } catch (SQLException ex) {
            System.out.println("Error..");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    private void loadTableData() {
        try {
            List<Student> students = studentDAO.getStudents();
            System.out.println("student Size"+students.size());
            studentTable.getItems().setAll(students);
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
    }

    @FXML
    private void deleteStudent(ActionEvent event) {
        
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select the student you want to delete..");
            alert.show();
            return;
        }
        try {
            studentDAO.deleteStudent(selectedStudent.getId());
            studentTable.getItems().remove(selectedStudent);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void loadEditWindow(ActionEvent event) throws IOException {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent==null){
            Alert alert = new Alert (AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select the student you want to edit...");
        alert.show();
        return;
        }  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/app/views/edit.fxml"));
    Parent root = loader.load();
    EditController controller = loader.getController();
    controller.setStudentData(selectedStudent);
    Scene scene = new Scene(root);
    Stage editStage = new Stage();
    editStage.setScene(scene);
    editStage.initModality(Modality.WINDOW_MODAL);
    editStage.initOwner(studentTable.getScene().getWindow());
    //editStage.show();
    editStage.showAndWait();
    loadTableData();
    }

}


