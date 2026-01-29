package com.gym.dao;

import com.gym.models.Trainer;
import com.gym.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDao {
    private Connection connection;

    public TrainerDao() {
        this.connection = DatabaseUtil.getConnection();
    }
    // Insert a new trainer 
    public boolean insert(Trainer trainer) {
        String sql = "INSERT INTO trainers (user_id, contact, specialization, experience, salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, trainer.getUserId());
            stmt.setString(2, trainer.getContact());
            stmt.setString(3, trainer.getSpecialization());
            stmt.setInt(4, trainer.getExperience());
            stmt.setDouble(5, trainer.getSalary());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return false;
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trainer.setTrainerId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
// // Get trainer by ID
    public Trainer getById(int trainerId) {
        String sql = "SELECT * FROM trainers WHERE trainer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractTrainer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 // Get all trainers
    public List<Trainer> getAll() {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT * FROM trainers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trainers.add(extractTrainer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }
    // Update trainer details
    public boolean update(Trainer trainer) {
        String sql = "UPDATE trainers SET contact=?, specialization=?, experience=?, salary=? WHERE trainer_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, trainer.getContact());
            stmt.setString(2, trainer.getSpecialization());
            stmt.setInt(3, trainer.getExperience());
            stmt.setDouble(4, trainer.getSalary());
            stmt.setInt(5, trainer.getTrainerId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Delete trainer by ID
    public boolean delete(int trainerId) {
        String sql = "DELETE FROM trainers WHERE trainer_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Trainer extractTrainer(ResultSet rs) throws SQLException {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(rs.getInt("trainer_id"));
        trainer.setUserId(rs.getInt("user_id"));
        trainer.setContact(rs.getString("contact"));
        trainer.setSpecialization(rs.getString("specialization"));
        trainer.setExperience(rs.getInt("experience"));
        trainer.setSalary(rs.getDouble("salary"));
        return trainer;
    }
}