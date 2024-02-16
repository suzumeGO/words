package com.example.wordstraining.mappers;

import com.example.wordstraining.DTO.WordDTO;
import com.example.wordstraining.DTO.WordsListDTO;
import com.example.wordstraining.entities.Word;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WordsMapper {
    WordDTO entityToDto(Word word);
    List<WordDTO> entityToDtoList(List<Word> words);
    @Mapping(target = "pagination", expression = "java(getPagination(words))")
    @Mapping(target = "data", expression = "java(entityToDtoList(words.getContent()))")
    WordsListDTO entityPageToWordsDTOList(Page<Word> words);

    default WordsListDTO.Pagination getPagination(Page<Word> words) {
        WordsListDTO.Pagination pagination = new WordsListDTO.Pagination();
        pagination.setCurrentPage(words.getPageable().getPageNumber() + 1);
        pagination.setHasNextPage(words.hasNext());
        pagination.setLastPage(words.getTotalPages());
        WordsListDTO.Pagination.Items items = new WordsListDTO.Pagination.Items();
        items.setCount(words.getNumberOfElements());
        items.setTotal((int) words.getTotalElements());
        items.setPerPage(words.getPageable().getPageSize());
        pagination.setItems(items);
        return pagination;
    }

}
