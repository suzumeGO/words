package com.example.wordstraining.services;

import com.example.wordstraining.DTO.QuizDTO;
import com.example.wordstraining.DTO.TranslateDTO;
import com.example.wordstraining.entities.UserWord;
import com.example.wordstraining.entities.UserWordKey;
import com.example.wordstraining.entities.Word;
import com.example.wordstraining.exceptions.WordAlreadyExistsException;
import com.example.wordstraining.mappers.WordsMapper;
import com.example.wordstraining.proxy.TranslateProxy;
import com.example.wordstraining.repo.WordsRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WordsService {
    private final WordsRepo wordsRepo;
    private final TranslateProxy translateProxy;
    private final UsersService usersService;
    private final UserWordsService userWordsService;
    private final WordsMapper mapper;

    public WordsService(WordsRepo wordsRepo,
                        TranslateProxy translateProxy,
                        UsersService usersService,
                        UserWordsService userWordsService,
                        WordsMapper mapper) {
        this.wordsRepo = wordsRepo;
        this.userWordsService = userWordsService;
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
        wordsRepo.save(word);
        if (userWordsService.findByKey(new UserWordKey(chatId, word.getWord())).isEmpty()) {
            userWordsService.save(createUserWord(word, chatId));
        } else {
            throw new WordAlreadyExistsException();
        }
    }

    private UserWord createUserWord(Word word, long chatId) {
        UserWord userWord = new UserWord();
        userWord.setWord(wordsRepo.findByWord(word.getWord()));
        userWord.setOccurrences(0);
        userWord.setCorrectRate(0);
        userWord.setId(new UserWordKey(chatId, word.getWord()));
        userWord.setCorrectReplies(0);
        userWord.setChatId(usersService.findUser(chatId).get());
        userWord.setAdditionDate(LocalDate.now());
        return userWord;
    }

    public Word createWord(String stringWord, String lang) {
        Word word = new Word();
        word.setWord(stringWord);
        word.setTranslate(translateProxy.translate(new TranslateDTO(lang, "ru", stringWord)));
        word.setLanguage(lang);
        return word;
    }
}
