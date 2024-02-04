package com.example.wordstraining.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedWordsRepo<T>  {
    Page<T> findAllByUser(long user, String lang, Pageable pageable);
}
