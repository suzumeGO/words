package com.example.wordstraining.repo;

import com.example.wordstraining.entities.User;
import com.example.wordstraining.entities.Word;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomizedWordsRepoImpl implements CustomizedWordsRepo<Word> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Word> findAllByUser(long user, String lang, Pageable pageable) {
        List<Word> result;
        long wordsTotal = createCountQuery(user, lang).getSingleResult();
        result = createQuery(user, lang)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(result, pageable, wordsTotal);
    }

    private TypedQuery<Word> createQuery(long user, String lang) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        User u = findUser(user);
        CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);
        Root<Word> word = criteriaQuery.from(Word.class);
        CriteriaQuery<Word> query = criteriaQuery
                .select(word)
                .where(criteriaBuilder.and(criteriaBuilder.equal(word.get("language"), lang),
                         criteriaBuilder.isMember(u, word.get("users"))));
        return entityManager.createQuery(query);
    }

    private TypedQuery<Long> createCountQuery(long user, String lang) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        User u = findUser(user);
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Word> word = criteriaQuery.from(Word.class);
        CriteriaQuery<Long> query = criteriaQuery
                .select(criteriaBuilder.count(word))
                .where(criteriaBuilder.and(criteriaBuilder.equal(word.get("language"), lang),
                        criteriaBuilder.isMember(u, word.get("users"))));
        return entityManager.createQuery(query);
    }

    private User findUser(long user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> u = criteriaQuery.from(User.class);
        CriteriaQuery<User> query = criteriaQuery
                .select(u)
                .where(criteriaBuilder.equal(u.get("chatId"), user));
        return entityManager.createQuery(query).getSingleResult();
    }
}
