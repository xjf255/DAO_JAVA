package org.example.DAO;

import java.util.List;

public interface IDAO<T,K> {
    T get (K prop) throws DAOException;
    void save (T prop) throws DAOException;
    void update (T prop) throws DAOException;
    void delete (K prop) throws DAOException;
    List<T> getAll() throws DAOException;
}
