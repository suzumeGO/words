package com.example.wordstraining.services;

import com.example.wordstraining.entities.UserWord;
import com.example.wordstraining.entities.UserWordKey;
import com.example.wordstraining.repo.UserWordRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWordsService {

    private final UserWordRepo userWordRepo;

    public UserWordsService(UserWordRepo userWordRepo) {
        this.userWordRepo = userWordRepo;
    }

    public Optional<UserWord> findByKey(UserWordKey key) {
        return userWordRepo.findById(key);
    }

    public void save(UserWord userWord) {
        userWordRepo.save(userWord);
    }
}
