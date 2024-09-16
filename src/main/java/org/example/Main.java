package org.example;

import org.example.DAO.DAOException;
import org.example.controller.UserDAO;
import org.example.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws DAOException {
        String user = "postgres";
        String password = "Prueba1234";
        String url = "jdbc:postgresql://localhost:5432/postgres";

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            UserDAO userDAO = new UserDAO(connection);
            User newUser = new User();
            User pedro = new User("pedro","Simon","PedroSimon@gmail.com");
            if (connection != null) {
//                userDAO.save(pedro);
                newUser = userDAO.get(3L);
                System.out.println(newUser);
                newUser.setEmail("CEO@gmail.com");
                userDAO.update(newUser);
            }
        }catch (SQLException e) {
            System.out.printf("SQLException: %s%n", e);
            e.printStackTrace();
        }
    }
}