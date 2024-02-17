package com.example.wordstraining.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "chat_id")
    private long chatId;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "chat_id",
            cascade = CascadeType.ALL)
    private Set<UserWord> userWords;


}
