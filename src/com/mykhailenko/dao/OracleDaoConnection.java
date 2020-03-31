package com.mykhailenko.dao;

import com.mykhailenko.entities.Student;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class OracleDaoConnection implements DaoConnection {
    private static OracleDaoConnection oracleDaoConnection;
    private Context ctx;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Hashtable<String, String> ht = new Hashtable<>();

    public static OracleDaoConnection getInstance(){
        if(oracleDaoConnection == null){
            return new OracleDaoConnection();
        }
        return oracleDaoConnection;
    }

    @Override
    public void connect() {
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            DataSource ds = (DataSource) ctx.lookup("Yulia_Data_Source");
            connection = ds.getConnection();
            System.out.println("Succesfully connected");
        } catch (NamingException | SQLException e) {
            System.out.println("Exception in connection");
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
            resultSet.close();
            statement.close();
            ctx.close();
            System.out.println("Disconnected");
        } catch (Exception e) {
            System.out.println("Exception in disconnection");
        }
    }

    @Override
    public List<Student> selectAllStudents() {
        connect();
        List<Student> students = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM STUDENTS ORDER BY STUDENT_NAME");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                students.add(parseStudent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return students;
    }

    private Student parseStudent (ResultSet resultSet) {
        Student student = null;
        try {
            int id = resultSet.getInt("STUDENT_ID");
            String name = resultSet.getString("STUDENT_NAME");
            float scholarship = resultSet.getFloat("STUDENT_SCHOLARSHIP");
            student = new Student(id, name, scholarship);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
}
