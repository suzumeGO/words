package com.example.wordstraining.repo;

import com.example.wordstraining.entities.UserWord;
import com.example.wordstraining.entities.UserWordKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWordRepo extends JpaRepository<UserWord, UserWordKey> {
}
