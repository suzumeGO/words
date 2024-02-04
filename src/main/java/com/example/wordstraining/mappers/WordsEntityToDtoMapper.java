package com.example.wordstraining.mappers;

import com.example.wordstraining.DTO.WordDTO;
import com.example.wordstraining.entities.Word;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WordsEntityToDtoMapper {
    WordDTO entityToDto(Word word);
    List<WordDTO> entityToDtoList(List<Word> words);

}
