package com.example.wordstraining.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserWordKey implements Serializable {
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "word")
    private String word;
}
