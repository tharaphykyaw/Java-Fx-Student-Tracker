/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import student.app.database.Database;
import student.app.model.Student;

/**
 *
 * @author Sithu
 */
public class StudentDAO {

    public int saveStudent(Student student) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into students (name,email,gender,dob)value (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, student.getName());
        stmt.setString(2, student.getEmail());
        stmt.setString(3, student.getGender());
        stmt.setDate(4, student.getDob());
        int rows = stmt.executeUpdate();
        return rows;

    }

    public Student getStudentById(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "Select * from students where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        Student sd = null;
        if (result.next()) {
            String name = result.getString("name");
            String email = result.getString("email");
            String gender = result.getString("gender");
            Date dob = result.getDate("dob");
            sd = new Student(id, name, email, gender, dob);
        }
        return sd;
    }

    public List<Student> getStudents() throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "Select * from students ";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        List<Student> students = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String email = result.getString("email");
            String gender = result.getString("gender");
            Date dob = result.getDate("dob");
            Student sd = new Student(id, name, email, gender, dob);
            students.add(sd);
        }
        return students;

    }

    public void updateStudent(Student student) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        int id = student.getId();
        String name = student.getName();
        String email = student.getEmail();
        String gender = student.getGender();
        Date dob = student.getDob();
        String sql = "Update students set name=?,email=?,gender=?,dob=? where id =?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, gender);
        stmt.setDate(4, dob);
        stmt.setInt(5, id);
        stmt.executeUpdate();
    }

    public int deleteStudent(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();

        String sql = "delete from students where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        return rows;
    }

}
