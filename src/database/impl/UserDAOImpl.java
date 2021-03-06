package database.impl;

import common.model.User;
import database.DBConnection;
import database.dao.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Norm on 2/25/2019.
 */
public class UserDAOImpl implements UserDAO {

    private Connection conn;
    private PreparedStatement stmt;

    public UserDAOImpl() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(User user) throws SQLException {

        System.out.println("User: " + user.toString());

        String sql = "INSERT INTO users " +
                "(user_name, password, uname, gender, address) " +
                "VALUES (?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getName());
        stmt.setString(4, user.getGender());
        stmt.setString(5, user.getAddress());
        return (stmt.executeUpdate() > 0);
    }

    @Override
    public boolean delete(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username=?";
        stmt = conn.prepareStatement(sql);
        stmt.setObject(1, username);
        return stmt.executeUpdate() > 0;
    }

    @Override
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET address=?,contact=? WHERE username=?";
        stmt = conn.prepareStatement(sql);
        stmt.setObject(1, user.getAddress());
        stmt.setObject(1, user.getContact());
        stmt.setObject(1, user.getUsername());
        return stmt.executeUpdate() > 0;
    }

    @Override
    public ArrayList<User> get() throws SQLException {
        ArrayList<User> usersList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        stmt = conn.prepareStatement(sql);
        ResultSet rst = stmt.executeQuery();
        while (rst.next()) {
            usersList.add(
                    new User(
                            rst.getString("user_name"),
                            rst.getString("password"),
                            rst.getString("uname"),
                            rst.getString("date_of_birth"),
                            rst.getString("gender"),
                            rst.getString("address"),
                            rst.getString("nic"),
                            rst.getString("contact")
                    )
            );
        }
        return usersList;
    }

    @Override
    public User get(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name=?";
        stmt = conn.prepareStatement(sql);
        stmt.setObject(1, username);
        ResultSet rst = stmt.executeQuery();
        if (rst.next()) {
            return new User(
                    rst.getString("user_name"),
                    rst.getString("password"),
                    rst.getString("uname"),
                    rst.getString("gender"),
                    rst.getString("address")
            );
        }
        return null;
    }
}
