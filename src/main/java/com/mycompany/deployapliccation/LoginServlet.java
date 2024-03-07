/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.deployapliccation;

import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andor
 */
public class LoginServlet extends HttpServlet {

     private static final String JDBC_URL = "jdbc:postgresql://ec2-44-213-151-75.compute-1.amazonaws.com:5432/d4te06e7baa8e1";
    private static final String JDBC_USER = "mbiptjqcrrsbse";
    private static final String JDBC_PASSWORD = "f47144460627c3aae9c00577fe241a4c5945349b1161b5d7a3c00b3620e4d8e4";
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean loginSuccess = loginUser(username, password);

        if (loginSuccess) {
            out.println("<html><body>");
            out.println("<h2>Login successful!</h2>");
            out.println("<p>Welcome, " + username + "!</p>");
            out.println("</body></html>");
            response.sendRedirect("login.html");
        } else {
            out.println("<html><body>");
            out.println("<h2>Login failed!</h2>");
            out.println("<p>Invalid username or password. Please try again.</p>");
            out.println("</body></html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private boolean loginUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
