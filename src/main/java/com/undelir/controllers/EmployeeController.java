package com.undelir.controllers;

import com.undelir.models.EmployeeDAO;
import com.undelir.models.EmployeeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;

public class EmployeeController {
    @FXML
    private TextField empIdTextTField;

    @FXML
    private TextField commissionTField;
    @FXML
    private TextArea resultAreaTArea;
    @FXML
    private TextField newEmailTextTField;
    @FXML
    private TextField nameTextTField;
    @FXML
    private TextField surnameTextTField;
    @FXML
    private TextField emailTextTField;
    @FXML
    private TextField permanent_statusTField;

    @FXML
    private TextField salaryTField;

    @FXML
    private TextField jobIdTField;
    @FXML
    private TextField phoneTField;
    @FXML
    private TextField hired_dateTField;

    @FXML
    private TextField departmentTField;

    @FXML
    private TableView employeeTableTView;
    @FXML
    private TableColumn<EmployeeModel, Integer> empIdColumnTColumn;
    @FXML
    private TableColumn<EmployeeModel, String>  empNameColumnTColumn;
    @FXML
    private TableColumn<EmployeeModel, String> empLastNameColumnTColumn;
    @FXML
    private TableColumn<EmployeeModel, String> empEmailColumnTColumn;
    @FXML
    private TableColumn<EmployeeModel, String> empPhoneNumberColumnTColumn;
    @FXML
    private TableColumn<EmployeeModel, Date> empHireDateColumnTColumn;
    //Search an employee
    @FXML
    private void searchEmployee (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Employee information
            EmployeeModel emp = EmployeeDAO.searchEmployee(empIdTextTField.getText());
            //Populate Employee on TableView and Display on TextArea
            populateAndShowEmployee(emp);
        } catch (SQLException e) {
            e.printStackTrace();
            resultAreaTArea.setText("Error occurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }
    //Search all employees
    @FXML
    private void searchEmployees(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Employees information
            ObservableList<EmployeeModel> empData = EmployeeDAO.searchEmployees();
            //Populate Employees on TableView
            populateEmployees(empData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }
    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize () {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Employee objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe
        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */
        empIdColumnTColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        empNameColumnTColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        empLastNameColumnTColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        empEmailColumnTColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        empPhoneNumberColumnTColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        empHireDateColumnTColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());
    }
    //Populate Employee
    @FXML
    private void populateEmployee (EmployeeModel emp) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<EmployeeModel> empData = FXCollections.observableArrayList();
        //Add employee to the ObservableList
        empData.add(emp);
        //Set items to the employeeTable
        employeeTableTView.setItems(empData);
    }
    //Set Employee information to Text Area
    @FXML
    private void setEmpInfoToTextArea ( EmployeeModel emp) {
        resultAreaTArea.setText("First Name: " + emp.getFirstName() + "\n" +
                "Last Name: " + emp.getLastName());
    }
    //Populate Employee for TableView and Display Employee on TextArea
    @FXML
    private void populateAndShowEmployee(EmployeeModel emp) throws ClassNotFoundException {
        if (emp != null) {
            populateEmployee(emp);
            setEmpInfoToTextArea(emp);
        } else {
            resultAreaTArea.setText("This employee does not exist!\n");
        }
    }
    //Populate Employees for TableView
    @FXML
    private void populateEmployees (ObservableList<EmployeeModel> empData) throws ClassNotFoundException {
        //Set items to the employeeTable
        employeeTableTView.setItems(empData);
    }
    //Update employee's email with the email which is written on newEmailText field
    @FXML
    private void updateEmployeeEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.updateEmpEmail(empIdTextTField.getText(),newEmailTextTField.getText());
            resultAreaTArea.setText("Email has been updated for, employee id: " + empIdTextTField.getText() + "\n");
        } catch (SQLException e) {
            resultAreaTArea.setText("Problem occurred while updating email: " + e);
        }
    }
    //Insert an employee to the DB
    @FXML
    private void insertEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.insertEmp(nameTextTField.getText(), surnameTextTField.getText(), emailTextTField.getText(), phoneTField.getText(), hired_dateTField.getText(), salaryTField.getText(), commissionTField.getText(), departmentTField.getText(),permanent_statusTField.getText());
            resultAreaTArea.setText("Employee inserted! \n");
        } catch (SQLException e) {
            resultAreaTArea.setText("Problem occurred while inserting employee " + e);
            throw e;
        }
    }
    //Delete an employee with a given employee Id from DB
    @FXML
    private void deleteEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.deleteEmpWithId(empIdTextTField.getText());
            resultAreaTArea.setText("Employee deleted! Employee id: " + empIdTextTField.getText() + "\n");
        } catch (SQLException e) {
            resultAreaTArea.setText("Problem occurred while deleting employee " + e);
            throw e;
        }
    }
}
