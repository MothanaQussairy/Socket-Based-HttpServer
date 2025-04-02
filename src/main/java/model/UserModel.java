package model;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserModel {
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM Users WHERE Id = ?";
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Phone"));
            }
        }
        return null;
    }
    public List<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM Users Where IsDeleted=0;";
        List<User> users = new ArrayList<>();
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Phone")));
            }
        }
        return users;
    }
    public boolean deleteUser(int userId) throws SQLException {
        String query = "UPDATE Users SET IsDeleted=1,DateDelete=? WHERE Id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(2, userId);
            LocalDate currentDate = LocalDate.now(); // Create a date object
            stmt.setString(1, currentDate.toString());


            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: Failed to delete user.");
            throw e;
        }
    }
    public boolean insertUser(User user) throws SQLException {
        String query = "INSERT INTO Users (Name, Phone, IsDeleted) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setBoolean(3, false);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Returns true if insertion was successful
        }
    }

}
