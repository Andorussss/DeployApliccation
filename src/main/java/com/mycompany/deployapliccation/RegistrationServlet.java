
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
public class RegistrationServlet extends HttpServlet {

  private static final String JDBC_URL = "jdbc:postgresql://ec2-44-213-151-75.compute-1.amazonaws.com:5432/d4te06e7baa8e1";
  private static final String JDBC_USER = "mbiptjqcrrsbse";
  private static final String JDBC_PASSWORD = "f47144460627c3aae9c00577fe241a4c5945349b1161b5d7a3c00b3620e4d8e4";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        boolean userExists = checkUserExists(username);

        if (userExists) {
            out.println("<html><body>");
            out.println("<h2>Регистрация не удалась!</h2>");
            out.println("<p>Пользователь с таким именем уже существует.</p>");
            out.println("</body></html>");
        } else {
            boolean registrationSuccess = registerUser(username, password, email);
            
            if (registrationSuccess) {
                out.println("<html><body>");
                out.println("<h2>Регистрация успешна!</h2>");
                out.println("<p>Имя пользователя: " + username + "</p>");
                out.println("<p>Email: " + email + "</p>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h2>Регистрация не удалась!</h2>");
                out.println("<p>Что-то пошло не так. Попробуйте еще раз.</p>");
                out.println("</body></html>");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
   private boolean checkUserExists(String username) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean registerUser(String username, String password, String email) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                System.out.println("Error: Failed to register the user.");
                return false;
            }
        }
    } catch (SQLException e) {
        System.out.println("SQL Error registering user:");
        e.printStackTrace(); 
        return false;
    }
}

}
