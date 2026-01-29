package com.gym.services;

import com.gym.dao.TrainerDao;
import com.gym.models.Trainer;

import java.util.List;

public class TrainerService {
    private TrainerDao trainerDao;
    // Constructor
    public TrainerService() {
        this.trainerDao = new TrainerDao();
    }
 // Add a new trainer
    public boolean addTrainer(Trainer trainer) {
        return trainerDao.insert(trainer);
    }
    // Get a trainer by ID
    public Trainer getTrainerById(int trainerId) {
        return trainerDao.getById(trainerId);
    }

    public List<Trainer> getAllTrainers() {
        return trainerDao.getAll();
    }
    // Update trainer details
    public boolean updateTrainer(Trainer trainer) {
        return trainerDao.update(trainer);
    }
 // Delete a trainer by ID
    public boolean deleteTrainer(int trainerId) {
        return trainerDao.delete(trainerId);
    }
}