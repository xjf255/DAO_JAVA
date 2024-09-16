package org.example.DAO;

import org.example.model.Books;
import org.example.model.Lend;
import org.example.model.User;

import java.awt.print.Book;

public interface IDAOManager {

    Lend getLendDAO();

    Books getBookDAO();

    User getUserDAO();
}
