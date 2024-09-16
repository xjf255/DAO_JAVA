package org.example.controller;

import org.example.DAO.IDAOManager;
import org.example.model.Books;
import org.example.model.Lend;
import org.example.model.User;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager extends IDAOManager {
    private Connection conn;
    private Lend lend = null;
    private User user = null;
    private Books book = null;


    public DAOManager(String url, String user, String pass) throws SQLException {
        conn = DriverManager.getConnection(url,user,pass);
    }

    @Override
    public Lend getLendDAO() {
        if(lend == null){
            lend = new LendDAO(conn);
        }
        return lend;
    }

    @Override
    public Books getBookDAO() {
        if(book == null){
            book = new BookDAO(conn);
        }
        return book;
    }

    @Override
    public User getUserDAO() {
        if(user == null){
            user = new UserDAO(conn);
        }
        return user;
    }
}
