package com.example.wordstraining.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Data
@Embeddable
class UserWordKey implements Serializable {
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "word")
    private String word;
}
