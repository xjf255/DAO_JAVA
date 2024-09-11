package org.example.DAO;

import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserIDAO{

    final String INSERT = "insert into usuario(nombre,apellido,email) values(?,?,?)";
    final String GETALL = "select * from usuario";
    final String GET = "select * from usuario where id=?";
    final String DELETE = "delete from usuario where id=?";
    final String UPDATE = "update usuario set nombre=?,apellido=?,email=? where id=?";

    private Connection conn = null;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(User user) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.INSERT);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getLastname());
            ps.setString(3,user.getEmail());
            if(ps.executeUpdate() == 0){
                throw new DAOException("No se guardaron los cambios");
            }
        }catch (SQLException e){
            throw new DAOException("Error en sql",e);
        }finally {
            if(ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Error en sql",e);
                }
            }
        }
    }

    @Override
    public void update(User user) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.UPDATE);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getLastname());
            ps.setString(3,user.getEmail());
            ps.setLong(4,user.getId());
            if(ps.executeUpdate() == 0){
                throw new DAOException("No se guardaron los cambios");
            }
        }catch (SQLException e){
            throw new DAOException("Error en SQL ",e);
        }finally{
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error en sql",e);
                }
            }
        }
    }

    @Override
    public void delete(Long idUser) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.DELETE);
            ps.setLong(1,idUser);
            if(ps.executeUpdate() == 0){
                throw new DAOException("No se pudo eliminar el usuario");
            }
        } catch (SQLException e) {
            throw new DAOException("Error SQL, ",e);
        } finally {
            if(ps != null){
                try {
                    if (ps != null) {
                        ps.close();
                    }
                }catch (SQLException e){
                    throw new DAOException("Error en sql",e);
                }
            }
        }
    }

    private User ConvertDAO(ResultSet rs) throws SQLException {
        String name = rs.getString("nombre");
        String lastName = rs.getString("apellido");
        String email = rs.getString("email");
        return new User(name,lastName,email);
    }

    @Override
    public User get (Long id) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            ps = conn.prepareStatement(this.GET);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                user = ConvertDAO(rs);
                user.setId(id);
            }
            return user;
        } catch (SQLException e) {
            throw new DAOException("Error SQL ",e);
        }finally {
            if(ps != null){
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if(rs != null){
                        rs.close();
                    }
                }catch (SQLException e){
                    throw new DAOException("Error en sql",e);
                }
            }
        }
    }

    public List<User> getAll() throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            ps = conn.prepareStatement(this.GETALL);
            rs = ps.executeQuery();
            while(rs.next()){
                users.add(ConvertDAO(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException("Error SQL ",e);
        }finally {
            if(ps != null){
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if(rs != null){
                        rs.close();
                    }
                }catch (SQLException e){
                    throw new DAOException("Error en sql",e);
                }
            }
        }
    }
}
