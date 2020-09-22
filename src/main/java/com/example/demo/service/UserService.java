package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User add(User user) {
        return userRepository.save(user);
    }

    public User addNewScore(Long id, int count) {
        User user = userRepository.findById(id).get();
        for (int i = 0; i < count; i++) {
            user.getScores().add(1000);
        }
        userRepository.save(user);
        return user;
    }

    public User deleteScore(Long id, int idScore) {
        User user = userRepository.findById(id).get();
        user.getScores().remove(idScore);
        userRepository.save(user);
        return user;
    }

    public User swappingScore(Long id, int idScorelast, int idScoreNew, int sum) {
        User user = userRepository.findById(id).get();
        Integer idScoreLastSum = user.getScores().get(idScorelast) - sum;
        user.getScores().remove(idScorelast);
        user.getScores().add(idScorelast, idScoreLastSum);
        Integer idScoreNewSum = user.getScores().get(idScoreNew) + sum;
        user.getScores().remove(idScoreNew);
        user.getScores().add(idScoreNew, idScoreNewSum);
        userRepository.save(user);
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean deleteById(Long id) {
        boolean result = userRepository.existsById(id);
        if (result) {
            userRepository.deleteById(id);
        }
        return result;
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public boolean validIdScore(Long id, int idScore) {
        User user = userRepository.findById(id).get();
        if (idScore > 1 && idScore < user.getScores().size()) {
            return true;
        }
        return false;
    }
}
