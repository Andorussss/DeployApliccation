/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.deployapliccation;

import java.sql.*;

public class UserCRUD {
    private Connection connection;

    public UserCRUD(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    // Create
    public void createUser(String name, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.executeUpdate();
        }
    }

    // Read
    public void getUser(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Email: " + resultSet.getString("email"));
                    System.out.println("Password: " + resultSet.getString("password"));
                } else {
                    System.out.println("User not found.");
                }
            }
        }
    }

    // Update
    public void updateUser(int id, String name, String email, String password) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }
        }
    }

    // Delete
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        }
    }
}
