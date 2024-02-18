package com.example.wordstraining.services;

import com.example.wordstraining.entities.User;
import com.example.wordstraining.repo.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepo usersRepo;

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }
    public void save(long chatId) {
        if (usersRepo.findById(chatId).isEmpty()) {
            usersRepo.save(new User(chatId, new HashSet<>()));
        }
    }
    public Optional<User> findUser(long id) {
        return usersRepo.findById(id);
    }
}
