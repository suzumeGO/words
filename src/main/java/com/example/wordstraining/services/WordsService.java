package com.example.wordstraining.services;

import com.example.wordstraining.DTO.QuizDTO;
import com.example.wordstraining.DTO.TranslateDTO;
import com.example.wordstraining.entities.Word;
import com.example.wordstraining.exceptions.WordAlreadyExistsException;
import com.example.wordstraining.mappers.WordsMapper;
import com.example.wordstraining.proxy.TranslateProxy;
import com.example.wordstraining.repo.WordsRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class WordsService {
    private final WordsRepo wordsRepo;
    private final TranslateProxy translateProxy;
    private final UsersService usersService;
    private final WordsMapper mapper;

    public WordsService(WordsRepo wordsRepo,
                        TranslateProxy translateProxy,
                        UsersService usersService,
                        WordsMapper mapper) {
        this.wordsRepo = wordsRepo;
        this.translateProxy = translateProxy;
        this.usersService = usersService;
        this.mapper = mapper;
    }

    public boolean contains(String word) {
        return wordsRepo.findByWord(word) != null;
    }

    public Word findById(String id) {
        return wordsRepo.findByWord(id);
    }


    public Page<Word> getWords(long chatId, String lang, Pageable pageable) {
        return wordsRepo.findAllByUser(chatId, lang, pageable);
    }

    public List<String> getTranslations(long chatId, String lang) {
        return wordsRepo.findAllTranslations(chatId, lang);
    }

    public QuizDTO getWeakestQuiz(long chatId, String lang) {
        List<String> translations = wordsRepo.findAllTranslations(chatId, lang);
        List<QuizDTO.QuizVariant> words = new ArrayList<>();
        for (Word word : wordsRepo.findWeakest(chatId, lang)) {
            QuizDTO.QuizVariant quizVariant = new QuizDTO.QuizVariant();
            quizVariant.setWord(mapper.entityToDto(word));
            quizVariant.setTranslations(getTranslationVariants(translations, word));
            words.add(quizVariant);
        }
        return new QuizDTO(words);
    }

    private List<String> getTranslationVariants(List<String> translations, Word word) {
        List<String> trans = new ArrayList<>(4);
        trans.add(word.getTranslate());
        trans.add(translations.get(0));
        Collections.shuffle(translations);
        trans.add(translations.get(0));
        Collections.shuffle(translations);
        trans.add(translations.get(0));
        Collections.shuffle(translations);
        return trans;
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
        word.setTranslate(translateProxy.translate(new TranslateDTO(lang, "ru", stringWord)));
        word.setLanguage(lang);
        word.setOccurrences(0);
        word.setUsers(new HashSet<>());
        word.setCorrectReplies(0);
        word.setAdditionDate(LocalDate.now());
        return word;
    }
}
