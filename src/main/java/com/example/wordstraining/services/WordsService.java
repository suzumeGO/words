package com.example.wordstraining.services;

import com.example.wordstraining.DTO.TranslateDTO;
import com.example.wordstraining.exceptions.WordAlreadyExistsException;
import com.example.wordstraining.entities.Word;
import com.example.wordstraining.proxy.TranslateProxy;
import com.example.wordstraining.repo.WordsRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordsService {
    private final WordsRepo wordsRepo;
    private final TranslateProxy translateProxy;
    private final UsersService usersService;
    public WordsService(WordsRepo wordsRepo,
                        TranslateProxy translateProxy,
                        UsersService usersService) {
        this.wordsRepo = wordsRepo;
        this.translateProxy = translateProxy;
        this.usersService = usersService;
    }
    public boolean contains(String word) {
        return wordsRepo.findByWord(word) != null;
    }

    public Word findById(String id) {
        return wordsRepo.findByWord(id);
    }
    public List<Word> getWords(long chatId, String lang) {
        return usersService.findUser(chatId).orElseThrow().getWords().stream()
                .filter(word -> word.getLanguage().equals(lang))
                .toList();
    }

    public void save(Word word, long chatId) {
        word.getUsers().add(usersService.findUser(chatId).orElseThrow());
        try {
            wordsRepo.save(word);
        } catch (Exception e) {
            throw new WordAlreadyExistsException();
        }
    }

    public Word createWord(String stringWord, String lang) {
        Word word = new Word();
        word.setWord(stringWord);
        word.setTranslate(translateProxy.translate(new TranslateDTO(lang,"ru", stringWord)));
        word.setLanguage(lang);
        word.setOccurrences(0);
        word.setUsers(new ArrayList<>());
        word.setCorrectReplies(0);
        word.setAdditionDate(LocalDate.now());
        return word;
    }
}
