package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnector.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/health_tracker";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                System.out.println("Connection successful!");
            }
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void savePrediction(int height, int weight, String result) {
        try (Connection conn = connect()) {
            if (conn != null) {
                String sql = "INSERT INTO predictions (height, weight, result) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, height);
                    stmt.setInt(2, weight);
                    stmt.setString(3, result);
                    int rowsAffected = stmt.executeUpdate();
                    System.out.println("Rows affected: " + rowsAffected);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while saving data: " + e.getMessage(), e);
        }
    }
    public static void saveHeartDiseasePrediction(int age, int bloodPressure, int cholesterol, String result) {
        try (Connection conn = connect()) {
            if (conn != null) {
                String sql = "INSERT INTO heart_predictions (age, blood_pressure, cholesterol, result) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, age);
                    stmt.setInt(2, bloodPressure);
                    stmt.setInt(3, cholesterol);
                    stmt.setString(4, result);
                    int rowsAffected = stmt.executeUpdate();
                    System.out.println("Heart Disease Prediction Saved. Rows affected: " + rowsAffected);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while saving heart disease prediction: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
