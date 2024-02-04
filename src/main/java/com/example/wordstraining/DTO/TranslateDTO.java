package com.example.wordstraining.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateDTO {
    private String src;
    private String dst;
    private String text;

}
