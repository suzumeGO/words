package com.example.wordstraining.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity(name = "user_words")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWord {
    @EmbeddedId
    private UserWordKey id;
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
    private long chatId;
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("word")
    @JoinColumn(name = "word")
    private String word;
    @Column(name = "occurrences")
    private int occurrences;
    @Column(name = "correct_replies")
    private int correctReplies;
    @Column(name = "correct_rate")
    private double correctRate;
    @Column(name = "addition_date")
    private LocalDate additionDate;


}
