package com.example.wordstraining.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "chat_id")
    private long chatId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_words",
    joinColumns = @JoinColumn(name = "chat_id"),
    inverseJoinColumns = @JoinColumn(name = "word"))
    private List<Word> words;


}
