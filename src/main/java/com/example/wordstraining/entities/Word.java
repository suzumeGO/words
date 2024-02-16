package com.example.wordstraining.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity(name = "words")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word {
    @Id
    @Column(name = "word")
    private String word;
    @Column(name = "translate")
    private String translate;
    @Column(name = "language")
    private String language;
    @Column(name = "occurrences")
    private int occurrences;
    @Column(name = "correct_replies")
    private int correctReplies;
    @Column(name = "correct_rate")
    private double correctRate;
    @Column(name = "addition_date")
    private LocalDate additionDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_words",
    joinColumns = @JoinColumn(name = "word"),
    inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<User> users;

}
