package org.example.controller;

import org.example.DAO.DAOException;
import org.example.DAO.IBookIDAO;
import org.example.model.Books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends Books implements IBookIDAO {
    final String SAVE = "INSERT INTO libro(titulo_libro,autor_libro,disponible) VALUES(?,?,?)";
    final String DELETE = "DELETE FROM libro WHERE cod_libro = ?";
    final String UPDATE = "UPDATE libro set titulo_libro = ?, autor_libro = ?, disponible = ? WHERE cod_libro = ?";
    final String GET = "SELECT * FROM libro WHERE cod_libro = ?";
    final String GETALL = "SELECT * FROM libro";
    private Connection conn = null;

    public BookDAO(Connection conn) {
        this.conn = conn;
    }

    private Books ConvertBook(ResultSet rs) throws SQLException {
        Books books = new Books();
        books.setCod(rs.getLong("cod_libro"));
        books.setAuthor(rs.getString("autor_libro"));
        books.setTitle(rs.getString("titulo_libro"));
        books.setStatus(rs.getString("disponible"));
        return books;
    }

    @Override
    public Books get(Long book) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Books books = null;
        try {
            ps = conn.prepareStatement(GET);
            ps.setLong(1, book);
            rs = ps.executeQuery();
            if(rs.next()) {
                books = ConvertBook(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return books;
    }
    @Override
    public void save(Books books)throws DAOException{
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SAVE);
            ps.setString(1, books.getTitle());
            ps.setString(2, books.getAuthor());
            ps.setString(3, books.getStatus());
            if(ps.executeUpdate() == 0){
                throw new DAOException("Error al guardar el libro");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
    }

    @Override
    public void update(Books props) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATE);
            ps.setString(1,props.getTitle());
            ps.setString(2,props.getAuthor());
            ps.setString(3,props.getStatus());
            if(ps.executeUpdate() == 0){
                throw new DAOException("Error al actualizar el libro");
            }
        } catch (SQLException e) {
            throw new DAOException("Error SQL ",e);
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
    }

    @Override
    public void delete(Long book) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(DELETE);
            ps.setLong(1,book);
            if(ps.executeUpdate() == 0){
                throw new DAOException("Error al eliminar el libro");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
    }

    @Override
    public List<Books> getAll() throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Books> books = new ArrayList<>();
        try {
            ps = conn.prepareStatement(GETALL);
            rs = ps.executeQuery();
            while (rs.next()) {
                books.add(ConvertBook(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return books;
    }
}
