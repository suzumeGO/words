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

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "word",
            cascade = CascadeType.ALL)
    private Set<UserWord> userWords;

}
