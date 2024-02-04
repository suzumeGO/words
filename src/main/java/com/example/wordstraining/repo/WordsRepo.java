package com.example.wordstraining.repo;

import com.example.wordstraining.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepo extends JpaRepository<Word, Long> {

    Word findByWord(String word);

}
