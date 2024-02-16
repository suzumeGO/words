package com.example.wordstraining.controllers;

import com.example.wordstraining.DTO.QuizDTO;
import com.example.wordstraining.DTO.WordDTO;
import com.example.wordstraining.DTO.WordsListDTO;
import com.example.wordstraining.entities.Word;
import com.example.wordstraining.mappers.WordsMapper;
import com.example.wordstraining.services.UsersService;
import com.example.wordstraining.services.WordsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/words")
public class WordsController {
    private static final int PAGE_SIZE = 40;
    private final WordsService wordsService;
    private final UsersService usersService;
    private final WordsMapper wordsMapper;
    public WordsController(WordsService wordsService,
                           UsersService usersService,
                           WordsMapper wordsMapper) {
        this.wordsService = wordsService;
        this.wordsMapper = wordsMapper;
        this.usersService = usersService;
    }

    @GetMapping("/list")
    public WordsListDTO getWords(@RequestParam long chatId,
                                 @RequestParam String lang,
                                 @RequestParam(defaultValue = "1") int page) {
        page = page - 1;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return wordsMapper.entityPageToWordsDTOList(wordsService.getWords(chatId, lang, pageable));
    }

    @PostMapping("/add")
    public void addWord(@RequestParam String word,
                        @RequestParam String lang,
                        @RequestParam long chatId) {
        word = word.toLowerCase();
        usersService.save(chatId);
        if (wordsService.contains(word)) {
            wordsService.save(wordsService.findById(word), chatId);
        } else {
            wordsService.save(wordsService.createWord(word, lang), chatId);
        }
    }
    @GetMapping("/weakestQuiz")
    public QuizDTO getWeakestQuiz(@RequestParam long chatId, @RequestParam String lang) {
        return wordsService.getWeakestQuiz(chatId, lang);
    }
    @PostMapping("/updateWord")
    public void updateWord(@RequestBody WordDTO word, @RequestParam long chatId) {
        Word w = wordsService.findById(word.getWord());
        w.setOccurrences(word.getOccurrences());
        w.setCorrectReplies(word.getCorrectReplies());
        w.setCorrectRate(word.getCorrectRate());
        wordsService.save(w, chatId);
    }
    @GetMapping("/quiz")
    public QuizDTO getQuiz(@RequestParam long chatId) {
        return null;
    }
}
