package com.example.wordstraining.controllers;

import com.example.wordstraining.DTO.WordDTO;
import com.example.wordstraining.entities.Word;
import com.example.wordstraining.mappers.WordsEntityToDtoMapper;
import com.example.wordstraining.services.UsersService;
import com.example.wordstraining.services.WordsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/words")
public class WordsController {
    private final WordsService wordsService;
    private final UsersService usersService;
    private final WordsEntityToDtoMapper wordsMapper;
    public WordsController(WordsService wordsService,
                           UsersService usersService,
                           WordsEntityToDtoMapper wordsMapper) {
        this.wordsService = wordsService;
        this.wordsMapper = wordsMapper;
        this.usersService = usersService;
    }

    @GetMapping("/list")
    public List<WordDTO> getWords(@RequestParam long chatId, @RequestParam String lang) {
        return wordsMapper.entityToDtoList(wordsService.getWords(chatId, lang));
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

    @GetMapping("/weakest")
    public List<Word> getWeakestWords(@RequestParam long chatId) {
        return null;
    }

    @GetMapping("/weakestQuiz")
    public List<Word> getWeakestQuiz(@RequestParam long chatId) {
        return null;
    }
    @GetMapping("/quiz")
    public List<Word> getQuiz(@RequestParam long chatId) {
        return null;
    }
}
