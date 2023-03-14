package com.undelir.models;

import com.undelir.utils.DataBaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {
    //*******************************
    //SELECT an Employee
    //*******************************
    public static EmployeeModel searchEmployee (String empId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM employees WHERE employee_id=" + empId;
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DataBaseUtil.dbExecuteQuery(selectStmt);
            //Send ResultSet to the getEmployeeFromResultSet method and get employee object
            EmployeeModel employee = getEmployeeFromResultSet(rsEmp);
            //Return employee object
            return employee;
        } catch (SQLException e) {
            System.out.println("While searching an employee with " + empId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Employee Object's attributes and return employee object.
    private static EmployeeModel getEmployeeFromResultSet(ResultSet rs) throws SQLException
    {
        EmployeeModel emp = null;
        if (rs.next()) {
            emp = new EmployeeModel();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(rs.getDate("HIRE_DATE"));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));
            emp.permanent_status(rs.getBoolean("WORKING_PERMANENT"));
        }
        return emp;
    }
    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<EmployeeModel> searchEmployees () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM employees";
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DataBaseUtil.dbExecuteQuery(selectStmt);
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<EmployeeModel> empList = getEmployeeList(rsEmps);
            //Return employee object
            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }
    //Select * from employees operation
    private static ObservableList<EmployeeModel> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<EmployeeModel> empList = FXCollections.observableArrayList();
        while (rs.next()) {
            EmployeeModel emp = new EmployeeModel();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(rs.getDate("HIRE_DATE"));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));
            //Add employee to the ObservableList
            empList.add(emp);
        }
        //return empList (ObservableList of Employees)
        return empList;
    }
    //*************************************
    //UPDATE an employee's email address
    //*************************************
    public static void updateEmpEmail (String empId, String empEmail) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE employees\n" +
                        "      SET EMAIL = '" + empEmail + "'\n" +
                        "    WHERE EMPLOYEE_ID = " + empId + ";\n" +
                        "   COMMIT;\n" +
                        "END;";
        //Execute UPDATE operation
        try {
            DataBaseUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }
    //*************************************
    //DELETE an employee
    //*************************************
    public static String deleteEmpWithId (String empId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM employees\n" +
                        "         WHERE employee_id ="+ empId +";\n" +
                        "   COMMIT;\n" +
                        "END;";
        //Execute UPDATE operation
        try {
            DataBaseUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
        return updateStmt;
    }
    //*************************************
    //INSERT an employee
    //*************************************
    public static String insertEmp (String fname, String lname, String email, String phone, String hire_date, String salary, String commission, String dept_id, String work_status ) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement (SYSDATE for current date)
        String insertStmt =
                "BEGIN\n" +
                        "INSERT INTO employees\n" +
                        "(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER,HIRE_DATE,SALARY,COMMISSION_PCT,DEPARTMENT_ID,WORKING_PERMANENT)\n" +
                        "VALUES\n" +
                        "('"+fname+"', '"+lname+"','"+email+"', '"+phone+"', '"+hire_date+"', '"+Integer.parseInt(salary)+"','"+ Double.parseDouble(commission)+"','"+dept_id+"','"+work_status+"');\n" +
                        "END;";
        //Execute insert operation
        try {
            DataBaseUtil.dbExecuteQuery(insertStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
        return insertStmt;
    }
}
