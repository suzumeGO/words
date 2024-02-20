package com.example.wordstraining.repo;

import com.example.wordstraining.entities.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomizedWordsRepo<T>  {
    Page<T> findAllByUser(long user, String lang, Pageable pageable);
    List<String> findAllTranslations(long user, String lang);
    List<Word> findWeakest(long user, String lang);
    List<String> findAllWords(long user, String lang);
}
