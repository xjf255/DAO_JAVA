package org.example.controller;

import org.example.DAO.DAOException;
import org.example.DAO.ILendIDAO;
import org.example.model.Lend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LendDAO extends Lend implements ILendIDAO {
    final String GETALL = "select * from libroprestado";
    final String GET = "select * from libroprestado where cod_prestamo=?";
    final String SAVE = "insert into libroprestado(cod_libro,cod_usuario,fecha_prestamo) values(?,?,?)";
    final String DELETE = "delete from libroprestado where cod_prestamo = ?";
    final String UPDATE = "update libroprestado set cod_libro=?, cod_usuario = ?,fecha_prestamo=?, fecha_devolucion = ? where cod_prestamo=?";
    private Connection conn = null;

    private Lend ConvertDAO(ResultSet rs) throws SQLException {
        Lend lend = new Lend();
        lend.setCod(rs.getLong("cod_prestamo"));
        lend.setCod_book(rs.getInt("cod_libro"));
        lend.setCod_user(rs.getInt("cod_usuario"));
        lend.setLend_date(rs.getTimestamp("fecha_prestamo"));
        lend.setReturn_date(rs.getTimestamp("fecha_devolucion"));
        return lend;
    }

    public LendDAO(Connection conn){
            this.conn = conn;
    }

    @Override
    public Lend get(Long id) throws DAOException {
        if(id == null) return null;
        Lend lend = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.GET);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                lend = ConvertDAO(rs);
                return lend;
            }
        } catch (SQLException e){
            throw new DAOException("Fallo SQL, ",e);
        }finally{
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Fallo SQL, ",e);
                }
            }
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Fallo SQL, ",e);
                }
            }
        }
        return lend;
    }

    @Override
    public void save(Lend lend) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.SAVE);
            ps.setLong(1, lend.getCod_book());
            ps.setInt(2, lend.getCod_user());
            ps.setTimestamp(3, lend.getLend_date());
            if(ps.executeUpdate() == 0){
                throw new DAOException("No se pudo guardas el prestamo");
            }
        }catch (SQLException e){
            throw new DAOException("Fallo SQL, ",e);
        }finally{
            if(ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Fallo SQL, ",e);
                }
            }
        }
    }

    @Override
    public void update(Lend prop) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.UPDATE);
            ps.setLong(1,prop.getCod_book());
            ps.setInt(2,prop.getCod_user());
            ps.setTimestamp(3,prop.getLend_date());
            ps.setTimestamp(4,prop.getReturn_date());
            if(ps.executeUpdate() == 0){
                throw new DAOException("No se pudo actualizar el prestamo");
            }
        }catch (SQLException e){
            throw new DAOException("Fallo sql ",e);
        }finally{
            if(ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Fallo SQL ",e);
                }
            }
        }
    }

    @Override
    public void delete(Long prop) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.DELETE);
            ps.setLong(1, prop);
            if(ps.executeUpdate() == 0){
                throw new DAOException("No se pudo eliminar el prestamo");
            }
        }catch (SQLException e){
            throw new DAOException("Fallo SQL, ",e);
        }finally{
            if(ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Fallo SQL, ",e);
                }
            }
        }
    }

    @Override
    public List<Lend> getAll() throws DAOException {
        List<Lend> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.GETALL);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(ConvertDAO(rs));
            }
        } catch (SQLException e){
            throw new DAOException("Fallo SQL, ",e);
        }finally{
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Fallo SQL, ",e);
                }
            }
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Fallo SQL, ",e);
                }
            }
        }
        return list;
    }
}
